package com.example.server.lambda.statusUpdateLambdas;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.example.server.lambda.statusUpdateLambdas.util.JsonSerializer;
import com.example.server.service.FollowerService;
import com.example.shared.model.service.request.NewStatusRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NewStatusUpdateFeedMessages implements RequestHandler<SQSEvent, Void> {
    private static final int BatchSize = 25;
    private String lastFollowerAlias;
    private static final String batchQueueUrl = "https://sqs.us-east-1.amazonaws.com/217816874822/BatchFeedUpdaterQueue.fifo";

    private static final String followerAliasesAttribute = "FollowerAliases";
    private static final String postedStatusAttribute = "PostedStatus";

    @Override
    public Void handleRequest(SQSEvent input, Context context) {
        //Uses FollowerService(?) to get batches of followers whose feeds are to be updated
        for (SQSEvent.SQSMessage msg : input.getRecords()) {
            NewStatusRequest newStatusRequest = convertToRequestObject(msg.getBody());
            queueMessages(newStatusRequest);
        }
        return null;
    }

    public void queueMessages(NewStatusRequest newStatusRequest) {
        String followeeAlias = newStatusRequest.getUserAlias();
        do {
            FollowerRequest followerRequest = new FollowerRequest( followeeAlias, BatchSize,  lastFollowerAlias);
            FollowerResponse followerResponse = null;
            try {
                followerResponse = getFollowersService().getFollowers(followerRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            lastFollowerAlias = followerResponse.getLastFollowerAlias();
            sendBatchToQueue(followerResponse, newStatusRequest);
        } while (lastFollowerAlias != null);
    }

    private void sendBatchToQueue(FollowerResponse followerResponse, NewStatusRequest newStatusRequest) {
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        Map<String, MessageAttributeValue> messageAttributes = packMapAttributes(followerResponse, newStatusRequest);
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(batchQueueUrl)
                .withMessageAttributes(messageAttributes);
        //.withMessageAttributes for sending < 10 items
        //else .withMessageBody(messageBody) and parse

        //VERIFY: sends newStatusRequest to PostUpdateFeedMessagesQueue
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
    }

    private Map<String, MessageAttributeValue> packMapAttributes(Object followerResponse, Object newStatusRequest) {
        MessageAttributeValue followersValue = new MessageAttributeValue();
        followersValue.setStringValue(createMessageString(followerResponse));
        MessageAttributeValue statusValue = new MessageAttributeValue();
        statusValue.setStringValue(createMessageString(newStatusRequest));

        Map<String, MessageAttributeValue> attributeMap = new HashMap<>();
        attributeMap.put(followerAliasesAttribute, followersValue);
        attributeMap.put(postedStatusAttribute, statusValue);

        return attributeMap;
    }

    private String createMessageString(Object sendingObject) {
        return JsonSerializer.serialize(sendingObject);
    }

    private NewStatusRequest convertToRequestObject(String msg) {
        return JsonSerializer.deserialize(msg, NewStatusRequest.class);
    }

    // Receive a list of Followers, and a status to add to each of their feeds
    private FollowerService getFollowersService() {
        return new FollowerService();
    }
}
