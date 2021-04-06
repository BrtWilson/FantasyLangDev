package com.example.server.dao.dbstrategies;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DynamoDBStrategy {

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion("us-east-1").build();
    private static DynamoDB dynamoDB = new DynamoDB(client);

    //Needs to be updated to Paginated Query
    public static void basicQuery(String targetTable, String primaryKeyName, String keyQueryContent) throws Exception {

        Table table = dynamoDB.getTable(targetTable);

        HashMap<String, String> nameMap = new NameMap();
        nameMap.put("#key", primaryKeyName); // e.g. "#fr", "follower_handle");

        HashMap<String, Object> valueMap = new ValueMap();
        valueMap.put(":" + primaryKeyName, keyQueryContent); // e.g. ":follower_handle", "Jerry");


        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#key = :" + primaryKeyName)
                .withNameMap(nameMap)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            System.out.println("Performing query");
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getString("follower_handle") + ": " + item.getString("followee_handle"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to complete query for key: " + primaryKeyName + "; and content: " + keyQueryContent);
            System.err.println(e.getMessage());
        }
    }

    // Use this for removing a follow
    public void deleteItem(String tableName, String key, String keyValue, String attribute, Object attributeValue) {
        Table table = dynamoDB.getTable(tableName);
        table.deleteItem(key, keyValue, attribute, attributeValue);
    }

    //Use for Creating Authorization, and Creating a Status object
    public void createItem(String tableName, String key, Object keyValue, String attribute, Object attributeValue) {
        Table table = dynamoDB.getTable(tableName);

        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put(key, keyValue);
        infoMap.put(attribute, attributeValue);

        try {
            System.out.println("Adding a new item...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey(key, keyValue, attribute, attributeValue).withMap("info", infoMap));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + keyValue + " " + attributeValue);
            System.err.println(e.getMessage());
        }
    }

    //Use for GetUserByAlias
    public static void basicGetItem(String tableName, String key, String keyValue) throws Exception {
        Table table = dynamoDB.getTable(tableName);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey(key, keyValue);

        try {
            System.out.println("Attempting to read the item...");
            Item outcome = table.getItem(spec);
            //TODO: return outcome?
            System.out.println("GetItem succeeded: " + outcome);

        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + keyValue);
            System.err.println(e.getMessage());
        }

    }

    //Use for update Authorization
    public static void updateItemStringAttributeFromDualKey(String tableName, String key, String keyValue, String indexKey, Object indexKeyValue, String attribute, String newAttrbiuteValue) throws Exception {
        Table table = dynamoDB.getTable("Movies");

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(key, keyValue, indexKey, indexKeyValue)
                .withUpdateExpression("set info." + attribute + " = :a")
                .withValueMap(new ValueMap().withString(":a", newAttrbiuteValue))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: " + keyValue + " " + indexKeyValue);
            System.err.println(e.getMessage());
        }
    }

    //attribute == desiredAttribute
    public String getBasicStringAttribute(String tableName, String key, String keyValue, String attribute, String attributeValue) {
        return getBasicStringAttribute( tableName, key, keyValue, attribute, attributeValue, attribute);
    }

    //desiredAttribute specified
    public String getBasicStringAttribute(String tableName, String key, String keyValue, String attribute, String attributeValue, String desiredAttribute) {
        Table table = dynamoDB.getTable(tableName);

        Item item = table.getItem(key, keyValue);
        if (item == null) {
            return null;
        }
        else {
            return item.getString(desiredAttribute);
        }
    }



    // PROBABLY NOT USED AS USES KEY/ATTRIBUTE SETUP (rather than single key)
    // ***
    //attribute == desiredAttribute
    public String getBasicStringAttributeFromDualKey(String tableName, String key, String keyValue, String attribute, String attributeValue) {
        return getBasicStringAttributeFromDualKey(tableName, key, keyValue, attribute, attributeValue, attribute);
    }

    //desiredAttribute specified
    public String getBasicStringAttributeFromDualKey(String tableName, String key, String keyValue, String attribute, String attributeValue, String desiredAttribute) {
        Table table = dynamoDB.getTable(tableName);

        Item item = table.getItem(key, keyValue, attribute, attributeValue);
        if (item == null) {
            return null;
        }
        else {
            return item.getString(desiredAttribute);
        }
    }
}
