package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.example.server.lambda.statusUpdateLambdas.util.JsonSerializer;
import com.example.server.service.NewStatusStoryService;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.response.NewLanguageResponse;

import java.io.IOException;

public class NewStatusHandler implements RequestHandler<NewLanguageRequest, NewLanguageResponse> {

    private static final String messageQueueUrl = "https://sqs.us-east-1.amazonaws.com/217816874822/PostUpdateFeedMessageQueue.fifo";

    @Override
    public NewLanguageResponse handleRequest(NewLanguageRequest newLanguageRequest, Context context) {
        NewStatusStoryService newStatusStoryService = new NewStatusStoryService();
        try {
            PostToFollowerFeeds(newLanguageRequest);
            return newStatusStoryService.postNewStatus(newLanguageRequest);
        } catch (RuntimeException | IOException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }
    }

    private void PostToFollowerFeeds(NewLanguageRequest newLanguageRequest) {
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        String requestMessage = createMessage(newLanguageRequest);
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

    private String createMessage(NewLanguageRequest newLanguageRequest) {
        return JsonSerializer.serialize(newLanguageRequest);
    }
}