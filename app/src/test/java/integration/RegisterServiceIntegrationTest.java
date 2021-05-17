package integration;

import com.example.shared.model.domain.User;
import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;
import edu.byu.cs.client.model.service.oldfiles.RegisterService;


public class RegisterServiceIntegrationTest {

    private RegisterRequest validRequest1;
    private RegisterRequest validRequest2;
    private RegisterRequest invalidRequest;

    private RegisterResponse successResponse;
    private RegisterResponse successResponse2;
    private RegisterResponse failureResponse;

    private RegisterService registerService;

    @BeforeEach
    public void setup() throws IOException, RemoteException {
        User user1 = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        User user2 = new User("User", "Name", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        validRequest1 = new RegisterRequest(user1.getFirstName(), user1.getLastName(), user1.getAlias(), user1.getPassword(), user1.getImageUrl());
        validRequest2 = new RegisterRequest(user2.getFirstName(), user2.getLastName(), user2.getAlias(), user2.getPassword(), user2.getImageUrl());
        invalidRequest = new RegisterRequest("Ash", "Ahketchum", "@AshAhketchum", "password", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngitem.com%2Fmiddle%2FwomThJ_ash-ketchum-hd-png-download%2F&psig=AOvVaw2h43_Bi3x5gdd1y2tRmAhq&ust=1616605412770000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJjVytTyxu8CFQAAAAAdAAAAABAL");

        successResponse = new RegisterResponse(user1, new AuthToken(), true);
        successResponse2 = new RegisterResponse(user2, new AuthToken(), true);
        ServerFacade serverFacade = Mockito.spy(new ServerFacade());
        Mockito.when(serverFacade.register(validRequest1)).thenReturn(successResponse);
        Mockito.when(serverFacade.register(validRequest2)).thenReturn(successResponse2);

        failureResponse = new RegisterResponse("Username already taken. User different username.", false);
        Mockito.when(serverFacade.register(invalidRequest)).thenReturn(failureResponse);

        registerService = Mockito.spy(new RegisterService());
        //Mockito.when(registerService.getServerFacade()).thenReturn(serverFacade);
//        Mockito.when(registerService.getServerFacade()).thenReturn(serverFacade); // this is commented out because registerService uses static serverFacade
    }

    @Test
    public void testRegister_validRequest_correctResponse() throws IOException, RemoteException {
        RegisterResponse response = registerService.register(validRequest1);
        System.out.println(response.getUser());
        Assertions.assertEquals(successResponse.isSuccess(), response.isSuccess());
    }

    @Test
    public void testRegister_validRequest_loadsProfileImage() throws IOException, RemoteException {
        RegisterResponse response = registerService.register(validRequest2);
        Assertions.assertNotNull(response.getUser().getImageBytes());
    }

    @Test
    public void testRegister_invalidRequest_returnsFailedMessage() throws IOException, RemoteException {
        RegisterResponse response = registerService.register(invalidRequest);
        //Assertions.assertEquals(failureResponse.getMessage(), response.getMessage());
        Assertions.assertEquals(failureResponse.isSuccess(), response.isSuccess());
    }
}
