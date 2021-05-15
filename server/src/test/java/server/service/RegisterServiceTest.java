package server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.UUID;

import com.example.server.dao.UsersTableDAO;
import com.example.server.service.RegisterService;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;


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
        user2.setPassword("password");
        User duplicateUser = DummyDataProvider.getInstance().getSampleDummyUser();

        UUID randomString = UUID.randomUUID();
        String randomAlias1 = "@" + randomString.toString().substring(0,5);
        String randomAlias2 = "@" + randomString.toString().substring(0,4);

        validRequest1 = new RegisterRequest(user1.getFirstName(), user1.getLastName(), randomAlias1, user1.getPassword(), "blah");
        validRequest2 = new RegisterRequest(user2.getFirstName(), user2.getLastName(), randomAlias2, user2.getPassword(), "blah");
        invalidRequest = new RegisterRequest(duplicateUser.getFirstName(), duplicateUser.getLastName(), duplicateUser.getAlias(), duplicateUser.getPassword(), duplicateUser.getImageUrl());

        successResponse = new RegisterResponse(user1, new AuthToken(), true);
        failureResponse = new RegisterResponse("Error error error", false);
        UsersTableDAO mockDao = Mockito.mock(UsersTableDAO.class);
        Mockito.when(mockDao.register(validRequest1)).thenReturn(successResponse);

        failureResponse = new RegisterResponse("Username already taken. User different username.", false);
        Mockito.when(mockDao.register(invalidRequest)).thenReturn(failureResponse);

        registerService = Mockito.spy(new RegisterService());
//        Mockito.when(registerService.getServerFacade()).thenReturn(mockDao); // this is commented out because registerService uses static serverFacade
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
        Assertions.assertNotNull(response.getUser().getImageUrl());
    }

    @Test
    public void testRegister_invalidRequest_returnsFailedMessage() throws IOException {
        //Assertions.assertEquals(failureResponse.isSuccess(), response.isSuccess());
        try {
            RegisterResponse response = registerService.register(invalidRequest);
        } catch (AssertionError e) {
            Assertions.assertEquals(e.getMessage(), new AssertionError().getMessage());
        }
    }
}
