package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterPresenterTest {

    private RegisterRequest request;
    private RegisterResponse response;
    private RegisterService mockRegisterService;
    private RegisterPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User user = new User("first", "last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user.setPassword("password");

        request = new RegisterRequest("first", "last", "@firstlast", "password", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        response = new RegisterResponse(user, new AuthToken(), true);

        mockRegisterService = Mockito.mock(RegisterService.class);
        Mockito.when(mockRegisterService.register(request)).thenReturn(response);

        presenter = Mockito.spy(new RegisterPresenter(new RegisterPresenter.View() {}));
        Mockito.when(presenter.getRegisterService()).thenReturn(mockRegisterService);
    }


    @Test
    public void testLogin_returnsServiceResult() throws IOException {
        Mockito.when(mockRegisterService.register(request)).thenReturn(response);

        Assertions.assertEquals(response.isSuccess(), presenter.register(request).isSuccess());
    }

    @Test
    public void testLogin_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockRegisterService.register(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.register(request);
        });
    }

}
