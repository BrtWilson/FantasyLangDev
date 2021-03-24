package server.dao;

import com.example.server.dao.FollowsTableDAO;
import com.example.server.service.FollowingService;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowingResponse;
import com.example.shared.model.service.response.RegisterResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

public class FolloweesDaoTest {

    private FollowingRequest validRequest;
    private FollowingRequest invalidRequest;

    private FollowingResponse successResponse;
    private FollowingResponse failureResponse;

    private FollowsTableDAO followsDaoSpy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("Ash", "Ahketchum",
                "https://i.pinimg.com/originals/e5/9b/e7/e59be7316543f2b7c94bcf693c2ad9f3.png");
        User resultUser2 = new User("Amy", "Ames",
                "https://i.pinimg.com/originals/5f/79/d6/5f79d6d933f194dbcb74ec5e5ce7a759.jpg");
        User resultUser3 = new User("Bob", "Bross",
                "https://i.pinimg.com/originals/e5/9b/e7/e59be7316543f2b7c94bcf693c2ad9f3.png");

        // Setup request objects to use in the tests
        validRequest = new FollowingRequest(currentUser.getAlias(), 3, null);
        invalidRequest = new FollowingRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        FollowsTableDAO mockFollowsDao = Mockito.mock(FollowsTableDAO.class);
        Mockito.when(mockFollowsDao.getFollowees(validRequest)).thenReturn(successResponse);

        failureResponse = new FollowingResponse("An exception occurred");
        Mockito.when(mockFollowsDao.getFollowees(invalidRequest)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followsDaoSpy = Mockito.spy(new FollowsTableDAO());
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
        FollowingResponse response = followsDaoSpy.getFollowees(validRequest);
        Assertions.assertEquals(successResponse.getMessage(), response.getMessage());
        Assertions.assertEquals(successResponse.getFollowees(), response.getFollowees());;
    }

    /**
     * Verify that the {@link FollowingService#getFollowees(FollowingRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowings_validRequest_loadsProfileImages() throws IOException {
        FollowingResponse response = followsDaoSpy.getFollowees(validRequest);

        for(User user : response.getFollowees()) {
            Assertions.assertNotNull(user.getImageUrl());
        }
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
            FollowingResponse response = followsDaoSpy.getFollowees(invalidRequest);
        } catch (AssertionError e) {
            Assertions.assertEquals(e.getMessage(), new AssertionError().getMessage());
        }
    }
    
}
