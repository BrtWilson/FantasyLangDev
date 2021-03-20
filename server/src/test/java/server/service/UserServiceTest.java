package server.service;

import com.example.server.dao.UsersTableDAO;
import com.example.server.service.UserService;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.UserResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;


public class UserServiceTest {
    private UserRequest validRequest;
    private UserRequest invalidRequest;

    private UserResponse successResponse;
    private UserResponse failureResponse;

    private UserService userService;

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

        userService = Mockito.spy(new UserService());
        Mockito.when(userService.getUserDao()).thenReturn(mockDao);
    }

    @Test
    public void testGetUser_validRequest_correctResponse() throws IOException {
        UserResponse response = userService.getUserByAlias(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetUser_validRequest_loadsProfileImage() throws IOException {
        UserResponse response = userService.getUserByAlias(validRequest);
        Assertions.assertNotNull(response.getUser().getImageBytes());
    }

    @Test
    public void testGetUser_invalidRequest_returnsFailedMessage() throws IOException {
        UserResponse response = userService.getUserByAlias(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
