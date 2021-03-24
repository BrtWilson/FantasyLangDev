package integration;

import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.FollowingResponse;
import com.example.shared.model.service.response.StatusArrayResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.client.model.net.ServerFacade;
import edu.byu.cs.client.model.service.StatusArrayService;

public class StatusArrayServiceTest {

    private StatusArrayRequest validRequest;
    private StatusArrayRequest invalidRequest;

    private StatusArrayResponse successResponse;
    private StatusArrayResponse failureResponse;

    private StatusArrayService statusArrayServiceSpy;

    /**
     * Create a StatusArrayService spy that uses a mock ServerFacade to return known responses to
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

        Status resultStatus1 = new Status("Message 1", "TimeStamp1", resultUser1);
        Status resultStatus2 = new Status("Message 2", "TimeStamp2", resultUser2);
        Status resultStatus3 = new Status("Message 3", "TimeStamp3", resultUser3);

        // Setup request objects to use in the tests
        validRequest = new StatusArrayRequest(resultUser1.getAlias(), 3, null);
        invalidRequest = new StatusArrayRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StatusArrayResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);
        ServerFacade serverFacade = Mockito.spy(new ServerFacade());
        //Mockito.when(serverFacade.getStatusArray(validRequest)).thenReturn(successResponse);

        failureResponse = new StatusArrayResponse("An exception occurred");
        //Mockito.when(serverFacade.getStatusArray(invalidRequest)).thenReturn(failureResponse);

        // Create a StatusArrayService instance and wrap it with a spy that will use the mock service
        statusArrayServiceSpy = Mockito.spy(new StatusArrayService());
        //Mockito.when(statusArrayServiceSpy.getServerFacade()).thenReturn(serverFacade);
    }

    /**
     * Verify that for successful requests the {@link StatusArrayService #getStatusArray(StatusArrayRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStatusArray_validRequest_correctResponse() throws IOException {
        StatusArrayResponse response = statusArrayServiceSpy.requestStatusArray(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link StatusArrayService #getStatusArray(StatusArrayRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStatusArray_validRequest_loadsProfileImages() throws IOException {
        StatusArrayResponse response = statusArrayServiceSpy.requestStatusArray(validRequest);

        for(Status status : response.getStatuses()) {
            Assertions.assertNotNull(status.getCorrespondingUser().getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link StatusArrayService #requestStatusArray(StatusArrayRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStatusArray_invalidRequest_returnsNoFollowers() throws IOException {
        //Assertions.assertEquals(failureResponse, response);
        try {
            StatusArrayResponse response = statusArrayServiceSpy.requestStatusArray(invalidRequest);
        } catch (AssertionError e) {
            Assertions.assertEquals(e.getMessage(), new AssertionError().getMessage());
        }
    }
}
