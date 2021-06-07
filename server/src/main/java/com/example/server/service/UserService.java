package com.example.server.service;

import com.example.server.dao.LanguageTableDAO;
import com.example.server.dao.UsersTableDAO;
import com.example.shared.model.domain.Language;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.IUserService;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.GetLanguageDataResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.RegisterResponse;

import org.apache.commons.codec.language.bm.Lang;

import java.io.IOException;
import java.util.List;

/**
 * Contains the business logic to support the login operation.
 */
public class UserService implements IUserService {
    @Override
    public LoginResponse login(LoginRequest request) throws IOException {
        User user = getUserDao().login(request);
        LoginResponse response = null;
        if(user.getUserName() != null){
            //get list of languages to make loginresponse object
            List<String> languages = getLanguageDao().getLanguages(request);
            List<Language> languageDatas = null;
            for (int i = 0; i < languages.size(); i++){
                GetLanguageDataRequest dataRequest = new GetLanguageDataRequest(languages.get(i));
                GetLanguageDataResponse dataResponse = getLanguageDao().getLanguageData(dataRequest);
                Language curLang = new Language(dataResponse.getLanguageID(), dataResponse.getUserName(), dataResponse.getLanguageName(), dataResponse.getAlphabet());
                languageDatas.add(curLang);
            }

            response = new LoginResponse(user, languageDatas);
        }
        else{
            response = new LoginResponse("Failed to log in");
        }

        return response;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) throws IOException {
        RegisterResponse response = getUserDao().register(request);

        return response;
    }

    /**
     * Returns an instance of {}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    public UsersTableDAO getUserDao() {
        return new UsersTableDAO();
    }

    public LanguageTableDAO getLanguageDao() {
        return new LanguageTableDAO();
    }


}