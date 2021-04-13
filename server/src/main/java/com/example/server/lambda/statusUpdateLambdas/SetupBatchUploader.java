package com.example.server.lambda.statusUpdateLambdas;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.List;
import java.util.Map;

public class SetupBatchUploader {

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);

    //Generating Data to DB
    public static void main(String[] args) {
        uploadUsers();
    }

    private static void uploadUsers() {
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

    private static void uploadFeeds() {
    }

    private static void uploadStories() {
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
