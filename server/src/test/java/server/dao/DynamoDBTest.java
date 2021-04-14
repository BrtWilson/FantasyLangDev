package server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.dao.dbstrategies.ResultsPage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class DynamoDBTest {

    private static final String feedTableName = "Feeds";
    private static final String storiesTableName = "Stories";
    private static final String partitionKey = "Alias";
    private static final String sortKeyTime = "TimeStamp";
    private static final String attributeMessage = "Message";
    private static final String attributeStatusUser = "StatusUser";

    private static final String usersTableName = "Users";
    private static final String attributeFirstName = "FirstName";
    private static final String attributeLastName = "LastName";
    private static final String attributePassword = "Password";
    private static final String attributeImageUrl = "ImageUrl";
    private static final String attributeFollowerCount = "FollowerCount";
    private static final String attributeFolloweeCount = "FolloweeCount";

    private static final String followsTableName = "Follows";
    private static final String partionKeyFollows = "FollowerAlias";
    private static final String sortKeyFollows = "FolloweeAlias";

    private static final Integer PAGE_SIZE_DEFAULT = 10;
    private static Integer pageSize;

    private static final int uploadBatchSize = 25;

    private static final String authTableName = "AuthTokens";
    private static final String secondaryKeyAuth = "Token";
    private static final String attributeTime = "TimeStamp";
    private static final String aliasInTable = "@HarryPotter";


    @BeforeEach
    public void setup() {}

    /*String tableName,
     String key,
      List<String> partitionKeyList,
      String sortKey,
      List<String> sortKeyList,
      List<String> attributeNames,
      List<List<String>> attributeValuesList,
     int batchSize
     */
    @Test
    public void batchUploadVaryingAttributes() {
        
    }

    /*String tableName,
     String key,
      List<String> partitionKeyList,
      String sortKey,
      String sortKeyValue,
      List<String> attributeNames,
      List<String> attributeValues,
     int batchSize
     */
    @Test //like above test, but SortKeys are consistent -- E.G. Post to multiple Feeds
    public void batchUploadByPartition() {
        DynamoDBStrategy.batchUploadByPartition(feedTableName, partitionKey, followersAliases, sortKeyTime, timeStamp, attributeNames, attributeValues, uploadBatchSize);
    }

    /*String tableName,
     String targetAttribute,
     String attributeValue,
     int pageSize,
     String attributeToRetrieve,
     String lastRetrieved
     */
    @Test
    public void getListByString() {
        ResultsPage resultsPage = DynamoDBStrategy.getListByString(feedTableName, partitionKey, aliasWithFeed, pageSize, sortKeyTime, null);
    }

    /*
        String tableName,
        String targetAttribute
        String attributeValue,
        int pageSize,
        String attributeToRetrieve,
        String lastRetrieved,
        Boolean byIndex,
        String indexName
     */
    @Test
    public void getListByString_WithIndex() { // RETRIEVE FOLLOWERS
        ResultsPage resultsPage = DynamoDBStrategy.getListByString(followsTableName, sortKeyFollows, aliasWithFollowers, pageSize, partionKeyFollows, null, true, sortKeyFollows);
    }

    /*
    String tableName,
    String key,
    String keyValue,
    String sortKey,
    Object sortKeyValue
     */
    @Test
    public void deleteItemWithDualKey() {
        DynamoDBStrategy.deleteItemWithDualKey(followsTableName, partionKeyFollows, request.getCurrentUser(), sortKeyFollows, request.getOtherUser());
    }

    /*
    String tableName,
    String key,
    Object keyValue,
    ArrayList<String> attributes,
    ArrayList<String> attributeValues
     */
    @Test
    public void createItemWithAttributes() { // NEW USER
        DynamoDBStrategy.createItemWithAttributes(usersTableName, partitionKey, alias, attributes, attributeValues);
    }

    /*
    String tableName,
    String key,
    String keyValue,
    String sortKey,
    String sortKeyValue
     */
    @Test
    public void createItemWithDualKey() {
        DynamoDBStrategy.createItemWithDualKey(followsTableName, partionKeyFollows, obscureUserAlias1, sortKeyFollows, obscureUserAlias2);
    }

    /*
    String tableName,
    String key,
    String keyValue,
    String sortKey,
    String sortKeyValue,
    boolean withAttribute
    String attributeName,
    String attributeValue
     */
    @Test
    public void createItemWithDualKey_oneAttribute() {
        DynamoDBStrategy.createItemWithDualKey(authTableName, partitionKey, userAlias, secondaryKeyAuth,  token.getToken(), true, attributeTime, date);
    }

    /*
    String tableName,
    String key,
    String keyValue,
    String sortKey,
    String sortKeyValue,
    List<String> attributeNames,
    List<String> attributeValues,
     */
    @Test
    public void creatItemWithDualKeyAndAttributes() {
        DynamoDBStrategy.createItemWithDualKeyAndAttributes(feedTableName, partitionKey, aliasInTable, sortKeyTime, newTimeStampValue, attributeNamesFeed, attributeValues);
    }

    /*
    String tableName,
    String key,
    String keyValue
     */
    @Test
    public void basicGetItem() {
        Item retrievedUser = (Item) DynamoDBStrategy.basicGetItem(usersTableName, partitionKey, aliasInTable);
    }

    /*
    String tableName,
    String primaryKey,
    String pkeyValue,
    String sortKey,
    String sortValue
     */
    @Test
    public void basicGetItemWithDualKey() {
        Object followTableMatch = DynamoDBStrategy.basicGetItemWithDualKey(followsTableName, partionKeyFollows, request.getCurrentUser(), sortKeyFollows, request.getOtherUser());
    }

    /*
    String tableName,
    String key,
    String keyValue,
    String sortKey,
    Object sortKeyValue,
    String attribute,
    String newAttributeValue
     */
    @Test
    public void updateItemStringAttributeFromDualKey() {
        DynamoDBStrategy.updateItemStringAttributeFromDualKey(authTableName, partitionKey, authToken.getUserName(), secondaryKeyAuth, authToken.getToken(), attributeTime, currentTime.toString() );
    }

    /*
    String tableName,
    String key,
    String keyValue,
    String attribute,
    String newAttributeValue
     */
    @Test
    public void updateItemStringAttribute() {
        DynamoDBStrategy.updateItemStringAttribute(usersTableName, partitionKey, request.getOtherUser(), attributeFollowerCount, followerCount.toString());

    }

    /*
    String tableName,
    String key,
    String keyValue,
    String sortKey,
    String sortKeyValue,
    String desiredAttribute
     */
    @Test
    public void getBasicStringAttributeFromDualKey() {
        DynamoDBStrategy.getBasicStringAttributeFromDualKey(authTableName, partitionKey, authToken.getUserName(), secondaryKeyAuth, authToken.getToken(), attributeTime);
    }
}
