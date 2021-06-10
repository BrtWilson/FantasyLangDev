package integration;

import com.example.shared.model.domain.Language;
import com.example.shared.model.domain.User;
import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.GetLanguageDataResponse;
import com.example.shared.model.service.response.LoginResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.client.model.service.GetLanguageDataServiceProxy;
import edu.byu.cs.client.model.service.LoginServiceProxy;

public class LoginTest {

    private LoginRequest loginRequest;
    private LoginResponse loginResponse;
    private GetLanguageDataRequest getLanguageDataRequest;
    private GetLanguageDataResponse getLanguageDataResponse;

    private final GetLanguageDataServiceProxy gldsProxy = new GetLanguageDataServiceProxy();
    private final LoginServiceProxy loginServiceProxy = new LoginServiceProxy();

    @BeforeEach
    public void setup() throws IOException {
        User existingUser = new User("huh", "huh", "password");
        loginRequest = new LoginRequest(existingUser.getUserName(), existingUser.getPassword());
        getLanguageDataRequest = new GetLanguageDataRequest("huh-language");

        List<Language> languagesList = new ArrayList<>();
        Language huhLang = new Language("huh-language", "huh", "language", "a b c");
        languagesList.add(huhLang);
        loginResponse = new LoginResponse(existingUser, languagesList);

        getLanguageDataResponse = new GetLanguageDataResponse(existingUser.getUserName(), huhLang.getLanguageName(), huhLang.getLanguageID(), huhLang.getAlphabet());
    }

    @Test
    public void testGetLanguageData() throws IOException, RemoteException {
        GetLanguageDataResponse response = gldsProxy.getLanguageData(getLanguageDataRequest);
        Assertions.assertEquals(getLanguageDataResponse.getLanguageID(), response.getLanguageID());
        Assertions.assertEquals(getLanguageDataResponse.getLanguageName(), response.getLanguageName());
        Assertions.assertEquals(getLanguageDataResponse.getUserName(), response.getUserName());
        Assertions.assertEquals(getLanguageDataResponse.getAlphabet(), response.getAlphabet());
    }

    @Test
    public void testLogin() throws IOException, RemoteException {
        LoginResponse response = loginServiceProxy.login(loginRequest);
        Assertions.assertEquals(loginResponse, response);
    }
}
