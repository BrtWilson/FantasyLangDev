package integration;

import com.example.shared.model.domain.User;
import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.response.NewLanguageResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;
import edu.byu.cs.client.model.service.oldfiles.NewStatusService;


public class PostStatusServiceIntegrationTest {

    private NewLanguageRequest validRequest1;
    private NewLanguageRequest validRequest2;

    private NewLanguageResponse successResponse1;
    private NewLanguageResponse successResponse2;

    private NewStatusService newStatusService;

    @BeforeEach
    public void setup() throws IOException, RemoteException {
        User user1 = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        User user2 = new User("User", "Name", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        Status resultStatus1 = new Status("Message 1", "TimeStamp1", user1);
        Status resultStatus2 = new Status("Message 2", "TimeStamp2", user2);

        validRequest1 = new NewLanguageRequest( user1.getAlias(), "Message 1", "TimeStamp1");
        validRequest2 = new NewLanguageRequest( user2.getAlias(), "Message 2", "TimeStamp2");

        successResponse1 = new NewLanguageResponse(resultStatus1);
        successResponse2 = new NewLanguageResponse(resultStatus2);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
       //Mockito.when(mockServerFacade.pushNewStatus(validRequest1)).thenReturn(successResponse1);
        //Mockito.when(mockServerFacade.pushNewStatus(validRequest2)).thenReturn(successResponse2);

        newStatusService = Mockito.spy(new NewStatusService());
//        Mockito.when(registerService.getServerFacade()).thenReturn(mockServerFacade); // this is commented out because registerService uses static serverFacade
    }

    @Test
    public void testPostStatus_validRequest_correctResponse() throws IOException, RemoteException {
        NewLanguageResponse response = newStatusService.postNewStatus(validRequest1);
        System.out.println(response.getNewStatus());
        Assertions.assertEquals(successResponse1.isSuccess(), response.isSuccess());
        Assertions.assertEquals(successResponse1.getNewStatus().getMessage(), response.getNewStatus().getMessage());
    }

    @Test
    public void testPostStatus_validRequest_correct2() throws IOException, RemoteException {
        NewLanguageResponse response1 = newStatusService.postNewStatus(validRequest2);
        System.out.println(response1.getNewStatus());
        Assertions.assertEquals(successResponse2.isSuccess(), response1.isSuccess());
        Assertions.assertEquals(successResponse2.getNewStatus().getMessage(), response1.getNewStatus().getMessage());
    }
}
