package com.example.server.dao;

import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.dao.dbstrategies.ResultsPage;
import com.example.server.dao.util.ListTypeTransformer;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.FollowStatusRequest;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowStatusResponse;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.FollowingResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sun.xml.internal.ws.addressing.EndpointReferenceUtil.transform;

public class FollowsTableDAO {
    //DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    private static final String tableName = "Follows";
    private static final String partionKey = "FollowerAlias";
    private static final String sortKey = "FollowerAlias";
    private static final Integer pageSize = 10;

    private static final String SERVER_SIDE_ERROR = "[Server Error]";

    public FollowerResponse getFollowers(FollowerRequest request) {
        verifyRequestLimit(request.getLimit());
        verifyRequestUserAlias(request.getUserAlias());
        return retrieveFollowers(request.getUserAlias(), request.getLastFollowerAlias());
    }

    //TODO: DEPRACATE
    public FollowerResponse getNumFollowers(FollowerRequest request) {
        return new FollowerResponse(13);
    }

    private void verifyRequestUserAlias(String requestAlias) {
        if (requestAlias == null) {
            throw new AssertionError();
        }
    }

    private void verifyRequestLimit(int requestLimit) {
        if (requestLimit < 0) {
            throw new AssertionError();
        }
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        verifyRequestLimit(request.getLimit());
        verifyRequestUserAlias(request.getFollowingAlias());

        return retrieveFollowees(request.getFollowingAlias(), request.getLastFolloweeAlias());
    }


    public FollowingResponse getNumFollowing(FollowingRequest request) {
        return new FollowingResponse(13);
    }

    private FollowingResponse retrieveFollowees(String targetAlias, String lastRetrieved) {
        ResultsPage resultsPage = DynamoDBStrategy.getListByString(tableName, partionKey, targetAlias, pageSize, sortKey, lastRetrieved);
        boolean hasMorePages = (resultsPage.hasLastKey());
        String newLastRetrieved = resultsPage.getLastKey();
        List<User> usersList = ListTypeTransformer.transform(resultsPage.getValues(), User.class);
        FollowingResponse response = new FollowingResponse(usersList, hasMorePages, newLastRetrieved);
        return response;
    }

    private FollowerResponse retrieveFollowers(String targetAlias, String lastRetrieved) {
        //TODO: verify whether this works, or if sortKey and partitionKey should stay normal
        ResultsPage resultsPage = DynamoDBStrategy.getListByString(tableName, sortKey, targetAlias, pageSize, partionKey, lastRetrieved, true, sortKey);
        boolean hasMorePages = (resultsPage.hasLastKey());
        String newLastRetrieved = resultsPage.getLastKey();
        List<User> usersList = ListTypeTransformer.transform(resultsPage.getValues(), User.class);
        FollowerResponse response = new FollowerResponse(usersList, hasMorePages, newLastRetrieved);
        return response;
    }

    public FollowStatusResponse unfollow(FollowStatusRequest request) {
        try {
            DynamoDBStrategy.deleteItemWithDualKey(tableName, partionKey, request.getCurrentUser(), sortKey, request.getOtherUser());
            return new FollowStatusResponse(false);
        } catch (Exception e) {
            return new FollowStatusResponse(SERVER_SIDE_ERROR + ": " + e.getMessage());
        }
    }

    public FollowStatusResponse follow(FollowStatusRequest request) {
        try {
            DynamoDBStrategy.createItemWithDualKey(tableName, partionKey, request.getCurrentUser(), sortKey, request.getOtherUser());
            return new FollowStatusResponse(true);
        } catch (Exception e) {
            return new FollowStatusResponse(SERVER_SIDE_ERROR + ": " + e.getMessage());
        }
    }

    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) {
        try {
            Object followTableMatch = DynamoDBStrategy.basicGetItemWithDualKey(tableName, partionKey, request.getCurrentUser(), sortKey, request.getOtherUser());
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
     * @param lastFollowAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollows      the generated list of followees from which we are returning paged results.
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
}
