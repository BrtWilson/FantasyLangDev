package edu.byu.cs.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.domain.Status;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;
import edu.byu.cs.client.model.service.NewStatusService;

public class PostStatusPresenterTest {

    private NewStatusRequest request1;
    private NewStatusRequest request2;
    private NewStatusResponse response1;
    private NewStatusResponse response2;
    private NewStatusService mockNewStatusService;
    private NewStatusPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User user1 = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        User user2 = new User("User", "Name", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        Status resultStatus1 = new Status("Message 1", "TimeStamp1", user1);
        Status resultStatus2 = new Status("Message 2", "TimeStamp2", user2);

        request1 = new NewStatusRequest("Message 1", "TimeStamp1", user1.getAlias());
        request2 = new NewStatusRequest("Message 2", "TimeStamp2", user2.getAlias());

        response1 = new NewStatusResponse(resultStatus1);
        response2 = new NewStatusResponse(resultStatus2);

        mockNewStatusService = Mockito.mock(NewStatusService.class);
        Mockito.when(mockNewStatusService.postNewStatus(request1)).thenReturn(response1);

        presenter = Mockito.spy(new NewStatusPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getNewStatusService()).thenReturn(mockNewStatusService);
    }


    @Test
    public void testLogin_returnsServiceResult1() throws IOException, TweeterRemoteException {
        Mockito.when(mockNewStatusService.postNewStatus(request1)).thenReturn(response1);

        Assertions.assertEquals(response1.isSuccess(), presenter.newStatus(request1).isSuccess());
    }

    @Test
    public void testLogin_returnsServiceResult2() throws IOException, TweeterRemoteException {
        Mockito.when(mockNewStatusService.postNewStatus(request2)).thenReturn(response2);

        Assertions.assertEquals(response2.isSuccess(), presenter.newStatus(request2).isSuccess());
    }
}
