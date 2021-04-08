package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.lambda.statusUpdateLambdas.NewStatusUpdateFeedMessages;
import com.example.server.service.NewStatusStoryService;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;

import java.io.IOException;

public class NewStatusHandler implements RequestHandler<NewStatusRequest, NewStatusResponse> {

    @Override
    public NewStatusResponse handleRequest(NewStatusRequest newStatusRequest, Context context) {
        NewStatusStoryService newStatusStoryService = new NewStatusStoryService();
        try {
            PostToFollowerFeeds(newStatusRequest);
            return newStatusStoryService.postNewStatus(newStatusRequest);
        } catch (RuntimeException | IOException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }
    }

    private void PostToFollowerFeeds(NewStatusRequest newStatusRequest) {
        NewStatusUpdateFeedMessages feedMessenger = new NewStatusUpdateFeedMessages();
        feedMessenger.queueMessages(newStatusRequest);
    }
}