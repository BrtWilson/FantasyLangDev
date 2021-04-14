package server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.dao.dbstrategies.ResultsPage;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

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
    private static final String aliasInTable = "@AshAhketchum";
    private static final String obscureAlias1 = "@TheForsaken";
    private static final String obscureAlias2 = "@TheScourge";
    private static final String tempAlias = "@Mugwarts";
    private static final String aliasWithFeed = "@AshAhketchum";
    private static final String aliasWithFollowers = "@HarryPotter";
    private static final String followerInTable = "@user1229";
    private static final String followeeInTable = "@HarryPotter";
    private static final String aliasWithToken = "@CairneBloodhoof";
    private static final String tokenInTable = "abcdefghijkm";


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
        //DynamoDBStrategy.batchUploadByPartition(feedTableName, partitionKey, followersAliases, sortKeyTime, timeStamp, attributeNames, attributeValues, uploadBatchSize);
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
        ResultsPage resultsPage = DynamoDBStrategy.getListByString(feedTableName, partitionKey, aliasWithFeed, pageSize, sortKeyTime, "");
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
        ResultsPage resultsPage = DynamoDBStrategy.getListByString(followsTableName, sortKeyFollows, aliasWithFollowers, pageSize, partionKeyFollows, "", true, sortKeyFollows);
    }

    /*
    String tableName,
    String key,
    String keyValue,
    String sortKey,
    Object sortKeyValue
     */
    //@Test
    public void deleteItemWithDualKey_Auth(String date) {
        DynamoDBStrategy.deleteItemWithDualKey(followsTableName, partionKeyFollows, obscureAlias1, sortKeyFollows, date);
    }

    public void deleteItemWithDualKey_Follows() {
        DynamoDBStrategy.deleteItemWithDualKey(followsTableName, partionKeyFollows, obscureAlias1, sortKeyFollows, obscureAlias2);
    }

    @Test
    public void createAndDelete_ItemTests() {
        createItemWithAttributes();
        createItemWithDualKey_Follows();
        String date = createItemWithDualKey_oneAttribute_Auth();

        deleteItemWithDualKey_Follows();
        deleteItemWithDualKey_Auth(date);
        DynamoDBStrategy.deleteItem(usersTableName, partitionKey, tempAlias);
    }


    /*
    String tableName,
    String key,
    Object keyValue,
    ArrayList<String> attributes,
    ArrayList<String> attributeValues
     */
    //@Test
    public void createItemWithAttributes() { // NEW USER
        String alias = tempAlias;
        String firstName = "Muglin";
        String lastName = "Warthog";
        String password = "lostUnder";
        String imageUrl = "uncoded";
        //User user = new User(firstName, lastName, alias, imageUrl);
        //user.setPassword(password);

        ArrayList<String> attributes = new ArrayList<>();
        attributes.add(attributeFirstName);
        attributes.add(attributeLastName);
        attributes.add(attributePassword);
        attributes.add(attributeImageUrl);
        attributes.add(attributeFollowerCount);
        attributes.add(attributeFolloweeCount);

        ArrayList<String> attributeValues = new ArrayList<>();
        attributeValues.add(firstName);
        attributeValues.add(lastName);
        attributeValues.add(password);
        attributeValues.add(imageUrl);
        attributeValues.add("0");
        attributeValues.add("0");
        DynamoDBStrategy.createItemWithAttributes(usersTableName, partitionKey, tempAlias, attributes, attributeValues);
    }

    /*
    String tableName,
    String key,
    String keyValue,
    String sortKey,
    String sortKeyValue
     */
    //@Test
    public void createItemWithDualKey_Follows() {
        DynamoDBStrategy.createItemWithDualKey(followsTableName, partionKeyFollows, obscureAlias1, sortKeyFollows, obscureAlias2);
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
    //@Test
    public String createItemWithDualKey_oneAttribute_Auth() {
        AuthToken token = new AuthToken(obscureAlias1);
        String date = new Timestamp(System.currentTimeMillis()).toString();
        DynamoDBStrategy.createItemWithDualKey(authTableName, partitionKey, obscureAlias1, secondaryKeyAuth,  token.getToken(), true, attributeTime, date);
        return date;
    }

    /* DEPRACATED FOR SQS POSTING
    String tableName,
    String key,
    String keyValue,
    String sortKey,
    String sortKeyValue,
    List<String> attributeNames,
    List<String> attributeValues,
     */
    //@Test
//    public void createItemWithDualKeyAndAttributes() {
//        DynamoDBStrategy.createItemWithDualKeyAndAttributes(feedTableName, partitionKey, aliasInTable, sortKeyTime, newTimeStampValue, attributeNamesFeed, attributeValues);
//    }

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
        Object followTableMatch = DynamoDBStrategy.basicGetItemWithDualKey(followsTableName, partionKeyFollows, followerInTable, sortKeyFollows, followeeInTable);
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
    public void updateItemStringAttributeFromDualKey() throws Exception {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        DynamoDBStrategy.updateItemStringAttributeFromDualKey(authTableName, partitionKey, aliasWithToken, secondaryKeyAuth, tokenInTable, attributeTime, currentTime.toString() );
    }

    /*
    String tableName,
    String key,
    String keyValue,
    String attribute,
    String newAttributeValue
     */
    @Test
    public void updateItemStringAttribute() throws Exception {
        String newFollowCount = "32";
        DynamoDBStrategy.updateItemStringAttribute(usersTableName, partitionKey, aliasInTable, attributeFollowerCount, newFollowCount);

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
        DynamoDBStrategy.getBasicStringAttributeFromDualKey(authTableName, partitionKey, aliasWithToken, secondaryKeyAuth, tokenInTable, attributeTime);
    }
}
