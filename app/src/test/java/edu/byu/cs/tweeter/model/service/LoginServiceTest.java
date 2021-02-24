package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;


public class LoginServiceTest {
    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginResponse successResponse;
    private LoginResponse failureResponse;

    private LoginService loginService;

    @BeforeEach
    public void setup() {
        User user = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user.setPassword("password");

        validRequest = new LoginRequest(user.getAlias(), user.getPassword());
        invalidRequest = new LoginRequest(user.getAlias(), "wrongPassword");

        successResponse = new LoginResponse(user, new AuthToken());
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.login(validRequest)).thenReturn(successResponse);

        failureResponse = new LoginResponse("Password does not match.");
        Mockito.when(mockServerFacade.login(invalidRequest)).thenReturn(failureResponse);

        loginService = Mockito.spy(new LoginService());
        Mockito.when(loginService.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testRegister_validRequest_correctResponse() throws IOException {
        LoginResponse response = loginService.login(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testRegister_validRequest_loadsProfileImage() throws IOException {
        LoginResponse response = loginService.login(validRequest);
        Assertions.assertNotNull(response.getUser().getImageBytes());
    }

    @Test
    public void testGetFollowers_invalidRequest_returnsNoFollowers() throws IOException {
        LoginResponse response = loginService.login(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
