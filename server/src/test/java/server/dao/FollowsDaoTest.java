package server.dao;

import com.example.server.dao.FollowsTableDAO;
import com.example.server.service.FollowerService;
import com.example.server.service.FollowingService;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.FollowStatusRequest;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowStatusResponse;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.FollowingResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class FollowsDaoTest {

    private FollowerRequest validFollowerRequest;
    private FollowerRequest invalidFollowerRequest;

    private FollowerResponse successFollowerResponse;
    private FollowerResponse failureResponse;

    private FollowingRequest validFollowingRequest;
    private FollowingResponse successFollowingResponse;
    private FollowingRequest invalidFollowingRequest;
    private FollowingResponse failureFollowingResponse;

    private FollowStatusRequest followStatusRequest1;
    private FollowStatusRequest followStatusRequest2;
    private FollowStatusResponse followStatusResponse1;
    private FollowStatusResponse followStatusResponse2;

    private FollowsTableDAO followsDao;

    /**
     * Create a FollowerService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("Harry", "Potter", "@HarryPotter", "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142");
        //user.setPassword("hello1234");
        //userAlias = "@HarryPotter";
        User resultUser1 = new User("Frodo", "Baggins", "@RingBear", "https://seekinggoddailyblog.files.wordpress.com/2016/07/1b9384b6de87ab45a1391d454bd695c5.jpg");
        User resultUser2 = new User("James", "Potter", "@JamesPotter", "https://jamesblakebrytontweeterimages.s3.amazonaws.com/Zuko.png");
        User resultUser3 = new User("Lilly", "Potter", "@LillyPotter", "https://jamesblakebrytontweeterimages.s3.amazonaws.com/Zuko.png");
        User resultUser4 = new User("Perrin", "Ibarra", "@goldeneyes", "data:image/jpeg;base64,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");
        User resultUser5 = new User("Luna", "Lovegood", "@LunaLovegood", "https://jamesblakebrytontweeterimages.s3.amazonaws.com/lunalovegood.png");
        User resultUser6 = new User("User'", "6082", "@user6082", "https://jamesblakebrytontweeterimages.s3.amazonaws.com/Zuko.png");
        User currentUser2 = new User("Obi-wan", "Kenobi", "https://jamesblakebrytontweeterimages.s3.amazonaws.com/obiwankenobi.png");

        /*
        User currentUser = new User("Harry", "Potter", null);

        User resultUser1 = new User("Kin", "Jonahs",
                "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142");
        User resultUser2 = new User("Luke", "Skywalker",
                "https://c0.klipartz.com/pngpicture/250/535/sticker-png-katara-avatar-the-last-airbender-aang-korra-zuko-aang-child-face-black-hair-hand-head.png");
        User resultUser3 = new User("Anakin", "Skywalker",
                "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=2018063011214");

        User currentUser2 = new User("FirstName", "LastName", null);

        User resultUser4 = new User("Ash", "Ahketchum",
                "https://i.pinimg.com/originals/e5/9b/e7/e59be7316543f2b7c94bcf693c2ad9f3.png");
        User resultUser5 = new User("Amy", "Ames",
                "https://i.pinimg.com/originals/5f/79/d6/5f79d6d933f194dbcb74ec5e5ce7a759.jpg");
        User resultUser6 = new User("Bob", "Bross",
                "https://i.pinimg.com/originals/e5/9b/e7/e59be7316543f2b7c94bcf693c2ad9f3.pn");

         */

        // Setup request objects to use in the tests
        validFollowerRequest = new FollowerRequest(currentUser.getAlias(), 3, null);

        // Setup a mock ServerFacade that will return known responses
        successFollowerResponse = new FollowerResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false, null);

        // Setup request objects to use in the tests
        validFollowingRequest = new FollowingRequest(currentUser.getAlias(), 3, resultUser2.getAlias());
        invalidFollowingRequest = new FollowingRequest(null, 0, resultUser1.getAlias());

        // Setup a mock ServerFacade that will return known responses
        successFollowingResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        failureFollowingResponse = new FollowingResponse("An exception occurred");

        followStatusRequest1 = new FollowStatusRequest(currentUser.getAlias(), currentUser2.getAlias(), FollowStatusRequest.GET, new AuthToken(currentUser.getAlias()));
        followStatusRequest2 = new FollowStatusRequest(currentUser2.getAlias(), currentUser.getAlias(), FollowStatusRequest.GET, new AuthToken(currentUser2.getAlias()));
        followStatusResponse1 = new FollowStatusResponse(true);
        followStatusResponse1 = new FollowStatusResponse(false);

        // Create a FollowerService instance and wrap it with a spy that will use the mock service
        followsDao = new FollowsTableDAO();
    }

    /**
     * Verify that for successful requests the {@link FollowerService#getFollowers(FollowerRequest)}
     * method returns the same result as the {@link FollowsTableDAO}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException {

        FollowerResponse response = followsDao.getFollowers(validFollowerRequest);
        //Assertions.assertTrue(successResponse.equals(response));
        Assertions.assertEquals(successFollowerResponse.getMessage(), response.getMessage());
        Assertions.assertEquals(successFollowerResponse.getLastFollowerAlias(), response.getLastFollowerAlias());
        Assertions.assertEquals(successFollowerResponse.getFollowers(), response.getFollowers());;
    }

    /**
     * Verify that the {@link FollowerService#getFollowers(FollowerRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_validRequest_loadsProfileImages() throws IOException {
        FollowerResponse response = followsDao.getFollowers(validFollowerRequest);

        for(User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageUrl());
        }
    }

    /**
     * Verify that for failed requests the {@link FollowerService#getFollowers(FollowerRequest)}
     * method returns the same result as the {@link FollowsTableDAO}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_invalidRequest_returnsNoFollowers() throws IOException {
        //Assertions.assertEquals(failureResponse, response);
        try {
            FollowerResponse response = followsDao.getFollowers(invalidFollowerRequest);
        } catch (AssertionError e) {
            Assertions.assertEquals(e.getMessage(), new AssertionError().getMessage());
        }
    }

    /**
     * Verify that for successful requests the {@link FollowingService #getFollowees(com.example.shared.model.service.request.FollowingRequest)}
     * method returns the same result as the {@link FollowsTableDAO}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowings_validRequest_correctResponse() throws IOException {
        FollowingResponse response = followsDao.getFollowees(validFollowingRequest);
        Assertions.assertEquals(successFollowingResponse.getMessage(), response.getMessage());
        Assertions.assertEquals(successFollowingResponse.getFollowees(), response.getFollowees());;
    }

    /**
     * Verify that for failed requests the {@link FollowingService #getFollowees(com.example.shared.model.service.request.FollowingRequest)}
     * method returns the same result as the {@link FollowsTableDAO}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowings_invalidRequest_returnsNoFollowings() throws IOException {
        //Assertions.assertEquals(failureResponse, response);
        try {
            FollowingResponse response = followsDao.getFollowees(invalidFollowingRequest);
        } catch (AssertionError e) {
            Assertions.assertEquals(e.getMessage(), new AssertionError().getMessage());
        }
    }

    @Test
    public void testGetFollowStatus_valid_returnTrue() {
        FollowStatusResponse response = followsDao.getFollowStatus(followStatusRequest1);
        Assertions.assertEquals(followStatusResponse1.relationshipExists(), response.relationshipExists());
    }

    @Test
    public void testGetFollowStatus_valid_returnFalse() {
        FollowStatusResponse response = followsDao.getFollowStatus(followStatusRequest2);
        Assertions.assertEquals(followStatusResponse2.relationshipExists(), response.relationshipExists());
    }
}
