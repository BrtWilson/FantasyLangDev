package server.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.LogoutRequest;

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

    private AuthTableDAO ourAuthDao;
    private String tableName = "AuthTokens";
    private static final String keyAttribute = "Alias";
    private static final String secondaryKey = "Token";

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
        validLogoutRequest = new LogoutRequest(user.getAlias());

        ourAuthDao = new AuthTableDAO();
    }

    @Test
    public void testStartingAuthToken_validRequest_returnsAuthToken() throws IOException {
        AuthToken response = ourAuthDao.startingAuth(userAlias);
        Assertions.assertNotNull(response);
        Item item = DynamoDBStrategy.basicGetItemWithDualKey(tableName, keyAttribute, response.getUserName(), secondaryKey, response.getToken());
        Assertions.assertNotNull(item);
    }
    
    @Test
    public void testGetAuthorized_validRequest_correctResponse() throws IOException {
        AuthToken response_0 = ourAuthDao.startingAuth(userAlias);
        Boolean response = ourAuthDao.getAuthorized(response_0);
        Assertions.assertEquals(true, response);
    }

    @Test
    public void testGetAuthorized_invalidRequest_failureResponse() throws IOException {
        Boolean response = ourAuthDao.getAuthorized(invalidAuthToken);
        Assertions.assertEquals(false, response);
    }

    @Test
    public void testLogoutToken_validRequest_correctResponse() throws IOException {
        AuthToken response_0 = ourAuthDao.startingAuth(userAlias);
        validLogoutRequest.setToken(response_0);
        Boolean response = ourAuthDao.logoutToken(validLogoutRequest);
        Assertions.assertEquals(true, response);
    }

}
