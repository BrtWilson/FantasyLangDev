package com.example.server.dao.dbstrategies;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DynamoDBStrategy {

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    private static DynamoDB dynamoDB = new DynamoDB(client);
    private static final String SERVER_SIDE_ERROR = "Server_Error";

    /**
     * Uploads a batch of items sharing attributes to a given table
     * Note that the only thing differing from item to item is the Partition Key
     * Allows for Dual Key or single key table entries. Enter "null" for both sort key parameters if it is for single ket entries.
     * @param tableName table to which the items are being sent
     * @param key name of the table's Partition Key
     * @param partitionKeyList the list of partition key values that distinguish the items
     * @param sortKey the sort key name for this table
     * @param sortKeyList the list of sort keys corresponding to each item
     * @param attributeNames the list of attribute names for these items
     * @param attributeValuesList a list of the values for each attribute, unique to each item
     * @param batchSize the to upload each batch as
     */
    public static void batchUploadVaryingAttributes(String tableName, String key, List<String> partitionKeyList, String sortKey, List<String> sortKeyList, List<String> attributeNames, List<List<String>> attributeValuesList, int batchSize) {
        TableWriteItems items = new TableWriteItems(tableName);
        for(int i = 0 ; i < partitionKeyList.size(); i ++){
            //later add this with BatchAdd
            String sortKeyValue = null;
            if (sortKeyList != null) {
                sortKeyValue = sortKeyList.get(i);
            }
            items = bacthUpload(items, tableName, key, partitionKeyList.get(i), sortKey, sortKeyValue, attributeNames, attributeValuesList.get(i), batchSize);
        }

        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWriter(items);
        }
    }

    /**
     * Uploads a batch of items sharing attributes to a given table
     * Note that the only thing differing from item to item is the Partition Key
     * Allows for Dual Key or single key table entries. Enter "null" for both sort key parameters if it is for single ket entries.
     * @param tableName table to which the items are being sent
     * @param key name of the table's Partition Key
     * @param partitionValue the value of the common partition key
     * @param sortKey the sort key name for this table
     * @param sortKeyList  the list of partition key values that distinguish the items
     * @param attributeNames the list of attribute names for these items
     * @param attributeValues the values for each attribute
     * @param batchSize the to upload each batch as
     */
    public static void batchUploadBySortKey(String tableName, String key, String partitionValue, String sortKey, List<String> sortKeyList, List<String> attributeNames, List<String> attributeValues, int batchSize) {
        TableWriteItems items = new TableWriteItems(tableName);
        for(int i = 0 ; i < sortKeyList.size(); i ++){
            //later add this with BatchAdd
            items = bacthUpload(items, tableName, key, partitionValue, sortKey, sortKeyList.get(i), attributeNames, attributeValues, batchSize);
        }

        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWriter(items);
        }
    }

    /**
     * Uploads a batch of items sharing attributes to a given table
     * Note that the only thing differing from item to item is the Partition Key
     * Allows for Dual Key or single key table entries. Enter "null" for both sort key parameters if it is for single ket entries.
     * @param tableName table to which the items are being sent
     * @param key name of the table's Partition Key
     * @param partitionKeyList the list of partition key values that distinguish the items
     * @param sortKey the sort key name for this table
     * @param sortKeyValue the value of the common sort key
     * @param attributeNames the list of attribute names for these items
     * @param attributeValues the values for each attribute
     * @param batchSize the to upload each batch as
     */
    public static void batchUploadByPartition(String tableName, String key, List<String> partitionKeyList, String sortKey, String sortKeyValue, List<String> attributeNames, List<String> attributeValues, int batchSize) {
        TableWriteItems items = new TableWriteItems(tableName);
        for(int i = 0 ; i < partitionKeyList.size(); i ++){
            //later add this with BatchAdd
            items = bacthUpload(items, tableName, key, partitionKeyList.get(i), sortKey, sortKeyValue, attributeNames, attributeValues, batchSize);
        }

        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWriter(items);
        }
    }

    public static TableWriteItems bacthUpload(TableWriteItems items, String tableName, String key, String partitionValue, String sortKey, String sortKeyValue, List<String> attributeNames, List<String> attributeValues, int batchSize) {
        Item item;
        if (sortKey == null) {
            item = new Item().withPrimaryKey(key, partitionValue);
        } else {
            item = new Item().withPrimaryKey(key, partitionValue, sortKey, sortKeyValue);
        }
        for (int j = 0; j < attributeValues.size(); j++) {
            //Note that these will be the same for each item
            if (j < attributeNames.size()) {
                item.withString(attributeNames.get(j), attributeValues.get(j));
            }
        }
        //items = new TableWriteItems(tableName);
        items.addItemToPut(item);
        if(items.getItemsToPut() != null && items.getItemsToPut().size() == batchSize){
            //then add a list for the Batch
            loopBatchWriter(items);
        }
        return items;
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

    public static ResultsPage getListByString(String tableName, String targetAttribute, String attributeValue, int pageSize, String attributeToRetrieve, String lastRetrieved) {
        return getListByString(tableName, targetAttribute, attributeValue, pageSize, attributeToRetrieve, lastRetrieved, false, null);
    }

    /**
     *
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
    public static ResultsPage getListByString(String tableName, String targetAttribute, String attributeValue, int pageSize, String attributeToRetrieve, String lastRetrieved, Boolean byIndex, String indexName) {
        ResultsPage result = new ResultsPage();

        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#att", targetAttribute);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":match_attribute", new AttributeValue().withS(attributeValue));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                //.withSortName(SortName)
                .withKeyConditionExpression("#att = :match_attribute")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(pageSize);

        if (byIndex){  // use for FOLLOWS search by index :: USED FOR FOLLOWING
                queryRequest = queryRequest.withIndexName(indexName);
        }
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
                AttributeValue returnedObject = item.get(attributeToRetrieve);
                Map<String, String> newItem = convertMap_to_WithStrings(item);
                result.addValue(newItem);
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            result.setLastKey(lastKey.get(attributeToRetrieve).getS());
        }

        return result;
    }

    private static Map<String, String> convertMap_to_WithStrings(Map<String, AttributeValue> item) {
        Map<String, String> newMap = new HashMap<>();
        for (Map.Entry<String, AttributeValue> entry : item.entrySet()){
            newMap.put(entry.getKey(), entry.getValue().toString());
        }
        return newMap;
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
     * @param sortKey The secondary or sort key name of the match object
     * @param sortKeyValue The secondary or sort key value of the match object
     */
    public static void deleteItemWithDualKey(String tableName, String key, String keyValue, String sortKey, Object sortKeyValue) {
        Table table = dynamoDB.getTable(tableName);
        table.deleteItem(key, keyValue, sortKey, sortKeyValue);
    }

    /**
     * Deletes an item of a specified dual key
     * @param tableName The Database Table being targeted
     * @param key The key that is used in the corresponding table. E.g. "Alias"
     * @param keyValue The value for the key to match in the query. E.g. for "Alias" could be "LukeSkywalker"
     */
    public static void deleteItem(String tableName, String key, String keyValue) {
        Table table = dynamoDB.getTable(tableName);
        table.deleteItem(key, keyValue);
    }

    /**
     * Creates a basic object with a single additional attribute
     * @param tableName The Database Table being targeted
     * @param key The key that is used in the corresponding table. E.g. "Alias"
     * @param keyValue The value for the key to match in the query. E.g. for "Alias" could be "LukeSkywalker"
     * @param attributes Attribute names for all additional item attributes
     * @param attributeValues Values for each additional attribute
     */
    public static boolean createItemWithAttributes(String tableName, String key, Object keyValue, ArrayList<String> attributes, ArrayList<String> attributeValues) {
        Table table = dynamoDB.getTable(tableName);

        //final Map<String, Object> infoMap = new HashMap<String, Object>();
        //infoMap.put(key, keyValue);
        int attValueListSize = attributeValues.size();
        //for (int i = 0; i < attributes.size(); i++) {
            //if (i < attValueListSize) {
            //    infoMap.put(attributes.get(i), attributeValues.get(i));
          //  }
        //}

        System.out.println("Adding a new item...");

        Item itemToPut = new Item().withPrimaryKey(key, keyValue);
        for (int i = 0; i < attributes.size(); i++) {
            if (i < attValueListSize) {
                itemToPut.withString(attributes.get(i), attributeValues.get(i));
            }
        }

        PutItemOutcome outcome = table.putItem(itemToPut);
                                 //.withMap("info", infoMap));

        System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
        return true;
    }

    public static void createItemWithDualKey(String tableName, String key, String keyValue, String sortKey, String sortKeyValue) {
        createItemWithDualKey(tableName, key, keyValue, sortKey, sortKeyValue, false, null, null);
    }
        //Use for creating AuthToken
    /**
     * Creates an object for a table that requires secondary keys, and has one additional attribute
     * @param tableName The Database Table being targeted
     * @param key The key that is used in the corresponding table. E.g. "Alias"
     * @param keyValue The value for the key to match in the query. E.g. for "Alias" could be "LukeSkywalker"
     * @param sortKey The secondary or sort key name of the match object
     * @param sortKeyValue The secondary or sort key value of the match object
     */
    public static void createItemWithDualKey(String tableName, String key, String keyValue, String sortKey, String sortKeyValue, boolean withAttributes, String attributeName, String attributeValue) {
        Table table = dynamoDB.getTable(tableName);

        /*
        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put(key, keyValue);
        infoMap.put(sortKey, sortKeyValue);
        infoMap.put(attribute, attributeValue);*/

        try {
            System.out.println("Adding a new item...");
            Item itemToPut = new Item().withPrimaryKey(key, keyValue, sortKey, sortKeyValue);
            if (withAttributes) {
                itemToPut = itemToPut.with(attributeName, attributeValue);
            }
            PutItemOutcome outcome = table
                    .putItem(itemToPut);

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + keyValue + " " + sortKeyValue);
            System.err.println(e.getMessage());
        }
    }

    //Probably not needed -- DEPRACATED FOR SQS POSTING
    public static void createItemWithDualKeyAndAttributes(String tableName, String key, String keyValue, String sortKey, String sortKeyValue, List<String> attributeNames, List<String> attributeValues) {
        Table table = dynamoDB.getTable(tableName);
        int attValueListSize = attributeValues.size();

        try {
            System.out.println("Adding a new item...");

            Item itemToPut = new Item().withPrimaryKey(key, keyValue, sortKey, sortKeyValue);
            for (int i = 0; i < attributeNames.size(); i++) {
                if (i < attValueListSize) {
                    itemToPut.withString(attributeNames.get(i), attributeValues.get(i));
                }
            }

            PutItemOutcome outcome = table
                    .putItem(itemToPut);

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + keyValue + " " + sortKeyValue);
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
    public static Item basicGetItem(String tableName, String key, String keyValue) {
        Table table = dynamoDB.getTable(tableName);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey(key, keyValue);

        System.out.println("Attempting to read the item...");
        Item outcome = table.getItem(spec);
        System.out.println("GetItem succeeded: " + outcome);
        return outcome;

    }

    public static Item basicGetItemWithDualKey(String tableName, String primaryKey, String pkeyValue, String sortKey, String sortValue) {
        Table table = dynamoDB.getTable(tableName);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey(primaryKey, pkeyValue, sortKey, sortValue);

        System.out.println("Attempting to read the item...");
        Item outcome = table.getItem(spec);
        System.out.println("GetItem succeeded: " + outcome);
        return outcome;
    }

    /**
     * Alters
     * @param tableName The Database Table being targeted
     * @param key The key that is used in the corresponding table. E.g. "Alias"
     * @param keyValue The value for the key to match in the query. E.g. for "Alias" could be "LukeSkywalker"
     * @param sortKey The secondary or sort key name of the match object
     * @param sortKeyValue The secondary or sort key value of the match object
     * @param attribute The attribute name to be targeted for update
     * @param newAttributeValue The new value for the targeted attribute (a String)
     * @throws Exception
     */
    //Use for update Authorization
    public static void updateItemStringAttributeFromDualKey(String tableName, String key, String keyValue, String sortKey, Object sortKeyValue, String attribute, String newAttributeValue) throws Exception {
        Table table = dynamoDB.getTable("Movies");

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(key, keyValue, sortKey, sortKeyValue)
                .withUpdateExpression("set info." + attribute + " = :a")
                .withValueMap(new ValueMap().withString(":a", newAttributeValue))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: " + keyValue + " " + sortKeyValue);
            System.err.println(e.getMessage());
        }
    }

    //Use for update Authorization
    public static void updateItemStringAttribute(String tableName, String key, String keyValue,  String attribute, String newAttributeValue) throws Exception {
        Table table = dynamoDB.getTable("Movies");

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(key, keyValue)
                .withUpdateExpression("set " + attribute + " = :a")
                .withValueMap(new ValueMap().withString(":a", newAttributeValue))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Unable to update item: " + keyValue);
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
    public static String getBasicStringAttributeFromDualKey(String tableName, String key, String keyValue, String sortKey, String sortKeyValue) {
        return getBasicStringAttributeFromDualKey(tableName, key, keyValue, sortKey, sortKeyValue, sortKey);
    }

    //desiredAttribute specified
    public static String getBasicStringAttributeFromDualKey(String tableName, String key, String keyValue, String sortKey, String sortKeyValue, String desiredAttribute) {
        Table table = dynamoDB.getTable(tableName);

        Item item = table.getItem(key, keyValue, sortKey, sortKeyValue);
        if (item == null) {
            return null;
        }
        else {
            return item.getString(desiredAttribute);
        }
    }
}
