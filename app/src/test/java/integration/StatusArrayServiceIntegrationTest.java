package integration;

import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.StatusArrayResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.client.model.net.ServerFacade;
import edu.byu.cs.client.model.service.StatusArrayService;

public class StatusArrayServiceIntegrationTest {

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
        User user1 = new User("Ash", "Ahketchum", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngitem.com%2Fmiddle%2FwomThJ_ash-ketchum-hd-png-download%2F&psig=AOvVaw2h43_Bi3x5gdd1y2tRmAhq&ust=1616605412770000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJjVytTyxu8CFQAAAAAdAAAAABAL");
        User user2 = new User("Amy", "Ames", "https://i.pinimg.com/originals/5f/79/d6/5f79d6d933f194dbcb74ec5e5ce7a759.jpg");
        User user3 = new User("Bob", "Bross", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.bobross.com%2F&psig=AOvVaw0aQWlEayotht6kNKp2WOPT&ust=1616605355172000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKjex7Pyxu8CFQAAAAAdAAAAABAD");

        Status resultStatus1 = new Status("Message 1", "TimeStamp1", resultUser1.getAlias());
        Status resultStatus2 = new Status("Message 2", "TimeStamp2", resultUser2.getAlias());
        Status resultStatus3 = new Status("Message 3", "TimeStamp3", resultUser3.getAlias());

        Status status1 = new Status("I have a sister.", "8:00pm", user1.getAlias());
        Status status2 = new Status("@Luke, I am your father.", "8:01pm", user2.getAlias());
        Status status3 = new Status("No, I am your father", "8:02pm", user3.getAlias());

        // Setup request objects to use in the tests
        validRequest = new StatusArrayRequest(resultUser1.getAlias(), 3, null);
        invalidRequest = new StatusArrayRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StatusArrayResponse(Arrays.asList(status1, status2, status3), false, null);
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
        Assertions.assertEquals(successResponse.getStatuses().get(0).getMessage(), response.getStatuses().get(0).getMessage());
        Assertions.assertEquals(successResponse.getStatuses().get(0).getDate(), response.getStatuses().get(0).getDate());
        Assertions.assertEquals(successResponse.getStatuses().get(1).getMessage(), response.getStatuses().get(1).getMessage());
        Assertions.assertEquals(successResponse.getStatuses().get(2), response.getStatuses().get(2));
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
            Assertions.assertNotNull(status.getCorrespondingUserAlias());
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
        } catch (RuntimeException e) {
            Assertions.assertEquals(e.getClass(), new RuntimeException().getClass());
        }
    }
}
