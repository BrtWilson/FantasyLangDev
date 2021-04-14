package server.dao;

import com.example.server.dao.DummyDataProvider;
import com.example.server.dao.AuthTableDAO;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.BasicResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.UserResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;


public class AuthDaoTest {

    private AuthToken validAuthToken;
    private AuthToken invalidAuthToken;
    private String userAlias;

    private AuthToken successStartingResponse;
    private LogoutRequest validLogoutRequest;

    private AuthTableDAO AuthDaoSpy;

    @BeforeEach
    public void setup() {
        User user = new User("Harry", "Potter", "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142");
        user.setPassword("hello1234");
        userAlias = "@HarryPotter";
        String secondUserAlias = "@AnakinSkywalker";
        
        validAuthToken = new AuthToken(userAlias);
        invalidAuthToken = new AuthToken(secondUserAlias);

        //DynamoDBStrategy mockDatabaseInteractor = Mockito.mock(DynamoDBStrategy.class);
//        Mockito.when(AuthDaoSpy.getDatabaseInteractor()).thenReturn(mockDatabaseInteractor);

        //startingAuth
        successStartingResponse = new AuthToken(userAlias);
        AuthTableDAO mockDao = Mockito.mock(AuthTableDAO.class);
        //Mockito.when(mockDao.startingAuth(userAlias)).thenReturn(validAuthToken);

        //getAuthorized
        //Mockito.when(mockDao.getAuthorized(validAuthToken)).thenReturn(true);
        //Mockito.when(mockDao.getAuthorized(invalidAuthToken)).thenReturn(false);

        //logoutToken
        validLogoutRequest = new LogoutRequest(user);

        AuthDaoSpy = Mockito.spy(new AuthTableDAO());
    }

    @Test
    public void testStartingAuthToken_validRequest_returnsAuthToken() throws IOException {
        AuthToken response = AuthDaoSpy.startingAuth(userAlias);
        Assertions.assertEquals(validAuthToken, response);
    }
    
    @Test
    public void testGetAuthorized_validRequest_correctResponse() throws IOException {
        Boolean response = AuthDaoSpy.getAuthorized(validAuthToken);
        Assertions.assertEquals(true, response);
    }

    @Test
    public void testGetAuthorized_invalidRequest_failureResponse() throws IOException {
        Boolean response = AuthDaoSpy.getAuthorized(invalidAuthToken);
        Assertions.assertEquals(false, response);
    }

    @Test
    public void testLogoutToken_validRequest_correctResponse() throws IOException {
        Boolean response = AuthDaoSpy.logoutToken(validLogoutRequest);
        Assertions.assertEquals(true, response);
    }

}
