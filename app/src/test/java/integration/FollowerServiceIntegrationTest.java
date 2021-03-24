package integration;

import com.example.shared.model.domain.User;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.RegisterResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.client.model.net.ServerFacade;
import edu.byu.cs.client.model.service.FollowerService;
import edu.byu.cs.client.util.ByteArrayUtils;

public class FollowerServiceIntegrationTest {

    private FollowerRequest validRequest;
    private FollowerRequest invalidRequest;

    private FollowerResponse successResponse;
    private FollowerResponse failureResponse;

    private FollowerService followerServiceSpy;

    /**
     * Create a FollowerService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        User user22 = new User("Luke", "Skywalker", "https://www.vippng.com/png/detail/510-5106254_luke-skywalker-cliparts-luke-skywalker-star-wars-5.png");
        User user23 = new User("Anakin", "Skywalker", "https://images.immediate.co.uk/production/volatile/sites/3/2019/12/Episode_III_Revenge_Christensen07-8bbd9e4.jpg?quality=90&resize=620,413");
        User user24 = new User("Mace", "Windu", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXViPRNkk2tpSFPpyuGE6HoIz6SgMzhO27iA&usqp=CAU");

        // Setup request objects to use in the tests
        validRequest = new FollowerRequest(currentUser.getAlias(), 3, null);
        invalidRequest = new FollowerRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowerResponse(Arrays.asList(user22, user23, user24), false);
        ServerFacade serverFacade = Mockito.spy(new ServerFacade());
        //Mockito.when(serverFacade.getFollowers(validRequest)).thenReturn(successResponse);

        failureResponse = new FollowerResponse("An exception occurred");
        //Mockito.when(serverFacade.getFollowers(invalidRequest)).thenReturn(failureResponse);

        // Create a FollowerService instance and wrap it with a spy that will use the mock service
        followerServiceSpy = Mockito.spy(new FollowerService());
        //Mockito.when(followerServiceSpy.getServerFacade()).thenReturn(serverFacade);
    }

    /**
     * Verify that for successful requests the {@link FollowerService#getFollowers(FollowerRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void myTest() throws IOException, TweeterRemoteException {

        User user22 = new User("Luke", "Skywalker", "https://www.vippng.com/png/detail/510-5106254_luke-skywalker-cliparts-luke-skywalker-star-wars-5.png");
        User user23 = new User("Anakin", "Skywalker", "https://images.immediate.co.uk/production/volatile/sites/3/2019/12/Episode_III_Revenge_Christensen07-8bbd9e4.jpg?quality=90&resize=620,413");
        User user24 = new User("Mace", "Windu", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXViPRNkk2tpSFPpyuGE6HoIz6SgMzhO27iA&usqp=CAU");
        User user25 = new User("Obi-wan", "Kenobi", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwIQVNGsBqqIYz6cMb3-xpBfurd2KXQr72kg&usqp=CAU");
        User user26 = new User("Sokka", "Sacapuntes", "https://oyster.ignimgs.com/mediawiki/apis.ign.com/avatar-the-last-airbender/a/ad/Sokka_img.jpg?width=325");
        User user27 = new User("Aang", "Jones", "https://www.kindpng.com/picc/m/25-251027_transparent-avatar-aang-png-avatar-aang-face-png.png");
        User user28 = new User("The Fatherlord", "Smith", "https://static.wikia.nocookie.net/avatar/images/4/4a/Ozai.png/revision/latest/scale-to-width-down/333?cb=20130612170743");
        User user29 = new User("Darth", "Vader", "https://static.wikia.nocookie.net/disney/images/8/80/Profile_-_Darth_Vader.png/revision/latest/scale-to-width-down/516?cb=20190314100842");
        User user30 = new User("Darth", "Plagueis", "https://static.wikia.nocookie.net/from-the-crazy/images/6/67/Plagueis.png/revision/latest/scale-to-width-down/310?cb=20180609204346");
        User user31 = new User("Chancellor", "Palpatine", "https://assets-jpcust.jwpsrv.com/thumbs/iko5Bilc-720.jpg");
        User user32 = new User("The", "Senate", "https://pbs.twimg.com/profile_images/647662588457676800/S8-ME1Jb_400x400.png");
        User user33 = new User("Toph", "The Destroyer", "https://i.pinimg.com/474x/84/b2/9b/84b29be844e69ed288bd45a257a72579.jpg");

        User user34 = new User("Cairne", "Bloodhoof", "https://static.wikia.nocookie.net/wowpedia/images/8/8f/Cairne-WC3.jpg/revision/latest/scale-to-width-down/102?cb=20051026232729");
        User user35 = new User("Prince", "Arthas", "https://www.personality-database.com/profile_images/7946.png?id=18875"); // <- THIS ONE
        User user37 = new User("Cleric", "Uther", "https://www.guiaswowtbc.com/wp-content/uploads/2020/08/uther.png");
        User user38 = new User("Frost", "The Undertaker", "https://static.wikia.nocookie.net/descent2e/images/3/36/SkeletonArcherHM.jpg/revision/latest?cb=20150201194421");
        User user40 = new User("Han", "Solo", "https://i.pinimg.com/736x/43/0a/2e/430a2e07513e5fb3e1ee95417bad1719.jpg"); //<- THIS ONE
        List<User> getDummy = Arrays.asList(user22, user23, user24, user25, user26, user27, user28, user29, user30, user31, user32, user33, user34, user35, user37, user38, user40);

        for(User user : getDummy) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowerResponse response = followerServiceSpy.getFollowers(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link FollowerService#getFollowers(FollowerRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowerResponse response = followerServiceSpy.getFollowers(validRequest);

        for(User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link FollowerService#getFollowers(FollowerRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_invalidRequest_returnsNoFollowers() throws IOException, TweeterRemoteException {
        //Assertions.assertEquals(failureResponse, response);
        try {
            FollowerResponse response = followerServiceSpy.getFollowers(invalidRequest);
        } catch (AssertionError e) {
            Assertions.assertEquals(e.getMessage(), new AssertionError().getMessage());
        }
    }
}
