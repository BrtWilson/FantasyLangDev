package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.example.server.lambda.statusUpdateLambdas.JsonSerializer;
import com.example.server.lambda.statusUpdateLambdas.NewStatusUpdateFeedMessages;
import com.example.server.service.NewStatusStoryService;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;

import java.io.IOException;

public class NewStatusHandler implements RequestHandler<NewStatusRequest, NewStatusResponse> {

    private static final String messageQueueUrl = "https://sqs.us-east-1.amazonaws.com/217816874822/PostUpdateFeedMessageQueue.fifo";

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
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        String requestMessage = createMessage(newStatusRequest);
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(messageQueueUrl)
                .withMessageBody(requestMessage);
                //.withMessageAttributes for sending < 10 items
                //else .withMessageBody(messageBody) and parse

        //VERIFY: sends newStatusRequest to PostUpdateFeedMessagesQueue
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

        //NewStatusUpdateFeedMessages feedMessenger = new NewStatusUpdateFeedMessages();
        //feedMessenger.queueMessages(newStatusRequest);
    }

    private String createMessage(NewStatusRequest newStatusRequest) {
        return JsonSerializer.serialize(newStatusRequest);
    }
}