package server.dao;

import com.example.server.dao.LanguageTableDAO;
import com.example.server.dao.DictionaryTableDAO;
import com.example.server.service.StatusArrayService;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.NewLanguageResponse;
import com.example.shared.model.service.response.DictionaryPageResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;


public class FeedDaoTest {

    private NewLanguageRequest validRequest1;
    private NewLanguageRequest validRequest2;

    private NewLanguageResponse successResponse1;
    private NewLanguageResponse successResponse2;

    private UpdateSyllablesRequest validArrayRequest;
    private UpdateSyllablesRequest invalidArrayRequest;

    private DictionaryPageResponse successArrayResponse;
    private DictionaryPageResponse failureArrayResponse;

    private FollowerResponse followerResponse;
    private NewLanguageResponse successBatchResponse;

    private String followerAlias;

    private LanguageTableDAO statusesDAO;
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


        validRequest1 = new NewLanguageRequest( user1.getAlias(), "Message 1", "1349533574");
        validRequest2 = new NewLanguageRequest( user2.getAlias(), "Message 2", "1349533574");

        successResponse1 = new NewLanguageResponse(resultStatus1);
        successResponse2 = new NewLanguageResponse(resultStatus2);

        successBatchResponse = new NewLanguageResponse(resultStatus1);

        followerAlias = "@LukeSkywalker";

        followerResponse =  new FollowerResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false, null);

        // Setup request objects to use in the tests
        validArrayRequest = new UpdateSyllablesRequest(userInTableAlias, pageSize, null);
        invalidArrayRequest = new UpdateSyllablesRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successArrayResponse = new DictionaryPageResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), true, null);
       // Mockito.when(mockDao.getStatusArray(validArrayRequest)).thenReturn(successArrayResponse);

        failureArrayResponse = new DictionaryPageResponse("An exception occurred");
        //Mockito.when(mockDao.getStatusArray(invalidArrayRequest)).thenReturn(failureArrayResponse);

        statusesDAO = Mockito.spy(new LanguageTableDAO());
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
        DictionaryPageResponse arrayResponse = statusesDAO.getStatusArray(validArrayRequest);
        Assertions.assertEquals(successArrayResponse.isSuccess(), arrayResponse.isSuccess());
        Assertions.assertEquals(pageSize, arrayResponse.getStatuses().size());
        Assertions.assertEquals(pageSize, arrayResponse.getStatuses().size());
    }

    /**
     * Verify that for successful requests the {@link StatusArrayService #getStatusArray(StatusArrayRequest)}
     * method returns the same result as the {@link DictionaryTableDAO}.
     *
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testPostStatusBatch_validRequest_correctResponse() throws IOException {
        NewLanguageResponse response = statusesDAO.postStatusBatch(validRequest1, followerResponse);
        Assertions.assertEquals(successBatchResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(successBatchResponse.getNewStatus().getMessage(), response.getNewStatus().getMessage());
    }

}
