package com.example.server.service;

import com.example.server.dao.DictionaryTableDAO;
import com.example.server.dao.SyllableTableDAO;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IDictionaryService;
import com.example.shared.model.service.ISyllableService;
import com.example.shared.model.service.request.DeleteWordRequest;
import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.NewWordRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.request.SearchWordRequest;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.NewWordResponse;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;
import java.util.List;

/**
 * Contains the business logic to support the login operation.
 */
public class DictionaryService implements IDictionaryService {

    @Override
    public DictionaryPageResponse dictionary(DictionaryPageRequest request) throws IOException, RemoteException {
        DictionaryPageResponse response = getDictionaryDao().getWordArray(request);
        return response;
    }

    @Override
    public DictionaryPageResponse searchWord(SearchWordRequest request) {
        DictionaryPageResponse response = getDictionaryDao().searchWord(request);
        return response;
    }

    @Override
    public Boolean checkWordExists(NewWordRequest request) {
        Boolean response = getDictionaryDao().checkWordExists(request);
        return response;
    }

    @Override
    public NewWordResponse insertNewWord(NewWordRequest request) {
        NewWordResponse response = getDictionaryDao().insertNewWord(request);
        return response;
    }

    @Override
    public NewWordResponse updateWordAddToAttributes(NewWordRequest request) {
        NewWordResponse response = getDictionaryDao().updateWordAddToAttributes(request);
        return response;
    }

    @Override
    public NewWordResponse updateWord(NewWordRequest request) {
        NewWordResponse response = getDictionaryDao().updateWord(request);
        return response;
    }

    @Override
    public GeneralUpdateResponse deleteWord(DeleteWordRequest request) {
        GeneralUpdateResponse response = getDictionaryDao().deleteWord(request);
        return response;
    }

    public DictionaryTableDAO getDictionaryDao() {
        return new DictionaryTableDAO();
    }
}