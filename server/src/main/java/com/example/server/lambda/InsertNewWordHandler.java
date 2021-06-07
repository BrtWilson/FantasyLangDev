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
import com.example.shared.model.service.request.NewWordRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.NewWordResponse;

import java.io.IOException;

public class InsertNewWordHandler implements RequestHandler<NewWordRequest, NewWordResponse> {
    @Override
    public NewWordResponse handleRequest(NewWordRequest input, Context context) {
        DictionaryService dictionaryService = new DictionaryService();
        NewWordResponse response = null;
        try {
            response = dictionaryService.insertNewWord(input);
        } catch (RuntimeException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }
        return response;
    }
}
