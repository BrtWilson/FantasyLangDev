package edu.byu.cs.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;

import com.example.shared.model.domain.User;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.response.NewLanguageResponse;
import edu.byu.cs.client.model.service.oldfiles.NewStatusService;
import edu.byu.cs.client.presenter.oldfiles.LoginPresenter;
import edu.byu.cs.client.presenter.oldfiles.NewStatusPresenter;

public class PostStatusPresenterTest {

    private NewLanguageRequest request1;
    private NewLanguageRequest request2;
    private NewLanguageResponse response1;
    private NewLanguageResponse response2;
    private NewStatusService mockNewStatusService;
    private NewStatusPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, RemoteException {
        User user1 = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        User user2 = new User("User", "Name", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        Status resultStatus1 = new Status("Message 1", "TimeStamp1", user1);
        Status resultStatus2 = new Status("Message 2", "TimeStamp2", user2);

        request1 = new NewLanguageRequest("Message 1", "TimeStamp1", user1.getAlias());
        request2 = new NewLanguageRequest("Message 2", "TimeStamp2", user2.getAlias());

        response1 = new NewLanguageResponse(resultStatus1);
        response2 = new NewLanguageResponse(resultStatus2);

        mockNewStatusService = Mockito.mock(NewStatusService.class);
        Mockito.when(mockNewStatusService.postNewStatus(request1)).thenReturn(response1);

        presenter = Mockito.spy(new NewStatusPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getNewStatusService()).thenReturn(mockNewStatusService);
    }


    @Test
    public void testLogin_returnsServiceResult1() throws IOException, RemoteException {
        Mockito.when(mockNewStatusService.postNewStatus(request1)).thenReturn(response1);

        Assertions.assertEquals(response1.isSuccess(), presenter.newStatus(request1).isSuccess());
    }

    @Test
    public void testLogin_returnsServiceResult2() throws IOException, RemoteException {
        Mockito.when(mockNewStatusService.postNewStatus(request2)).thenReturn(response2);

        Assertions.assertEquals(response2.isSuccess(), presenter.newStatus(request2).isSuccess());
    }
}
