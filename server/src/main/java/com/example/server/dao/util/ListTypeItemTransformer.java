package com.example.server.dao.util;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;

import java.util.ArrayList;
import java.util.List;

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

    public static List<User> transformToUser(List<Object> values) {
        List<User> newList = new ArrayList();
        for (int i = 0; i < values.size(); i++) {
            newList.add( toUser(values.get(i)) );
        }
        return newList;
    }

    private static User toUser(Item retrievedUser) {
        User tempUser = new User();
        tempUser.setAlias(retrievedUser.getString(partitionKey));
        tempUser.setFirstName(retrievedUser.getString(attributeFirstName));
        tempUser.setLastName(retrievedUser.getString(attributeLastName));
        tempUser.setFolloweeCount(retrievedUser.getString(attributeFolloweeCount));
        tempUser.setFollowerCount(retrievedUser.getString(attributeFollowerCount));
        tempUser.setImageUrl(retrievedUser.getString(attributeImageUrl));
        return tempUser;
    }

    public static  List<Status> transformToStatus(List<Object> values) {
        List<Status> newList = new ArrayList();
        for (int i = 0; i < values.size(); i++) {
            newList.add( toStatus(values.get(i) ));
        }
        return newList;
    }

    private static Status toStatus(Item item) {
        return new Status(item.getString(sortKey), item.getString(attributeMessage), item.getString(partitionKey));
    }
}
