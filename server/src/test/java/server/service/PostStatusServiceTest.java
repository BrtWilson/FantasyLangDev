package server.service;

import com.example.server.dao.DictionaryTableDAO;
import com.example.server.service.NewStatusStoryService;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;


public class PostStatusServiceTest {

    private NewStatusRequest validRequest1;
    private NewStatusRequest validRequest2;

    private NewStatusResponse successResponse1;
    private NewStatusResponse successResponse2;

    private NewStatusStoryService newStatusStoryService;

    @BeforeEach
    public void setup() {
        User user1 = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        User user2 = new User("User", "Name", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        Status resultStatus1 = new Status("Message 1", "TimeStamp1", user1.getAlias());
        Status resultStatus2 = new Status("Message 2", "TimeStamp2", user2.getAlias());

        validRequest1 = new NewStatusRequest( user1.getAlias(), "Message 1", "TimeStamp1");
        validRequest2 = new NewStatusRequest( user2.getAlias(), "Message 2", "TimeStamp2");

        successResponse1 = new NewStatusResponse(resultStatus1);
        successResponse2 = new NewStatusResponse(resultStatus2);
        DictionaryTableDAO mockDao = Mockito.mock(DictionaryTableDAO.class);
        Mockito.when(mockDao.postNewStatus(validRequest1)).thenReturn(successResponse1);
        Mockito.when(mockDao.postNewStatus(validRequest2)).thenReturn(successResponse2);

        newStatusStoryService = Mockito.spy(new NewStatusStoryService());
    }

    @Test
    public void testPostStatus_validRequest_correctResponse() throws IOException {
        NewStatusResponse response = newStatusStoryService.postNewStatus(validRequest1);
        System.out.println(response.getNewStatus());
        Assertions.assertEquals(successResponse1.isSuccess(), response.isSuccess());
        Assertions.assertEquals(successResponse1.getNewStatus().getMessage(), response.getNewStatus().getMessage());
    }

    @Test
    public void testPostStatus_validRequest_correct2() throws IOException {
        NewStatusResponse response1 = newStatusStoryService.postNewStatus(validRequest2);
        System.out.println(response1.getNewStatus());
        Assertions.assertEquals(successResponse2.isSuccess(), response1.isSuccess());
        Assertions.assertEquals(successResponse2.getNewStatus().getMessage(), response1.getNewStatus().getMessage());
    }
}
