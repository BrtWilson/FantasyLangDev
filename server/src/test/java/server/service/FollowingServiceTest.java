package server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import com.example.server.dao.SyllableTableDAO;
import com.example.server.service.FollowingService;
import com.example.shared.model.domain.User;

public class FollowingServiceTest {

    private FollowingRequest validRequest;
    private FollowingRequest invalidRequest;

    private FollowingResponse successResponse;
    private FollowingResponse failureResponse;

    private FollowingService followingServiceSpy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup request objects to use in the tests
        validRequest = new FollowingRequest(currentUser.getAlias(), 3, null);
        invalidRequest = new FollowingRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        SyllableTableDAO mockServerFacade = Mockito.mock(SyllableTableDAO.class);
        Mockito.when(mockServerFacade.getFollowees(validRequest)).thenReturn(successResponse);

        failureResponse = new FollowingResponse("An exception occurred");
        Mockito.when(mockServerFacade.getFollowees(invalidRequest)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followingServiceSpy = Mockito.spy(new FollowingService());
        Mockito.when(followingServiceSpy.getFolloweesDao()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link FollowingService#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link SyllableTableDAO}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException {
        FollowingResponse response = followingServiceSpy.getFollowees(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link FollowingService#getFollowees(FollowingRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException {
        FollowingResponse response = followingServiceSpy.getFollowees(validRequest);

        for(User user : response.getFollowees()) {
            Assertions.assertNotNull(user.getImageUrl());
        }
    }

    /**
     * Verify that for failed requests the {@link FollowingService#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link SyllableTableDAO}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException {
        FollowingResponse response = followingServiceSpy.getFollowees(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
