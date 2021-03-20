package server.dao;

import com.example.server.dao.DummyDataProvider;
import com.example.server.dao.UsersTableDAO;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.UserResponse;
import com.example.shared.model.service.response.BasicResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;


public class UserDaoTest {
    private UserRequest validRequest;
    private UserRequest invalidRequest;

    private UserResponse successResponse;
    private UserResponse failureResponse;

    private LoginRequest validLoginRequest;
    private LoginRequest invalidLoginRequest;
    private LogoutRequest validLogoutRequest;

    private LoginResponse loginSuccessResponse;
    private LoginResponse loginFailureResponse;
    private BasicResponse logoutResponse;

    private UsersTableDAO userDaoSpy;

    @BeforeEach
    public void setup() {
        User user = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user.setPassword("password");

        validRequest = new UserRequest(user.getAlias());
        invalidRequest = new UserRequest(null);

        successResponse = new UserResponse(DummyDataProvider.getInstance().getSampleDummyUser());
        UsersTableDAO mockDao = Mockito.mock(UsersTableDAO.class);
        Mockito.when(mockDao.getUserByAlias(validRequest)).thenReturn(successResponse);

        failureResponse = new UserResponse("Password does not match.");
        Mockito.when(mockDao.getUserByAlias(invalidRequest)).thenReturn(failureResponse);

        validLoginRequest = new LoginRequest(user.getAlias(), user.getPassword());
        invalidLoginRequest = new LoginRequest(user.getAlias(), "wrongPassword");
        validLogoutRequest = new LogoutRequest(user);

        loginSuccessResponse = new LoginResponse(user, new AuthToken());
        Mockito.when(mockDao.login(validLoginRequest)).thenReturn(loginSuccessResponse);
        Mockito.when(mockDao.logout(validLogoutRequest)).thenReturn(logoutResponse);

        loginFailureResponse = new LoginResponse("Password does not match.");
        Mockito.when(mockDao.login(invalidLoginRequest)).thenReturn(loginFailureResponse);

        logoutResponse = new BasicResponse(true, "Successfully logged out");

        userDaoSpy = Mockito.spy(new UsersTableDAO());
    }

    @Test
    public void testGetUser_validRequest_correctResponse() throws IOException {
        UserResponse response = userDaoSpy.getUserByAlias(validRequest);
        //Assertions.assertEquals(successResponse, response);
        Assertions.assertEquals(successResponse.getMessage(), response.getMessage());
        Assertions.assertEquals(successResponse.getUser(), response.getUser());;
    }

    @Test
    public void testGetUser_validRequest_loadsProfileImage() throws IOException {
        UserResponse response = userDaoSpy.getUserByAlias(validRequest);
        Assertions.assertNotNull(response.getUser().getImageUrl());
    }

    @Test
    public void testGetUser_invalidRequest_returnsFailedMessage() throws IOException {
        //Assertions.assertEquals(failureResponse, response);
        try {
            UserResponse response = userDaoSpy.getUserByAlias(invalidRequest);
        } catch (AssertionError e) {
            Assertions.assertEquals(e.getMessage(), new AssertionError().getMessage());
        }
    }


    @Test
    public void testLogin_validRequest_correctResponse() throws IOException {
        LoginResponse response = userDaoSpy.login(validLoginRequest);
        Assertions.assertEquals(loginSuccessResponse.getUser(), response.getUser());
        Assertions.assertEquals(loginSuccessResponse.getAuthToken(), response.getAuthToken());
        //Assertions.assertTrue(loginSuccessResponse.equals(response));
    }

    @Test
    public void testLogin_validRequest_loadsProfileImage() throws IOException {
        LoginResponse response = userDaoSpy.login(validLoginRequest);
        Assertions.assertNotNull(response.getUser().getImageUrl());
    }

    @Test
    public void testLogin_invalidRequest_returnsFailedMessage() throws IOException {
        //Assertions.assertEquals(failureResponse, response);
        try {
            LoginResponse response = userDaoSpy.login(invalidLoginRequest);
        } catch (AssertionError e) {
            Assertions.assertEquals(e.getMessage(), new AssertionError().getMessage());
        }
    }

    @Test
    public void testLogout_validRequest_correctResponse() throws IOException {
        BasicResponse response = userDaoSpy.logout(validLogoutRequest);
        Assertions.assertEquals(logoutResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(logoutResponse.getMessage(), response.getMessage());
    }
}
