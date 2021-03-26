package integration;

import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.FollowStatusRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowStatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;
import edu.byu.cs.client.model.service.FollowStatusService;
import edu.byu.cs.client.model.service.FollowingService;

public class FollowStatusServiceIntegrationTest {

    private FollowStatusRequest validGetRequest;
    private FollowStatusRequest validFollowRequest;
    private FollowStatusRequest validUnfollowRequest;

    private FollowStatusResponse successGetResponse;
    private FollowStatusResponse successFollowResponse;
    private FollowStatusResponse successUnfollowResponse;

    private FollowStatusService followStatusServiceSpy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        AuthToken authToken = new AuthToken();
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        validGetRequest = new FollowStatusRequest(resultUser1.getAlias(), currentUser.getAlias(), FollowStatusRequest.GET, authToken);
        validFollowRequest = new FollowStatusRequest(resultUser1.getAlias(), currentUser.getAlias(), FollowStatusRequest.FOLLOW, authToken);
        validUnfollowRequest = new FollowStatusRequest(resultUser1.getAlias(), currentUser.getAlias(), FollowStatusRequest.UNFOLLOW, authToken);

        successGetResponse =  new FollowStatusResponse(true);
        successUnfollowResponse =  new FollowStatusResponse(false);
        successFollowResponse =  new FollowStatusResponse(true);


        // Setup a mock ServerFacade that will return known responses
        //ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        //Mockito.when(mockServerFacade.getFollowStatus(validGetRequest)).thenReturn(successGetResponse);
        //Mockito.when(mockServerFacade.getFollowStatus(validFollowRequest)).thenReturn(successFollowResponse);
        //Mockito.when(mockServerFacade.getFollowStatus(validUnfollowRequest)).thenReturn(successUnfollowResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followStatusServiceSpy = Mockito.spy(new FollowStatusService());
        //Mockito.when(followStatusServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link FollowingService#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowStatus_validGetRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowStatusResponse response = followStatusServiceSpy.getFollowStatus(validGetRequest);
        Assertions.assertEquals(successGetResponse, response);
    }

    @Test
    public void testGetFollowStatus_validFollowRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowStatusResponse response = followStatusServiceSpy.getFollowStatus(validFollowRequest);
        Assertions.assertEquals(successFollowResponse.relationshipExists(), response.relationshipExists());
    }

    @Test
    public void testGetFollowStatus_validUnfollowRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowStatusResponse response = followStatusServiceSpy.getFollowStatus(validUnfollowRequest);
        Assertions.assertEquals(successUnfollowResponse, response);
    }
}
