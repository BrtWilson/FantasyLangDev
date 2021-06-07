package com.example.server.service;

import com.example.server.dao.DictionaryTableDAO;
import com.example.server.dao.LanguageTableDAO;
import com.example.server.dao.SyllableTableDAO;

import com.example.shared.model.domain.Language;
import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IGetLanguageDataService;
import com.example.shared.model.service.ILanguageService;
import com.example.shared.model.service.ISyllableService;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.request.UpdateAlphabetRequest;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;
import com.example.shared.model.service.response.NewLanguageResponse;

import java.io.IOException;
import java.util.List;

/**
 * Contains the business logic to support the login operation.
 */
public class LanguageService implements ILanguageService {




    public LanguageTableDAO getLanguageDAO() {
        return new LanguageTableDAO();
    }

    @Override
    public List<String> getLanguages(LoginRequest request) {
        List<String> response = getLanguageDAO().getLanguages(request);
        return response;
    }
}