package server.service;

import com.example.server.dao.UsersTableDAO;
import com.example.server.service.UserService;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.response.GetLanguageDataResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;


public class UserServiceTest {
    private GetLanguageDataRequest validRequest;
    private GetLanguageDataRequest invalidRequest;

    private GetLanguageDataResponse successResponse;
    private GetLanguageDataResponse failureResponse;

    private UserService userService;

    @BeforeEach
    public void setup() {
        User user = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user.setPassword("password");

        validRequest = new GetLanguageDataRequest(user.getAlias());
        invalidRequest = new GetLanguageDataRequest(null);

        successResponse = new GetLanguageDataResponse(user);
        UsersTableDAO mockDao = Mockito.mock(UsersTableDAO.class);
        Mockito.when(mockDao.getUserByAlias(validRequest)).thenReturn(successResponse);

        failureResponse = new GetLanguageDataResponse("Password does not match.");
        Mockito.when(mockDao.getUserByAlias(invalidRequest)).thenReturn(failureResponse);

        userService = Mockito.spy(new UserService());
        Mockito.when(userService.getUserDao()).thenReturn(mockDao);
    }

    @Test
    public void testGetUser_validRequest_correctResponse() throws IOException {
        GetLanguageDataResponse response = userService.getUserByAlias(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetUser_validRequest_loadsProfileImage() throws IOException {
        GetLanguageDataResponse response = userService.getUserByAlias(validRequest);
        Assertions.assertNotNull(response.getUser().getImageUrl());
    }

    @Test
    public void testGetUser_invalidRequest_returnsFailedMessage() throws IOException {
        GetLanguageDataResponse response = userService.getUserByAlias(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
