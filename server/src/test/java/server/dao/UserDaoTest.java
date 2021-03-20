package server.dao;

import com.example.server.dao.FollowsTableDAO;
import com.example.server.dao.UsersTableDAO;
import com.example.server.service.UserService;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.UserResponse;
import com.example.shared.model.service.response.LogoutResponse;
import com.example.shared.model.service.response.UserResponse;

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
    private LogoutResponse logoutResponse;

    private UsersTableDAO userDaoSpy;

    @BeforeEach
    public void setup() {
        User user = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user.setPassword("password");

        validRequest = new UserRequest(user.getAlias());
        invalidRequest = new UserRequest(null);

        successResponse = new UserResponse(user);
        UsersTableDAO mockDao = Mockito.mock(UsersTableDAO.class);
        Mockito.when(mockDao.getUserByAlias(validRequest)).thenReturn(successResponse);

        failureResponse = new UserResponse("Password does not match.");
        Mockito.when(mockDao.getUserByAlias(invalidRequest)).thenReturn(failureResponse);

        userDaoSpy = Mockito.spy(new UsersTableDAO());
    }

    @Test
    public void testGetUser_validRequest_correctResponse() throws IOException {
        UserResponse response = userDaoSpy.getUserByAlias(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetUser_validRequest_loadsProfileImage() throws IOException {
        UserResponse response = userDaoSpy.getUserByAlias(validRequest);
        Assertions.assertNotNull(response.getUser().getImageBytes());
    }

    @Test
    public void testGetUser_invalidRequest_returnsFailedMessage() throws IOException {
        UserResponse response = userDaoSpy.getUserByAlias(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }


    @Test
    public void testLogin_validRequest_correctResponse() throws IOException {
        LoginResponse response = userDaoSpy.login(validLoginRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testLogin_validRequest_loadsProfileImage() throws IOException {
        LoginResponse response = userDaoSpy.login(validLoginRequest);
        Assertions.assertNotNull(response.getUser().getImageBytes());
    }

    @Test
    public void testLogin_invalidRequest_returnsFailedMessage() throws IOException {
        LoginResponse response = userDaoSpy.login(invalidLoginRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testLogout_validRequest_correctResponse() throws IOException {
        LogoutResponse response = userDaoSpy.logout(validLogoutRequest);
        Assertions.assertEquals(successResponse, response);
    }
}
