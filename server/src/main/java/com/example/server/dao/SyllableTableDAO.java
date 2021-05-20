package com.example.server.dao;

import com.example.server.dao.dbstrategies.AWS_RDBStrategy;
import com.example.server.dao.dbstrategies.DBStrategyInterface;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.dao.dbstrategies.ResultsPage;
import com.example.server.dao.util.ListTypeItemTransformer;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;

public class SyllableTableDAO {
    private DBStrategyInterface databaseInteractor = getDatabaseInteractor();

    private static final String tableName = "Syllables";
    private static final String attributeLangID = "LanguageID";
    private static final String attributeTemplate = "SyllableTemplate";
    private static final String attributeDigit = "Digit";
    private static final String attributePossibleChars = "PossibleChars";

    private static final String SERVER_SIDE_ERROR = "[Server Error]";

    /**
     *
     * @param request provides languageID
     * @return a List of String containing the syllable data corresponding to the languageID if any exists
     */
    public List<String> getSyllableData(GetLanguageDataRequest request) {
        Map<String, String> syllableQuery = new HashMap<>();

        List<Map<String, String>> returnedItems = databaseInteractor.queryListItems(tableName, syllableQuery);
        if (returnedItems.size() != 0) {
            Map<Integer, String> returnedSyllablePairs = parseItemsForSyllableMap(returnedItems);
            List<String> syllablesRetrieved = parseMapForSyllables(returnedSyllablePairs);

            return syllablesRetrieved;
        }
        return null;
    }

    private Map<Integer, String> parseItemsForSyllableMap(List<Map<String, String>> returnedItems) {
        Map<Integer, String> returnedSyllablePairs = new HashMap<>();
        for (int i = 0; i < returnedItems.size(); i++) {
            Integer tmpInt = Integer.parseInt(returnedItems.get(i).get(attributeDigit));
            String tmpStr = returnedItems.get(i).get(attributePossibleChars);
            returnedSyllablePairs.put(tmpInt, tmpStr);
        }
        return returnedSyllablePairs;
    }

    /**
     * COMPLICATED enough, should be tested individually
     */
    private List<String> parseMapForSyllables(Map<Integer, String> returnedSyllablePairs) {
        List<Integer> pairOrder = new ArrayList<>();
        List<String> syllablesRetrieved = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : returnedSyllablePairs.entrySet()) {
            pairOrder.add(entry.getKey());
        }
        if (pairOrder.size() != returnedSyllablePairs.size()) {
            System.out.println("Flaw in parseMapForSyllables method");
        }
        for (Map.Entry<Integer, String> entry : returnedSyllablePairs.entrySet()) {
            Integer smallest = 0;
            int index = 0;
            for (int i = 0; i < pairOrder.size(); i++) {
                Integer tmp = pairOrder.get(i);
                if (tmp < smallest) {
                    smallest = tmp;
                    index = i;
                }
            }
            if (pairOrder.size() > 0) {
                syllablesRetrieved.add(returnedSyllablePairs.get(smallest));
                pairOrder.remove(index);
            }
        }

        return syllablesRetrieved;
    }

    public GeneralUpdateResponse updateSyllables(UpdateSyllablesRequest request) {
        Map<Integer, String> syllableItemsToInsert = request.getSyllables();

        try {
            for (Map.Entry<Integer, String> entry : syllableItemsToInsert.entrySet()) {
                Map<String, String> syllableItem = new HashMap<>();
                syllableItem.put(attributeLangID, request.getLanguageID());
                syllableItem.put(attributeTemplate, request.getSyllableTemplate());
                syllableItem.put(attributeDigit, entry.getKey().toString());
                syllableItem.put(attributePossibleChars, entry.getValue());
                databaseInteractor.insertItem(tableName, syllableItem);
            }
        } catch (Exception e) {
            return new GeneralUpdateResponse(false, e.getMessage());
        }

        return new GeneralUpdateResponse(request.getLanguageID());
    }

/*
    public FollowerResponse getFollowers(FollowerRequest request) {
        pageSize = request.getLimit();
        verifyRequestLimit(request.getLimit());
        verifyRequestUserAlias(request.getUserAlias());
        System.out.println("User: " + request.getUserAlias() + " LastFollower: " +request.getLastFollowerAlias());
        return retrieveFollowers(request.getUserAlias(), request.getLastFollowerAlias());
    }

    //
    //public FollowerResponse getNumFollowers(FollowerRequest request) {
      //  return new FollowerResponse(13);
    //}

    private void verifyRequestUserAlias(String requestAlias) {//We have followers that hits this point, and just blows up
        if (requestAlias == null) {
            throw new AssertionError();
        }
    }

    private void verifyRequestLimit(int requestLimit) {
        if (requestLimit < 0) {
            pageSize = PAGE_SIZE_DEFAULT;
        }
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        pageSize = request.getLimit();
        verifyRequestLimit(request.getLimit());
        verifyRequestUserAlias(request.getFollowingAlias());

        System.out.println("User: " + request.getFollowingAlias() + " LastFollower: " +request.getLastFolloweeAlias());

        return retrieveFollowees(request.getFollowingAlias(), request.getLastFolloweeAlias());
    }

    //DEPRACATE
    //public FollowingResponse getNumFollowing(FollowingRequest request) {
      //  return new FollowingResponse(13);
    //}

    private FollowingResponse retrieveFollowees(String targetAlias, String lastRetrieved) {
        System.out.println("Followees targetAlias: " + targetAlias + " and " + lastRetrieved);

        ResultsPage resultsPage = getDatabaseInteractor().getListByString(tableName, partionKey, targetAlias, pageSize, sortKey, lastRetrieved, false, null);
        boolean hasMorePages = (resultsPage.hasLastKey());
        String newLastRetrieved = resultsPage.getLastKey();
        List<User> usersList = ListTypeItemTransformer.transformToUser(resultsPage.getValues());
        FollowingResponse response = new FollowingResponse(usersList, hasMorePages, newLastRetrieved);
        return response;
    }

    private FollowerResponse retrieveFollowers(String targetAlias, String lastRetrieved) {
        //TODO: verify whether this works, or if sortKey and partitionKey should stay normal
        System.out.println("Followers targetAlias: " + targetAlias + " and " + lastRetrieved);

        ResultsPage resultsPage = getDatabaseInteractor().getListByString(tableName, sortKey, targetAlias, pageSize, partionKey, lastRetrieved, true, "FolloweeAlias-FollowerAlias-index");
        boolean hasMorePages = (resultsPage.hasLastKey());
        String newLastRetrieved = resultsPage.getLastKey();
        List<User> usersList = ListTypeItemTransformer.transformToUser(resultsPage.getValues());
        FollowerResponse response = new FollowerResponse(usersList, hasMorePages, newLastRetrieved);
        return response;
    }

    public FollowStatusResponse unfollow(FollowStatusRequest request) {
        try {
            getDatabaseInteractor().deleteItemWithDualKey(tableName, partionKey, request.getCurrentUser(), sortKey, request.getOtherUser());

            UsersTableDAO usersTableDAO = new UsersTableDAO();
            usersTableDAO.unfollow(request);
            return new FollowStatusResponse(false);
        } catch (Exception e) {
            return new FollowStatusResponse(SERVER_SIDE_ERROR + ": " + e.getMessage());
        }
    }

    public FollowStatusResponse follow(FollowStatusRequest request) {
        try {
            getDatabaseInteractor().createItemWithDualKey(tableName, partionKey, request.getCurrentUser(), sortKey, request.getOtherUser());

            UsersTableDAO usersTableDAO = new UsersTableDAO();
            usersTableDAO.follow(request);
            return new FollowStatusResponse(true);
        } catch (Exception e) {
            return new FollowStatusResponse(SERVER_SIDE_ERROR + ": " + e.getMessage()); //folllw lambda is hitting this
        }
    }

    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) {
        try {
            Object followTableMatch = getDatabaseInteractor().basicGetItemWithDualKey(tableName, partionKey, request.getCurrentUser(), sortKey, request.getOtherUser());
            return new FollowStatusResponse((followTableMatch != null));
        } catch (Exception e) {
            return new FollowStatusResponse(SERVER_SIDE_ERROR + ": " + e.getMessage());
        }
    }




    /** DEPRACATED: AS DYNAMODB WILL HANDLE THIS, I BELIEVE
     * Determines the index for the first follower in the specified 'allFollowers' list that should
     * be returned in the current request. This will be the index of the next follower after the
     * specified 'lastFollower'.
     *
     * @/param lastFollowAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @/param allFollows      the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
   /* private int getFollowsStartingIndex(String lastFollowAlias, List<User> allFollows) {

        int followsIndex = 0;

        if (lastFollowAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollows.size(); i++) {
                if (lastFollowAlias.equals(allFollows.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followsIndex = i + 1;
                    break;
                }
            }
        }

        return followsIndex;
    }*/

    private DBStrategyInterface getDatabaseInteractor() {
        return new AWS_RDBStrategy();
        //return new DynamoDBStrategy();
    }
}
