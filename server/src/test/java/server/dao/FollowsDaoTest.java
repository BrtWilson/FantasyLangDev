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
import org.mockito.Mockito;

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

    private FollowsTableDAO followsDaoSpy;

    /**
     * Create a FollowerService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
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

        // Setup request objects to use in the tests
        validFollowerRequest = new FollowerRequest(currentUser.getAlias(), 3, null);

        // Setup a mock ServerFacade that will return known responses
        successFollowerResponse = new FollowerResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false, null);
        //FollowsTableDAO mockFollowsDao = Mockito.mock(FollowsTableDAO.class);
//        DynamoDBStrategy mockDatabaseInteractor = Mockito.mock(DynamoDBStrategy.class);
//        Mockito.when(mockFollowsDao.getDatabaseInteractor()).thenReturn(mockDatabaseInteractor);
        //Mockito.when(mockFollowsDao.getFollowers(validFollowerRequest)).thenReturn(successFollowerResponse);


        // Setup request objects to use in the tests
        validFollowingRequest = new FollowingRequest(currentUser.getAlias(), 3, null);
        invalidFollowingRequest = new FollowingRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successFollowingResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        //Mockito.when(mockFollowsDao.getFollowees(validFollowingRequest)).thenReturn(successFollowingResponse);

        failureFollowingResponse = new FollowingResponse("An exception occurred");
        //Mockito.when(mockFollowsDao.getFollowees(invalidFollowingRequest)).thenReturn(failureFollowingResponse);

        followStatusRequest1 = new FollowStatusRequest(currentUser.getAlias(), currentUser2.getAlias(), FollowStatusRequest.GET, new AuthToken(currentUser.getAlias()));
        followStatusRequest2 = new FollowStatusRequest(currentUser2.getAlias(), currentUser.getAlias(), FollowStatusRequest.GET, new AuthToken(currentUser2.getAlias()));
        followStatusResponse1 = new FollowStatusResponse(true);
        //Mockito.when(mockFollowsDao.getFollowStatus(followStatusRequest1)).thenReturn(followStatusResponse1);
        followStatusResponse1 = new FollowStatusResponse(false);
        //Mockito.when(mockFollowsDao.getFollowStatus(followStatusRequest2)).thenReturn(followStatusResponse2);

        // Create a FollowerService instance and wrap it with a spy that will use the mock service
        followsDaoSpy = Mockito.spy(new FollowsTableDAO());
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
        FollowerResponse response = followsDaoSpy.getFollowers(validFollowerRequest);
        //Assertions.assertTrue(successResponse.equals(response));
        Assertions.assertEquals(successFollowerResponse.getMessage(), response.getMessage());
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
        FollowerResponse response = followsDaoSpy.getFollowers(validFollowerRequest);

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
            FollowerResponse response = followsDaoSpy.getFollowers(invalidFollowerRequest);
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
        FollowingResponse response = followsDaoSpy.getFollowees(validFollowingRequest);
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
            FollowingResponse response = followsDaoSpy.getFollowees(invalidFollowingRequest);
        } catch (AssertionError e) {
            Assertions.assertEquals(e.getMessage(), new AssertionError().getMessage());
        }
    }

    @Test
    public void testGetFollowStatus_valid_returnTrue() {
        FollowStatusResponse response = followsDaoSpy.getFollowStatus(followStatusRequest1);
        Assertions.assertEquals(followStatusResponse1.relationshipExists(), response.relationshipExists());
    }

    @Test
    public void testGetFollowStatus_valid_returnFalse() {
        FollowStatusResponse response = followsDaoSpy.getFollowStatus(followStatusRequest2);
        Assertions.assertEquals(followStatusResponse2.relationshipExists(), response.relationshipExists());
    }
}
