package com.example.server.lambda.statusUpdateLambdas;

import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.service.FollowerService;
import com.example.shared.model.service.request.NewStatusRequest;

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


    //Generating Data to DB
    public static void main(String[] args) {

        DynamoDBStrategy dynamoDBStrategy = new DynamoDBStrategy();
        String dbPrimaryKey = "Alias";

        for(int i =0 ; i < 10000; i ++){
            String userName = "@user"+ i;
            String firstName = "@user";
            String lastName = String.valueOf(i);
            String profileImage = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngitem.com%2Fmiddle%2FwomThJ_ash-ketchum-hd-png-download%2F&psig=AOvVaw2h43_Bi3x5gdd1y2tRmAhq&ust=1616605412770000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJjVytTyxu8CFQAAAAAdAAAAABAL";
            //later add this with BatchAdd
            if(i - 1 % 25 == 0){
                //then add a list for the Batch
            }
        }

        for(int i =0; i < 10000; i++){
            //create a test user have 100000 followers
            String userName = "@user"+ i;

            if(i - 1 % 25 == 0){
                //then add a list for the Batch
            }
        }
    }
}
