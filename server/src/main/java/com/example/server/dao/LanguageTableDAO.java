package com.example.server.dao;

import com.example.server.dao.dbstrategies.AWS_RDBStrategy;
import com.example.server.dao.dbstrategies.DBStrategyInterface;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.request.UpdateAlphabetRequest;
import com.example.shared.model.service.request.UpdateWordRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.NewLanguageResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.rmi.runtime.Log;

public class LanguageTableDAO {
    private DBStrategyInterface databaseInteractor = getDatabaseInteractor();

    private static final String tableName = "Languages";
    private static final String attributeUserName = "UserName";
    private static final String attributeLangID = "LanguageID";

    private static final String attributeLanguageName = "LanguageName";
    private static final String attributeAlphabet = "Alphabet";

    private static final Integer PAGE_SIZE_DEFAULT = 10;
    private static Integer pageSize;

    private static final int uploadBatchSize = 25;

    /**
     * Queries for the languages associated with a given UserName
     * @param request the LoginRequest providing the User's UserName
     * @return the list of languageID's found in the Language Table, corresponding to the given UserName
     */
    public List<String> getLanguages(LoginRequest request) {
        Map<String, String> queryAttributes = new HashMap<>();
        queryAttributes.put(attributeUserName, request.getUsername());

        List<Map<String, String>> queryResults = databaseInteractor.queryListItems(tableName, queryAttributes);
        List<String> userLanguages = new ArrayList<>();
        for (int i = 0; i < queryResults.size(); i++) {
            userLanguages.add(queryResults.get(i).get(attributeLangID));
        }
        return userLanguages;
    }

    /**
     *
     * @param request provides the LanguageID
     * @return GetLanguageDataResponse object with all fields corresponding to the Languages Table
     *          including alphabet if it exists
     */
    public GetLanguageDataResponse getLanguageData(GetLanguageDataRequest request) {
        Map<String, String> languageData = databaseInteractor.getItem(tableName, attributeLangID, request.getLanguageID());
        if (languageData != null) {
            String userName = languageData.get(attributeUserName);
            String languageName = languageData.get(attributeLanguageName);
            String languageID = languageData.get(attributeLangID);
            String alphabet = languageData.get(attributeAlphabet);
            GetLanguageDataResponse response = new GetLanguageDataResponse(userName, languageName, languageID, alphabet);
            return response;
        }
        return new GetLanguageDataResponse("No language data found for " + request.getLanguageID());
    }

    /**
     *  *TODO NOTE: likely will require particular adjustment for establishment of LANGUAGEID,
     *      unless we keep that the database will develop the key for this
     */
    public NewLanguageResponse createLanguage(NewLanguageRequest request) {
        Map<String, String> basicLanguageData = new HashMap<>();
        basicLanguageData.put(attributeUserName, request.getUserName());
        basicLanguageData.put(attributeLanguageName, request.getLanguageName());
        if (databaseInteractor.insertItem(tableName, basicLanguageData)) {
            Map<String, String> languageInserted = databaseInteractor.querySingleItem(tableName, basicLanguageData);
            String languageIDRetrieved = languageInserted.get(attributeLangID);
            return new NewLanguageResponse(request.getUserName(), request.getLanguageName(), languageIDRetrieved);
        }
        return new NewLanguageResponse("Failed to insert new language.");
    }

    public GeneralUpdateResponse updateAlphabet(UpdateAlphabetRequest request) {
        Map<String, String> queryAttributes = new HashMap<>();
        Map<String, String> updateAttributes = new HashMap<>();
        queryAttributes.put(attributeLangID, request.getLanguageID());
        updateAttributes.put(attributeAlphabet, request.getAlphabet());
        try {
            databaseInteractor.updateItem(tableName, queryAttributes, updateAttributes);
        } catch (Exception e) {
            return new GeneralUpdateResponse(false, "[Error] Alphabet failed to update: " + e.getMessage());
        }
        return new GeneralUpdateResponse(request.getLanguageID());
    }

/*
    public StatusArrayResponse getStatusArray(StatusArrayRequest request) {
        pageSize = request.getLimit();
        verifyLimit(request.getLimit());
        verifyAlias(request.getUserAlias());

        return retrieveFeed(request.getUserAlias(), request.getLastStatusDate());
    }

    private void verifyLimit(int limit) {
        if (limit < 0) {
            pageSize = PAGE_SIZE_DEFAULT;
        }
    }

    private void verifyAlias(String userAlias) {
        if (userAlias == null) {
            throw new AssertionError();
        }
    }

    //NOPE: Probably will need refactoring with having the additional userAlias --> Should work as is
    private StatusArrayResponse retrieveFeed(String targetAlias, String lastRetrieved) {
        ResultsPage resultsPage = getDatabaseInteractor().getListByString(tableName, partitionKey, targetAlias, pageSize, sortKey, lastRetrieved);
        boolean hasMorePages = (resultsPage.hasLastKey());
        String newLastRetrieved = resultsPage.getLastKey();
        List<Status> statusList = ListTypeItemTransformer.transformToStatus(resultsPage.getValues());
        StatusArrayResponse response = new StatusArrayResponse(statusList, hasMorePages, newLastRetrieved);
        return response;
    }

    public NewStatusResponse postStatusBatch(NewStatusRequest request, FollowerResponse followerResponse) {
        // Done: follow along what Blake did in BatchFeedUpdater to
        //  get each batch of followers, send request to queue with corresponding info (batch of followers (25) and the status)

        String timeStamp = request.getDate();

        List<String> attributeNames = new ArrayList<>();
        List<String> attributeValues = new ArrayList<>();
        attributeNames.add(attributeMessage);
        attributeValues.add(request.getMessage());
        attributeNames.add(attributeStatusUser);
        attributeValues.add(request.getUserAlias());
        List<User> followersList = followerResponse.getFollowers();
        List<String> followersAliases = getUserAliases(followersList);

        getDatabaseInteractor().batchUploadByPartition(tableName, partitionKey, followersAliases, sortKey, timeStamp, attributeNames, attributeValues, uploadBatchSize);

        return new NewStatusResponse(new Status(request.getMessage(),timeStamp, request.getUserAlias()));
    }

    private List<String> getUserAliases(List<User> followersList) {
        List<String> aliases = new ArrayList<>();
        for (User u : followersList) {
            aliases.add(u.getAlias());
        }
        return aliases;
    }


    /* UNNEEDED: DEPRACATED
    private int getStatusesStartingIndex(String lastStatusAlias, List<Status> allStatuses) {

        int statusesIndex = 0;

        if (lastStatusAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if (lastStatusAlias.equals(allStatuses.get(i).getDate())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusesIndex = i + 1;
                    break;
                }
            }
        }

        return statusesIndex;
    }*/

    private DBStrategyInterface getDatabaseInteractor() {
        return new AWS_RDBStrategy();
        //return new DynamoDBStrategy();
    }
}
