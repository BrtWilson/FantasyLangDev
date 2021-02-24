package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;


public class RegisterServiceTest {

    private RegisterRequest validRequest1;
    private RegisterRequest validRequest2;
    private RegisterRequest invalidRequest;

    private RegisterResponse successResponse;
    private RegisterResponse failureResponse;

    private RegisterService registerService;

    @BeforeEach
    public void setup() {
        User user1 = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        User user2 = new User("User", "Name", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        validRequest1 = new RegisterRequest(user1.getFirstName(), user1.getLastName(), user1.getAlias(), user1.getPassword(), user1.getImageUrl());
        validRequest2 = new RegisterRequest(user2.getFirstName(), user2.getLastName(), user2.getAlias(), user2.getPassword(), user2.getImageUrl());
        invalidRequest = new RegisterRequest(user1.getFirstName(), user1.getLastName(), user1.getAlias(), user1.getPassword(), user1.getImageUrl());

        successResponse = new RegisterResponse(user1, new AuthToken(), true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.register(validRequest1)).thenReturn(successResponse);

        failureResponse = new RegisterResponse("Username already taken. User different username.", false);
        Mockito.when(mockServerFacade.register(invalidRequest)).thenReturn(failureResponse);

        registerService = Mockito.spy(new RegisterService());
    }

    @Test
    public void testRegister_validRequest_correctResponse() throws IOException {
        RegisterResponse response = registerService.register(validRequest1);
        System.out.println(response.getUser());
        Assertions.assertEquals(successResponse.isSuccess(), response.isSuccess());
    }

    @Test
    public void testRegister_validRequest_loadsProfileImage() throws IOException {
        RegisterResponse response = registerService.register(validRequest2);
        Assertions.assertNotNull(response.getUser().getImageBytes());
    }

    @Test
    public void testRegister_invalidRequest_returnsFailedMessage() throws IOException {
        RegisterResponse response = registerService.register(invalidRequest);
        Assertions.assertEquals(failureResponse.isSuccess(), response.isSuccess());
    }
}
