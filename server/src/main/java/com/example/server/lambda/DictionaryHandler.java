package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.DictionaryService;
import com.example.server.service.UserService;
import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.DeleteWordRequest;
import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;
import com.example.shared.model.service.response.LoginResponse;

import java.io.IOException;

public class DictionaryHandler implements RequestHandler<DictionaryPageRequest, DictionaryPageResponse> {
    @Override
    public DictionaryPageResponse handleRequest(DictionaryPageRequest input, Context context) {
        DictionaryService dictionaryService = new DictionaryService();
        DictionaryPageResponse response = null;
        try {
            response = dictionaryService.dictionary(input);
        } catch (RuntimeException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
