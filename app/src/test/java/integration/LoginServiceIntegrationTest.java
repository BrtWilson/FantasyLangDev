package integration;

import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.LoginResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;
import edu.byu.cs.client.model.service.LoginService;


public class LoginServiceIntegrationTest {
    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginResponse successResponse;
    private LoginResponse failureResponse;

    private LoginService loginService;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User user = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user.setPassword("password");
        User testUser2 = new User("test", "user2", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        testUser2.setPassword("password");

        validRequest = new LoginRequest(testUser2.getAlias(), testUser2.getPassword());
        invalidRequest = new LoginRequest(testUser2.getAlias(), "wrongPassword");

        successResponse = new LoginResponse(testUser2, new AuthToken());
        ServerFacade serverFacade = Mockito.spy(new ServerFacade());
        //Mockito.when(serverFacade.login(validRequest)).thenReturn(successResponse);

        failureResponse = new LoginResponse("Password does not match.");
        //Mockito.when(serverFacade.login(invalidRequest)).thenReturn(failureResponse);

        loginService = Mockito.spy(new LoginService());
        //Mockito.when(loginService.getServerFacade()).thenReturn(serverFacade);
    }

    @Test
    public void testLogin_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginService.login(validRequest);
        Assertions.assertEquals(successResponse.getUser(), response.getUser());
    }

    @Test
    public void testLogin_validRequest_loadsProfileImage() throws IOException, TweeterRemoteException {
        LoginResponse response = loginService.login(validRequest);
        Assertions.assertNotNull(response.getUser().getImageUrl());
    }

    @Test
    public void testLogin_invalidRequest_returnsFailedMessage() throws IOException, TweeterRemoteException {
        LoginResponse response = loginService.login(invalidRequest);
        Assertions.assertEquals(failureResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(failureResponse.getMessage(), response.getMessage());
    }
}
