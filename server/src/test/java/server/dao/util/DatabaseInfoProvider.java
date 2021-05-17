package server.dao.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.shared.model.domain.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInfoProvider {
    private static final String userTableName = "Users";
    private static final String partitionKey = "Alias";
    private static final String attributeFirstName = "FirstName";
    private static final String attributeLastName = "LastName";
    private static final String attributeImageUrl = "ImageUrl";
    private static final List<String> userAttributeNames = new ArrayList<String>() {{
        add(attributeFirstName);
        add(attributeLastName);
        add(attributeImageUrl);
    }};

    private static final String feedTableName = "Feeds";
    private static final String storyTableName = "Stories";
    private static final String statusSortKey = "TimeStamp";
    private static final String attributeStatusMessage = "Message";
    private static final String attributeFeedStatusAlias = "StatusUser";
    private static final List<String> storyAttributeNames = new ArrayList<String>() {{
        add(attributeStatusMessage);
    }};
    private static final List<String> feedAttributeNames = new ArrayList<String>() {{
        add(attributeStatusMessage);
        add(attributeFeedStatusAlias);
    }};

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-east-1")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);
    private static final String standardUserAlias = "@HarryPotter";

    private static void uploadOurUsers() {
        List<User> ourUsers = getOurDefaultUsers();
        List<String> ourUserAliases = getUserAliases(ourUsers);
        List<List<String>> ourUserAttributes = getUserAttributes(ourUsers);
        DynamoDBStrategy.batchUploadVaryingAttributes(userTableName, partitionKey, ourUserAliases, null, null, userAttributeNames, ourUserAttributes, 25);
    }
    private static void uploadOurFeeds() {
        List<User> ourUsers = getOurDefaultUsers();
        List<Status> ourFeed = getFeed(ourUsers);
        for (int i = 0; i < ourFeed.size(); i++) {
            List<String> ourUserAliases = getIdenticalAliasesList(ourUsers.get(i).getAlias(), ourFeed.size());
            List<String> ourStatusAliases = getStatusAliases(ourUsers);
            List<List<String>> ourUserAttributes = getStatusAttributes(ourFeed, true);
            DynamoDBStrategy.batchUploadVaryingAttributes(feedTableName, partitionKey, ourUserAliases, statusSortKey, ourStatusAliases, feedAttributeNames, ourUserAttributes, 25);
        }
    }
//    private static List<String> getIdenticalAliasesList(List<User> ourUsers, int i) {
//        List<String> aliasList = new ArrayList<>();
//        for (int j = 0; j < ourUsers.size(); j++) {
//            aliasList.add(ourUsers.get(i).getAlias());
//        }
//        return aliasList;
//    }

    private static List<String> getIdenticalAliasesList(String userAlias, int i) {
        List<String> aliasList = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            aliasList.add(userAlias);
        }
        return aliasList;
    }

    private static void uploadOurStory(String userAlias) {
        List<Status> storyStatuses = getStory(userAlias);
        List<String> ourSingleAliasList = getIdenticalAliasesList(userAlias, storyStatuses.size());
        List<String> ourStatusAliases = getStatusDates(storyStatuses);
        List<List<String>> ourUserAttributes = getStatusAttributes(storyStatuses, false);
        DynamoDBStrategy.batchUploadVaryingAttributes(storyTableName, partitionKey, ourSingleAliasList, statusSortKey, ourStatusAliases, feedAttributeNames, ourUserAttributes, 25);
    }

    private static List<List<String>> getStatusAttributes(List<Status> storyStatuses, Boolean isFeed) {
        List<List<String>> usersAttributesList = new ArrayList<>();
        for (int i = 0; i < storyStatuses.size(); i++) {
            List<String> attributeValues = new ArrayList<>();
            attributeValues.add(storyStatuses.get(i).getMessage());
            if (isFeed) {
                attributeValues.add(storyStatuses.get(i).getCorrespondingUserAlias());
            }
            usersAttributesList.add(attributeValues);
        }
        return usersAttributesList;
    }

    private static List<String> getStatusDates(List<Status> storyStatuses) {
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < storyStatuses.size(); i++) {
            dateList.add(storyStatuses.get(i).getDate());
        }
        return dateList;
    }

    private static List<List<String>> getUserAttributes(List<User> ourUsers) {
        List<List<String>> usersAttributesList = new ArrayList<>();
        for (int i = 0; i < ourUsers.size(); i++) {
            List<String> attributeValues = new ArrayList<>();
            attributeValues.add(ourUsers.get(i).getFirstName());
            attributeValues.add(ourUsers.get(i).getLastName());
            attributeValues.add(ourUsers.get(i).getImageUrl());
            usersAttributesList.add(attributeValues);
        }
        return usersAttributesList;
    }

    private static List<String> getUserAliases(List<User> ourUsers) {
        List<String> userAliases = new ArrayList<>();
        for (int i = 0; i < ourUsers.size(); i++) {
            userAliases.add(ourUsers.get(i).getAlias());
        }
        return userAliases;
    }

    public static List<String> getStatusAliases(List<User> ourUsers) {
        List<String> userAliases = new ArrayList<>();
        for (int i = 0; i < ourUsers.size(); i++) {
            userAliases.add(ourUsers.get(i).getAlias());
        }
        return userAliases;
    }

    public static List<User> getOurDefaultUsers() {
        List<User> users = new ArrayList<>();
        User userTemp;
        userTemp = new User("@AangJones",
                "Aang",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/kuzonfire.jgp",
                "Jones", 0);
        users.add(userTemp);
        userTemp = new User("@AmyAmes",
                "Amy",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/vulpix.jgp",
                "Ames", 0);
        users.add(userTemp);
        userTemp = new User("@AnakinSkywalker",
                "Anakin",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/anakinskywalker.png",
                "Skywalker", 0);
        users.add(userTemp);
        userTemp = new User("@AshAhketchum",
                "Ash",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/ashketchum.png",
                "Ahketchum", 0);
        users.add(userTemp);
        userTemp = new User("@AzulaFirestart",
                "Azula",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/jigglypuff.webp",
                "Firestart", 0);
        users.add(userTemp);
        userTemp = new User("@BobBross",
                "Bob",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/bobross.png",
                "Bross", 0);
        users.add(userTemp);
        userTemp = new User("@BonnieBetty",
                "Bonnie",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/jigglypuff.webp",
                "Betty", 0);
        users.add(userTemp);
        userTemp = new User("@CairneBloodhoof",
                "Cairne",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/cairnebloodhoof.jgp",
                "Bloodhoof", 0);
        users.add(userTemp);
        userTemp = new User("@CaptainChris",
                "Captain",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/bulbasaur.png",
                "Chris", 0);
        users.add(userTemp);
        userTemp = new User("@ChancellorPalpatine",
                "Chancellor",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/chancellorpalpatine.png",
                "Palpatine", 0);
        users.add(userTemp);
        userTemp = new User("@CindyCoats",
                "Cindy",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/jigglypuff.webp",
                "Coats", 0);
        users.add(userTemp);
        userTemp = new User("@ClericUther",
                "Cleric",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/humanfootman.png",
                "Uther", 0);
        users.add(userTemp);
        userTemp = new User("@CountDooku",
                "Count",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/countdooku.png",
                "Dooku", 0);
        users.add(userTemp);
        userTemp = new User("@DanDumbledoor",
                "Dan",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/snorlax.jgp",
                "Dumbledoor", 0);
        users.add(userTemp);
        userTemp = new User("@DarthMaul",
                "Darth",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/darthmaul.jgp",
                "Maul", 0);
        users.add(userTemp);
        userTemp = new User("@DarthPlagueis",
                "Darth",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/darthplagueis.png",
                "Plagueis", 0);
        users.add(userTemp);
        userTemp = new User("@DarthSidious",
                "Darth",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/darthsidious.jgp",
                "Sidious", 0);
        users.add(userTemp);
        userTemp = new User("@DeeDempsey",
                "Dee",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/vulpix.jgp",
                "Dempsey", 0);
        users.add(userTemp);
        userTemp = new User("@DracoMalfoy",
                "Draco",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/humanfootman.png",
                "Malfoy", 0);
        users.add(userTemp);
        userTemp = new User("@ElizabethEngle",
                "Elizabeth",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/jigglypuff.webp",
                "Engle", 0);
        users.add(userTemp);
        userTemp = new User("@ElliottEnderson",
                "Elliott",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/Zuko.png",
                "Enderson", 0);
        users.add(userTemp);
        userTemp = new User("@FranFranklin",
                "Fran",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/vulpix.jgp",
                "Franklin", 0);
        users.add(userTemp);
        userTemp = new User("@FrankFrandson",
                "Frank",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/Zuko.png",
                "Frandson", 0);
        users.add(userTemp);
        userTemp = new User("@FrostUndertaker",
                "Frost",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/snorlax.jgp",
                "Undertaker", 0);
        users.add(userTemp);
        userTemp = new User("@GaryGiovanni",
                "Gary",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/bulbasaur.png",
                "Giovanni", 0);
        users.add(userTemp);
        userTemp = new User("@GiovannaGiles",
                "Giovanna",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/vulpix.jgp",
                "Giles", 0);
        users.add(userTemp);
        userTemp = new User("@HagridGameskeeper",
                "Hagrid",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/snorlax.jgp",
                "Gameskeeper", 0);
        users.add(userTemp);
        userTemp = new User("@HanSolo",
                "Han",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/hansolo.jgp",
                "Solo", 0);
        users.add(userTemp);
        userTemp = new User("@HelenHopwell",
                "Helen",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/vulpix.jgp",
                "Hopwell", 0);
        users.add(userTemp);
        userTemp = new User("@IgorIsaacson",
                "Igor",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/humanfootman.png",
                "Isaacson", 0);
        users.add(userTemp);
        userTemp = new User("@KataraSmug",
                "Katara",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/saphirefire1.jgp",
                "Smug", 0);
        users.add(userTemp);
        userTemp = new User("@KinJonahs",
                "Kin",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/humanfootman.png",
                "Jonahs", 0);
        users.add(userTemp);
        userTemp = new User("@LukeSkywalker",
                "Luke",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/lukeskywalker.png",
                "Skywalker", 0);
        users.add(userTemp);
        userTemp = new User("@LunaLovegood",
                "Luna",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/lunalovegood.png",
                "Lovegood", 0);
        users.add(userTemp);
        userTemp = new User("@MaceWindu",
                "Mace",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/macewindu.jgp",
                "Windu", 0);
        users.add(userTemp);
        userTemp = new User("@Obi-wanKenobi",
                "Obi-wan",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/obiwankenobi.png",
                "Kenobi", 0);
        users.add(userTemp);
        userTemp = new User("@PrinceArthas",
                "Prince",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/princearthas.jgp",
                "Arthas", 0);
        users.add(userTemp);
        userTemp = new User("@SaphireFire",
                "Saphire",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/saphirefire1.jgp",
                "Fire", 0);
        users.add(userTemp);
        userTemp = new User("@SokkaSacapuntes",
                "Sokka",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/wangfire.jgp",
                "Sacapuntes", 0);
        users.add(userTemp);
        userTemp = new User("@SukiSacar",
                "Suki",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/327-3273611_katara-vector-katara.png",
                "Sacar", 0);
        users.add(userTemp);
        userTemp = new User("@TheFatherlordSmith",
                "TheFatherlord",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/firelord.png",
                "Smith", 0);
        users.add(userTemp);
        userTemp = new User("@TheSenate",
                "The",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/chancellorpalpatine.png",
                "Senate", 0);
        users.add(userTemp);
        userTemp = new User("@TophTheDestroyer",
                "Toph",
                "https://jamesblakebrytontweeterimages.s3.amazonaws.com/tophire.jgp",
                "TheDestroyer", 0);
        users.add(userTemp);

        return users;
    }

    public static List<Status> getStory(String userAlias) {
        List<Status> ourStory = new ArrayList<>();
        Status status1b = new Status("Avatar state, yip, yip!.", "1349333576",userAlias);
        ourStory.add(status1b);
        Status status2b = new Status("That's rough, buddy.", "1349334576",userAlias);
        ourStory.add(status2b);
        Status status3b = new Status("Maybe it's friendly!", "1349335576",userAlias);
        ourStory.add(status3b);
        Status status4b = new Status("It's the quenchiest!", "1349336576",userAlias);
        ourStory.add(status4b);
        Status status5b = new Status("The Boulder feels conflicted.", "1349337576", userAlias);
        ourStory.add(status5b);
        Status status6b = new Status("My cabbages! \n https://youtu.be/dQw4w9WgXcQ", "1349338576", userAlias);
        ourStory.add(status6b);
        Status status7b = new Status("No, I am Melon Lord.", "1349339576",userAlias);
        ourStory.add(status7b);
        Status status8b = new Status("@SparkySparkyBoomMan", "1349334576",userAlias);
        ourStory.add(status8b);
        Status status9b = new Status("https://youtu.be/vYud9sZ91Mc", "1349353576",userAlias);
        ourStory.add(status9b);
        Status status10b = new Status("But I don't want to cure cancer. I want to turn people into dinosaurs. \n https://youtu.be/HfoVqap3ar4", "1349363576",userAlias);
        ourStory.add(status10b);
        Status status11b = new Status("Tactical buttcheeks", "1349373576",userAlias);
        ourStory.add(status11b);
        Status status12b = new Status("Pocket sand!", "1349383576",userAlias);
        ourStory.add(status12b);
        Status status13b = new Status("In self defense \n A knife protects \n I bring my lunch \n No one suspects", "1349393576",userAlias);
        ourStory.add(status13b);
        Status status14b = new Status("And when it's time \n To end a life \n Deceptive fruit \n Banana knife.", "1349433576",userAlias);
        ourStory.add(status14b);
        Status status15b = new Status("I would like to see the baby.", "1349533576",userAlias);
        ourStory.add(status15b);
        return ourStory;
    }

    public static List<Status> getFeed(List<User> ourUsers) { //For "@AshAhketchum
        List<Status> ourFeed = new ArrayList<>();
        Status status1 = new Status("I have a sister.", "1349533576", "@CaptainChris");
        ourFeed.add(status1);
        Status status2 = new Status("@Luke, I am your father.", "1349553576", "@DanDumbledoor");
        ourFeed.add(status2);
        Status status3 = new Status("No, I am your father", "1349536576", "@HanSolo");
        ourFeed.add(status3);
        Status status4 = new Status("Oh, I am my daddy.", "1349333576", "@KataraSmug");
        ourFeed.add(status4);
        Status status5 = new Status("Wait, what?", "1349533586", "@TophTheDestroyer");
        ourFeed.add(status5);
        Status status6 = new Status("No tomatoes \n https://youtu.be/yRw1onpgFJA", "1349933576", "@SaphireFire");
        ourFeed.add(status6);
        Status status7 = new Status("It's over @Anakin.", "1349534576", "@IgorIsaacson");
        ourFeed.add(status7);
        Status status8 = new Status("I have the high ground.", "1329533576", "@KataraSmug");
        ourFeed.add(status8);
        Status status9 = new Status("You underestimate my power.", "1449533576m", "@DeeDempsey");
        ourFeed.add(status9);
        Status status10 = new Status("Don't try it.", "1349533576", "@ElliottEnderson");
        ourFeed.add(status10);
        Status status11 = new Status("You were my brother, @Anakin!", "1549533576", "@CairneBloodhoof");
        ourFeed.add(status11);
        Status status12 = new Status("You were supposed to bring balance to the Force!", "1349533577", "@DracoMalfoy");
        ourFeed.add(status12);
        Status status13 = new Status("You were to destroy the Sith, not join them!", "1349533586", "@ChancellorPalpatine");
        ourFeed.add(status13);
        Status status14 = new Status("I loved you like a brother.", "1349533586", "@DarthMaul");
        ourFeed.add(status14);
        Status status15 = new Status("Barbeque accident. \n https://youtu.be/_MHusGl9BeM", "1359533576", "@KinJonahs");
        ourFeed.add(status15);
        Status status16 = new Status("Hello there.", "1349573576", "@ElizabethEngle");
        ourFeed.add(status16);
        Status status17 = new Status("General Kenobi.", "1349534576", "@ElliottEnderson");
        ourFeed.add(status17);
        Status status18 = new Status("Come closer, my little friend.", "1349533574", "@SokkaSacapuntes");
        ourFeed.add(status18);
        Status status19 = new Status("These are not the droids you are looking for.", "1359533576", "@FrostUndertaker");
        ourFeed.add(status19);
        Status status20 = new Status("He's not worth anything to me dead.", "1349533526", "@DanDumbledoor");
        ourFeed.add(status20);
        return ourFeed;
    }

}
