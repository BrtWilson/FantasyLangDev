package com.example.server.dao;

import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.dao.dbstrategies.ResultsPage;
import com.example.server.dao.util.ListTypeItemTransformer;
import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.NewStatusResponse;
import com.example.shared.model.service.response.StatusArrayResponse;

import java.util.ArrayList;
import java.util.List;

public class FeedTableDAO {
    //DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    private static final String tableName = "Feeds";
    private static final String partitionKey = "Alias";
    private static final String sortKey = "TimeStamp";

    private static final String attributeMessage = "Message";
    private static final String attributeStatusUser = "StatusUser";

    private static final Integer PAGE_SIZE_DEFAULT = 10;
    private static Integer pageSize;

    private static final int uploadBatchSize = 25;


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

    /** DEPRACATED FOR SQS POSTING
     *  // * * * => THIS TO BE USED WITH THE SQS QUEUES BY THE BATCH_FEED_UPDATER.
     *  It will need the inclusion of the Status's correspondingUserAlias, as well as the Feed's owner userAlias
     *
     * @/param request Contains the Status info: CorrespondingUser, Date, Message
     * @return
     */
//    public NewStatusResponse postNewStatus(NewStatusRequest request, String followerAlias) {
//        List<String> attributeNames = new ArrayList<>();
//        attributeNames.add(attributeStatusUser);
//        attributeNames.add(attributeMessage);
//        List<String> attributeValues = new ArrayList<>();
//        attributeValues.add(request.getUserAlias());
//        attributeValues.add(request.getMessage());
//
//        getDatabaseInteractor().createItemWithDualKeyAndAttributes(tableName, partitionKey, followerAlias, sortKey, request.getDate(), attributeNames, attributeValues);
//        return new NewStatusResponse(new Status(request.getMessage(), request.getDate(), request.getUserAlias()));
//    }

    //private User getAUser() {
      //  return null;// dataProvider.getSampleDummyUser();
    //}

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
        for (int i = 0; i < followersList.size(); i++) {
            aliases.add(followersList.get(i).getAlias());
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

    public DynamoDBStrategy getDatabaseInteractor() {
        return new DynamoDBStrategy();
    }
}
