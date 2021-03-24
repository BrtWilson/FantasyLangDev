package edu.byu.cs.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import edu.byu.cs.client.model.service.LoginService;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.LoginResponse;

public class LoginPresenterTest {

    private LoginRequest request;
    private LoginResponse response;
    private LoginService mockLoginService;
    private LoginPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User user = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user.setPassword("password");

        request = new LoginRequest(user.getAlias(), user.getPassword());
        response = new LoginResponse(user, new AuthToken());

        mockLoginService = Mockito.mock(LoginService.class);
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getLoginService()).thenReturn(mockLoginService);
    }

    @Test
    public void testLogin_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        Assertions.assertEquals(response.isSuccess(), presenter.login(request).isSuccess());
    }

    @Test
    public void testLogin_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginService.login(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.login(request);
        });
    }
}
