package com.example.server.lambda.statusUpdateLambdas;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.example.server.service.FollowerService;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.FollowerResponse;

import java.util.List;
import java.util.Map;

public class BatchFeedUpdater implements RequestHandler<SQSEvent, Void> {

    private static final String followerAliasesAttribute = "FollowerAliases";
    private static final String postedStatusAttribute = "PostedStatus";

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
        String correspondingUserAlias = newStatusRequest.getUserAlias();

        FollowerService followerService = new FollowerService();
        // TODO: follow along what Blake did below to
        //  get each batch of followers, send request to queue with corresponding info (batch of followers (25) and the status)
       /* try {
            //return followerService.gets(newStatusRequest);
        } catch (RuntimeException | IOException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }*/
    }


    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);

    //Generating Data to DB
    public static void main(String[] args) {


        String dbPrimaryKey = "Alias";

        TableWriteItems items = new TableWriteItems("Users");

        for(int i =0 ; i < 2; i ++){
            String userName = "@blakeJ"+ i;
            String firstName = "@user";
            String lastName = String.valueOf(i);
            String profileImage = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngitem.com%2Fmiddle%2FwomThJ_ash-ketchum-hd-png-download%2F&psig=AOvVaw2h43_Bi3x5gdd1y2tRmAhq&ust=1616605412770000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJjVytTyxu8CFQAAAAAdAAAAABAL";
            //later add this with BatchAdd
            Item item = new Item().withPrimaryKey(dbPrimaryKey, userName).withString("FirstName", firstName).withString("ImageUrl", profileImage).withString("LastName", lastName);

            //table.putItem(item);

            items.addItemToPut(item);
            if(items.getItemsToPut() != null && items.getItemsToPut().size() == 25){
                //then add a list for the Batch
                loopBatchWriter(items);
                items = new TableWriteItems("Users");
            }
        }

        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWriter(items);
        }
    }

    private static void loopBatchWriter(TableWriteItems items){
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);
        //logger.log("Wrote User Batch");

        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
            //   logger.log("Wrote more Users");
        }
    }
}
