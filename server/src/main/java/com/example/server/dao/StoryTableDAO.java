package com.example.server.dao;

import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.dao.dbstrategies.ResultsPage;
import com.example.server.dao.util.ListTypeTransformer;
import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.NewStatusResponse;
import com.example.shared.model.service.response.StatusArrayResponse;

import java.util.List;

public class StoryTableDAO {
    //DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    private static final String tableName = "Stories";
    private static final String partitionKey = "Alias";
    private static final String sortKey = "TimeStamp";
    private static final String attributeMessage = "Message";
    private static final Integer pageSize = 10;

    public StatusArrayResponse getStatusArray(StatusArrayRequest request) {
        verifyLimit(request.getLimit());
        verifyAlias(request.getUserAlias());

        return retrieveStory(request.getUserAlias(), request.getLastStatusDate());
    }

    private void verifyLimit(int limit) {
        if (limit < 0) {
            throw new AssertionError();
        }
    }

    private void verifyAlias(String userAlias) {
        if (userAlias == null) {
            throw new AssertionError();
        }
    }

    /*
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

    /**
     *  // * * * => THIS WILL BE USED WITH THE SQS QUEUES. It will need the inclusion of the Status's correspondingUserAlias, as well as the Feed's owner userAlias
     *
     * @param request
     * @return
     */
    public NewStatusResponse postNewStatus(NewStatusRequest request) {
        DynamoDBStrategy.createItemWithDualKey(tableName, partitionKey, request.getUserAlias(), sortKey, request.getDate(), true, attributeMessage, request.getMessage());
        return new NewStatusResponse(new Status(request.getMessage(), request.getDate(), request.getUserAlias()));
        //return dataProvider.pushNewStatus(request);
    }

    //private User getAUser() {
      //  return null;// dataProvider.getSampleDummyUser();
    //}

    private StatusArrayResponse retrieveStory(String targetAlias, String lastRetrieved) {
        ResultsPage resultsPage = DynamoDBStrategy.getListByString(tableName, partitionKey, targetAlias, pageSize, sortKey, lastRetrieved);
        boolean hasMorePages = (resultsPage.hasLastKey());
        String newLastRetrieved = resultsPage.getLastKey();
        List<Status> statusList = ListTypeTransformer.transform(resultsPage.getValues(), Status.class);
        StatusArrayResponse response = new StatusArrayResponse(statusList, hasMorePages, newLastRetrieved);
        return response;
    }
}
