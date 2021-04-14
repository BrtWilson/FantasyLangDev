package com.example.server.dao.util;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListTypeItemTransformer {

    private static final String partitionKey = "Alias";
    private static final String sortKey = "TimeStamp";
    private static final String attributeMessage = "Message";

    private static final String attributeFirstName = "FirstName";
    private static final String attributeLastName = "LastName";
    private static final String attributePassword = "Password";
    private static final String attributeImageUrl = "ImageUrl";
    private static final String attributeFollowerCount = "FollowerCount";
    private static final String attributeFolloweeCount = "FolloweeCount";

    public static List<User> transformToUser(List<Map<String, String>> values) {
        List<User> newList = new ArrayList();
        for (int i = 0; i < values.size(); i++) {
            newList.add( toUser(values.get(i)) );
        }
        return newList;
    }

    private static User toUser(Map<String, String> retrievedUser) {
        User tempUser = new User();
        tempUser.setAlias(retrievedUser.get(partitionKey));
        tempUser.setFirstName(retrievedUser.get(attributeFirstName));
        tempUser.setLastName(retrievedUser.get(attributeLastName));
        tempUser.setFolloweeCount(retrievedUser.get(attributeFolloweeCount));
        tempUser.setFollowerCount(retrievedUser.get(attributeFollowerCount));
        tempUser.setImageUrl(retrievedUser.get(attributeImageUrl));
        return tempUser;
    }

    public static  List<Status> transformToStatus(List<Map<String, String>> values) {
        List<Status> newList = new ArrayList();
        for (int i = 0; i < values.size(); i++) {
            newList.add( toStatus(values.get(i) ));
        }
        return newList;
    }

    private static Status toStatus(Map<String, String> item) {
        return new Status(item.get(sortKey), item.get(attributeMessage), item.get(partitionKey));
    }
}
