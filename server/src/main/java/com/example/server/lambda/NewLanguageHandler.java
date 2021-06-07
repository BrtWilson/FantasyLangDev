package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.DictionaryService;
import com.example.server.service.LanguageService;
import com.example.server.service.NewLanguageService;
import com.example.server.service.UserService;
import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.DeleteWordRequest;
import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.request.NewWordRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.NewLanguageResponse;

import java.io.IOException;
import java.util.List;

public class NewLanguageHandler implements RequestHandler<NewLanguageRequest, NewLanguageResponse> {
    @Override
    public NewLanguageResponse handleRequest(NewLanguageRequest input, Context context) {
        NewLanguageService newLanguageService = new NewLanguageService();
        NewLanguageResponse response = null;
        try {
            response = newLanguageService.newLanguage(input);
        } catch (RuntimeException | IOException | RemoteException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }
        return response;
    }
}
