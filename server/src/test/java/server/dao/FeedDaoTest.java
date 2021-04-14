package server.dao;

import com.example.server.dao.FeedTableDAO;
import com.example.server.dao.StoryTableDAO;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.service.StatusArrayService;
import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.NewStatusResponse;
import com.example.shared.model.service.response.StatusArrayResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;


public class FeedDaoTest {

    private NewStatusRequest validRequest1;
    private NewStatusRequest validRequest2;

    private NewStatusResponse successResponse1;
    private NewStatusResponse successResponse2;

    private StatusArrayRequest validArrayRequest;
    private StatusArrayRequest invalidArrayRequest;

    private StatusArrayResponse successArrayResponse;
    private StatusArrayResponse failureArrayResponse;

    private FollowerResponse followerResponse;
    private NewStatusResponse successBatchResponse;

    private String followerAlias;

    private FeedTableDAO statusesDAO;
    private String userInTableAlias = "@AangJones";
    private int pageSize = 5;

    @BeforeEach
    public void setup() {
        User user1 = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        User user2 = new User("User", "Name", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");
        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        Status resultStatus1 = new Status("Message 1", "1349533574", user1.getAlias());
        Status resultStatus2 = new Status("Message 2", "1349533574", user2.getAlias());
        Status resultStatus3 = new Status("Message 3", "1349533574", resultUser3.getAlias());


        validRequest1 = new NewStatusRequest( user1.getAlias(), "Message 1", "1349533574");
        validRequest2 = new NewStatusRequest( user2.getAlias(), "Message 2", "1349533574");

        successResponse1 = new NewStatusResponse(resultStatus1);
        successResponse2 = new NewStatusResponse(resultStatus2);
        followerAlias = "@LukeSkywalker";
        FeedTableDAO mockDao = Mockito.mock(FeedTableDAO.class);
//        DynamoDBStrategy mockDatabaseInteractor = Mockito.mock(DynamoDBStrategy.class);
//        Mockito.when(mockDao.getDatabaseInteractor()).thenReturn(mockDatabaseInteractor);
//        Mockito.when(mockDao.postNewStatus(validRequest1, followerAlias)).thenReturn(successResponse1);
//        Mockito.when(mockDao.postNewStatus(validRequest2, followerAlias)).thenReturn(successResponse2);

        followerResponse =  new FollowerResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false, null);

        // Setup request objects to use in the tests
        validArrayRequest = new StatusArrayRequest(userInTableAlias, pageSize, null);
        invalidArrayRequest = new StatusArrayRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successArrayResponse = new StatusArrayResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), true, null);
       // Mockito.when(mockDao.getStatusArray(validArrayRequest)).thenReturn(successArrayResponse);

        failureArrayResponse = new StatusArrayResponse("An exception occurred");
        //Mockito.when(mockDao.getStatusArray(invalidArrayRequest)).thenReturn(failureArrayResponse);

        statusesDAO = Mockito.spy(new FeedTableDAO());
    }

//    @Test
//    public void testPostStatus_validRequest_correctResponse() throws IOException {
//        NewStatusResponse response = statusesDAO.postNewStatus(validRequest1, followerAlias);
//        System.out.println(response.getNewStatus());
//        Assertions.assertEquals(successResponse1.isSuccess(), response.isSuccess());
//        Assertions.assertEquals(successResponse1.getNewStatus().getMessage(), response.getNewStatus().getMessage());
//    }

    @Test
    public void testGetFeed_validRequest_correct2() throws IOException {
        StatusArrayResponse arrayResponse = statusesDAO.getStatusArray(validArrayRequest);
        //System.out.println(arrayResponse.getStatuses());
        Assertions.assertEquals(successArrayResponse.isSuccess(), arrayResponse.isSuccess());
        Assertions.assertEquals(successArrayResponse.getStatuses().size(), arrayResponse.getStatuses().size());
        Assertions.assertEquals(pageSize, arrayResponse.getStatuses().size());
        Assertions.assertEquals(pageSize, arrayResponse.getStatuses().size());
    }

    /**
     * Verify that for successful requests the {@link StatusArrayService #getStatusArray(StatusArrayRequest)}
     * method returns the same result as the {@link StoryTableDAO}.
     *
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testPostStatusBatch_validRequest_correctResponse() throws IOException {
        NewStatusResponse response = statusesDAO.postStatusBatch(validRequest1, followerResponse);
        Assertions.assertEquals(successBatchResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(successBatchResponse.getNewStatus().getMessage(), response.getNewStatus().getMessage());
    }

}
