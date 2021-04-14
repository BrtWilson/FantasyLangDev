package server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;

import com.example.server.dao.UsersTableDAO;
import com.example.server.service.LoginService;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.BasicResponse;


public class LoginServiceTest {
    private LoginRequest validRequest;
    private LoginRequest invalidRequest;
    private LogoutRequest validLogoutRequest;

    private LoginResponse successResponse;
    private LoginResponse failureResponse;
    private BasicResponse logoutResponse;

    private LoginService loginService;

    @BeforeEach
    public void setup() {
        User user = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user.setPassword("password");

        validRequest = new LoginRequest(user.getAlias(), user.getPassword());
        invalidRequest = new LoginRequest(user.getAlias(), "wrongPassword");
        validLogoutRequest = new LogoutRequest(user.getAlias());

        successResponse = new LoginResponse(user, new AuthToken());
        logoutResponse = new BasicResponse(true, "Successfully logged out");
        UsersTableDAO mockDao = Mockito.mock(UsersTableDAO.class);
        Mockito.when(mockDao.login(validRequest)).thenReturn(successResponse);
        Mockito.when(mockDao.logout(validLogoutRequest)).thenReturn(logoutResponse);

        failureResponse = new LoginResponse("Password does not match.");
        Mockito.when(mockDao.login(invalidRequest)).thenReturn(failureResponse);


        loginService = Mockito.spy(new LoginService());
        Mockito.when(loginService.getLoginDao()).thenReturn(mockDao);
    }

    @Test
    public void testLogin_validRequest_correctResponse() throws IOException {
        LoginResponse response = loginService.login(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testLogin_validRequest_loadsProfileImage() throws IOException {
        LoginResponse response = loginService.login(validRequest);
        Assertions.assertNotNull(response.getUser().getImageUrl());
    }

    @Test
    public void testLogin_invalidRequest_returnsFailedMessage() throws IOException {
        LoginResponse response = loginService.login(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testLogout_validRequest_correctResponse() throws IOException {
        BasicResponse response = loginService.logout(validLogoutRequest);
        Assertions.assertEquals(logoutResponse.getMessage(), response.getMessage());
    }
}
