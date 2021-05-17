package com.example.server.lambda.statusUpdateLambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.example.server.lambda.statusUpdateLambdas.util.JsonSerializer;
import com.example.server.service.NewStatusFeedService;
import com.example.shared.model.service.request.NewStatusRequest;

import java.io.IOException;
import java.util.Map;

public class BatchFeedUpdater implements RequestHandler<SQSEvent, Void> {

    private static final String followerAliasesAttribute = "FollowerAliases";
    private static final String postedStatusAttribute = "PostedStatus";
    private static final String BAD_REQUEST = "[Bad Request]";

    @Override
    public Void handleRequest(SQSEvent input, Context context) {
        for (SQSEvent.SQSMessage msg : input.getRecords()) {
            Map attributes = msg.getAttributes();
            String followerResponseStr = extractAttribute(attributes.get(followerAliasesAttribute));
            String newStatusRequestStr = extractAttribute(attributes.get(postedStatusAttribute));
            FollowerResponse followerResponse = convertToRequestObject(followerResponseStr, FollowerResponse.class);
            NewStatusRequest newStatusRequest = convertToRequestObject(newStatusRequestStr, NewStatusRequest.class);
            handleBatch(newStatusRequest, followerResponse);
        }

        //Uses FollowerService(?) to get batches of followers whose feeds are to be updated
        return null;
    }

    private <T> T convertToRequestObject(String objectString, Class<T> toClass) {
        return JsonSerializer.deserialize(objectString, toClass);
    }

    private String extractAttribute(Object tempMessageObject) {
        MessageAttributeValue tempMessageAttributeValue = (MessageAttributeValue) tempMessageObject;
        return tempMessageAttributeValue.getStringValue();
    }

    public void handleBatch(NewStatusRequest newStatusRequest, FollowerResponse followerResponse) { // Uses an input Status or an input NewStatusRequest
        //From parameter info, discerns user
        NewStatusFeedService newStatusFeedService = new NewStatusFeedService();
        try {
            newStatusFeedService.postStatusBatch(newStatusRequest, followerResponse);
        } catch (IOException e) {
            String message = BAD_REQUEST;
            throw new RuntimeException(message, e);
        }
    }

}
