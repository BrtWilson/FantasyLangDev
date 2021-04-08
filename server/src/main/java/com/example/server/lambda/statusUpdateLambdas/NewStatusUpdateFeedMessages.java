package com.example.server.lambda.statusUpdateLambdas;

import com.example.server.dao.FollowsTableDAO;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.FollowerResponse;

public class NewStatusUpdateFeedMessages {
    private static final int BatchSize = 25;
    private String lastFollowerAlias;

    public void queueMessages(NewStatusRequest newStatusRequest) {
        String followeeAlias = newStatusRequest.getUserAlias();
        do {
            FollowerRequest followerRequest = new FollowerRequest( followeeAlias, BatchSize,  lastFollowerAlias);
            FollowerResponse followerResponse = getFollowersTable().getFollowers(followerRequest);
            lastFollowerAlias = followerResponse.getLastFollowerAlias();
            sendBatchToQueue(followerResponse, newStatusRequest);
        } while (lastFollowerAlias != null);
    }

    private void sendBatchToQueue(FollowerResponse followerResponse, NewStatusRequest newStatusRequest) {

    }

    // Receive a list of Followers, and a status to add to each of their feeds
    private FollowsTableDAO getFollowersTable() {
        return new FollowsTableDAO();
    }
}
