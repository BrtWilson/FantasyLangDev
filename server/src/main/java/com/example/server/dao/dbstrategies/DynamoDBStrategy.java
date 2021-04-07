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
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DynamoDBStrategy {

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion("us-east-1").build();
    private static DynamoDB dynamoDB = new DynamoDB(client);
    private static final String SERVER_SIDE_ERROR = "Server_Error";

    public static Item basicQueryWithKey(String targetTable, String key, String keyValue) throws Exception {

        Table table = dynamoDB.getTable(targetTable);

       /* These seem unnecessary with the .withHashKey() method
        HashMap<String, String> nameMap = new NameMap();
        nameMap.put("#key", primaryKeyName); // e.g. "#fr", "follower_handle");

        HashMap<String, Object> valueMap = new ValueMap();
        valueMap.put(":" + primaryKeyName, keyQueryContent); // e.g. ":follower_handle", "Jerry");
*/

        QuerySpec querySpec = new QuerySpec().withHashKey(key, keyValue);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        //Item item = null;

        System.out.println("Performing query");
        items = table.query(querySpec);
        iterator = items.iterator();
        return iterator.next();
    }
            /*while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getString("follower_handle") + ": " + item.getString("followee_handle"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to complete query for key: " + primaryKeyName + "; and content: " + keyQueryContent);
            System.err.println(e.getMessage());
            return null;
        }
    }

    /*public static ItemCollection<QueryOutcome> basicQueryWithKey(String targetTable, String primaryKeyName, String keyQueryContent) throws Exception {

        Table table = dynamoDB.getTable(targetTable);

        HashMap<String, String> nameMap = new NameMap();
        nameMap.put("#key", primaryKeyName); // e.g. "#fr", "follower_handle");

        HashMap<String, Object> valueMap = new ValueMap();
        valueMap.put(":" + primaryKeyName, keyQueryContent); // e.g. ":follower_handle", "Jerry");


        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#key = :" + primaryKeyName)
                .withNameMap(nameMap)
                .withValueMap(valueMap);

        //ItemCollection<QueryOutcome> items = null;
        //Iterator<Item> iterator = null;
        //Item item = null;

        try {
            System.out.println("Performing query");
            return table.query(querySpec);

            /*iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getString("follower_handle") + ": " + item.getString("followee_handle"));
            }

        }
        catch (Exception e) {
            System.err.println("Unable to complete query for key: " + primaryKeyName + "; and content: " + keyQueryContent);
            System.err.println(e.getMessage());
            return null;
        }
    }*/

    /**
     * TODO: Paged Response! Use this or the one above
     * Fetch the next page of visitors who have visited location
     *
     * @param tableName The Database Table being targeted
     * @param targetAttribute Name of the attribute of interest in the table, the attribute we use to identify the data to retrive
     * @param attributeValue The specific value being checked for in the attribute of interest
     * @param pageSize The maximum number of visitors to include in the result
     * @param attributeToRetrieve The attribute name of the lastRetrieved attribute type in the table of interest
     * @param lastRetrieved The last e.g. status date returned in the previous page of results
     * @return The next page of visitors who have visited location
     *
     * e.g. getListByString("Follows", "FollowerAlias", "LukeSkywalker", 10, "FolloweeAlias", null)
     */
    public static ResultsPage getListByString(String tableName, String targetAttribute, String attributeValue, int pageSize, String attributeToRetrieve, String lastRetrieved) {
        ResultsPage result = new ResultsPage();

        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#att", targetAttribute);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":match_attribute", new AttributeValue().withS(attributeValue));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                //.withIndexName(IndexName)
                .withKeyConditionExpression("#att = :match_attribute")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(pageSize);

        if (isNonEmptyString(lastRetrieved)) {
            Map<String, AttributeValue> lastKey = new HashMap<>();
            lastKey.put(targetAttribute, new AttributeValue().withS(attributeValue));
            lastKey.put(attributeToRetrieve, new AttributeValue().withS(lastRetrieved));

            queryRequest = queryRequest.withExclusiveStartKey(lastKey);
        }

        QueryResult queryResult = client.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items){
                String visitor = item.get(attributeToRetrieve).getS();
                result.addValue(visitor);
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            result.setLastKey(lastKey.get(attributeToRetrieve).getS());
        }

        return result;
    }

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }


    // Use this for removing a follow
    /**
     * Deletes an item of a specified dual key
     * @param tableName The Database Table being targeted
     * @param key The key that is used in the corresponding table. E.g. "Alias"
     * @param keyValue The value for the key to match in the query. E.g. for "Alias" could be "LukeSkywalker"
     * @param indexKey The secondary or index key name of the match object
     * @param indexKeyValue The secondary or index key value of the match object
     */
    public static void deleteItemWithDualKey(String tableName, String key, String keyValue, String indexKey, Object indexKeyValue) {
        Table table = dynamoDB.getTable(tableName);
        table.deleteItem(key, keyValue, indexKey, indexKeyValue);
    }

    //Use for Creating a Status object?
    /**
     * Creates a basic object with a single additional attribute
     * @param tableName The Database Table being targeted
     * @param key The key that is used in the corresponding table. E.g. "Alias"
     * @param keyValue The value for the key to match in the query. E.g. for "Alias" could be "LukeSkywalker"
     * @param attribute Attribute name for additional item attribute
     * @param attributeValue Value of the additional attribute
     */
    public static boolean createItem(String tableName, String key, Object keyValue, String attribute, Object attributeValue) {
        Table table = dynamoDB.getTable(tableName);

        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put(key, keyValue);
        infoMap.put(attribute, attributeValue);

        try {
            System.out.println("Adding a new item...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey(key, keyValue, attribute, attributeValue).withMap("info", infoMap));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
            return true;
        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + keyValue + " " + attributeValue);
            System.err.println(e.getMessage());
            return false;
        }
    }


    /**
     * Creates a basic object with a single additional attribute
     * @param tableName The Database Table being targeted
     * @param key The key that is used in the corresponding table. E.g. "Alias"
     * @param keyValue The value for the key to match in the query. E.g. for "Alias" could be "LukeSkywalker"
     * @param attributes Attribute names for all additional item attributes
     * @param attributeValues Values for each additional attribute
     */
    public static boolean createItemWithAttributes(String tableName, String key, Object keyValue, ArrayList<String> attributes, ArrayList<Object> attributeValues) {
        Table table = dynamoDB.getTable(tableName);

        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put(key, keyValue);
        int attValueListSize = attributeValues.size();
        for (int i = 0; i < attributes.size(); i++) {
            if (i < attValueListSize) {
                infoMap.put(attributes.get(i), attributeValues.get(i));
            }
        }

        System.out.println("Adding a new item...");

        PutItemOutcome outcome = table // TODO: DETERMINE USER OF INFOMAPS
                .putItem(new Item().withPrimaryKey(key, keyValue).withMap("info", infoMap));

        System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
        return true;
    }

    //Use for creating AuthToken
    /**
     * Creates an object for a table that requires secondary keys, and has one additional attribute
     * @param tableName The Database Table being targeted
     * @param key The key that is used in the corresponding table. E.g. "Alias"
     * @param keyValue The value for the key to match in the query. E.g. for "Alias" could be "LukeSkywalker"
     * @param indexKey The secondary or index key name of the match object
     * @param indexKeyValue The secondary or index key value of the match object
     * @param attribute Attribute name for additional item attribute
     * @param attributeValue Value of the additional attribute
     */
    public static void createItemWithDualKey(String tableName, String key, String keyValue, String indexKey, Object indexKeyValue, String attribute, String attributeValue) {
        Table table = dynamoDB.getTable(tableName);

        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put(key, keyValue);
        infoMap.put(indexKey, indexKeyValue);
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

    /**
     * Returns an object matching a key from the specified table
     * @param tableName The Database Table being targeted
     * @param key The key that is used in the corresponding table. E.g. "Alias"
     * @param keyValue The value for the key to match in the query. E.g. for "Alias" could be "LukeSkywalker"
     * @throws Exception
     * returns the desired object
     */
    //Use for GetUserByAlias
    public static Object basicGetItem(String tableName, String key, String keyValue) throws Exception {
        Table table = dynamoDB.getTable(tableName);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey(key, keyValue);

        try {
            System.out.println("Attempting to read the item...");
            Item outcome = table.getItem(spec);
            System.out.println("GetItem succeeded: " + outcome);
            return outcome;

        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + keyValue);
            System.err.println(e.getMessage());
            return e;
        }
    }

    /**
     * Alters
     * @param tableName The Database Table being targeted
     * @param key The key that is used in the corresponding table. E.g. "Alias"
     * @param keyValue The value for the key to match in the query. E.g. for "Alias" could be "LukeSkywalker"
     * @param indexKey The secondary or index key name of the match object
     * @param indexKeyValue The secondary or index key value of the match object
     * @param attribute The attribute name to be targeted for update
     * @param newAttributeValue The new value for the targeted attribute (a String)
     * @throws Exception
     */
    //Use for update Authorization
    public static void updateItemStringAttributeFromDualKey(String tableName, String key, String keyValue, String indexKey, Object indexKeyValue, String attribute, String newAttributeValue) throws Exception {
        Table table = dynamoDB.getTable("Movies");

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(key, keyValue, indexKey, indexKeyValue)
                .withUpdateExpression("set info." + attribute + " = :a")
                .withValueMap(new ValueMap().withString(":a", newAttributeValue))
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


    /**
     * Retrieves a single String-type value from an object matching a specified key
     * @param tableName The Database Table being targeted
     * @param key The key that is used in the corresponding table. E.g. "Alias"
     * @param keyValue The value for the key to match in the query. E.g. for "Alias" could be "LukeSkywalker"
     * @param desiredAttribute Attribute name in table of attribute requested
     * @return Requested string type attribute
     */
    //desiredAttribute specified
    public static String getBasicStringAttribute(String tableName, String key, String keyValue, String desiredAttribute) {
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
    public static String getBasicStringAttributeFromDualKey(String tableName, String key, String keyValue, String indexKey, String indexKeyValue) {
        return getBasicStringAttributeFromDualKey(tableName, key, keyValue, indexKey, indexKeyValue, indexKey);
    }

    //desiredAttribute specified
    public static String getBasicStringAttributeFromDualKey(String tableName, String key, String keyValue, String indexKey, String indexKeyValue, String desiredAttribute) {
        Table table = dynamoDB.getTable(tableName);

        Item item = table.getItem(key, keyValue, indexKey, indexKeyValue);
        if (item == null) {
            return null;
        }
        else {
            return item.getString(desiredAttribute);
        }
    }
}