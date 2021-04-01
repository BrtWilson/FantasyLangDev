package com.example.server.lambda.statusUpdateLambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.server.service.FollowerService;
import com.example.server.service.NewStatusService;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;

import java.io.IOException;

public class BatchFeedUpdater  {
    //Uses FollowerService(?) to get batches of followers whose feeds are to be updated

    public void handleNewStatusMessage(NewStatusRequest newStatusRequest) { // Uses an input Status or an input NewStatusRequest
        //From parameter info, discerns user
        String correspondingUserAlias = newStatusRequest.getUserAlias();

        FollowerService followerService = new FollowerService();
        // TODO: get each batch of followers, send request to queue with corresponding info (batch of followers (25) and the status)
       /* try {
            //return followerService.gets(newStatusRequest);
        } catch (RuntimeException | IOException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }*/
    }
}
