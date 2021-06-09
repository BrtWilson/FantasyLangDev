package com.example.server.service;

import com.example.server.dao.DictionaryTableDAO;
import com.example.server.dao.LanguageTableDAO;
import com.example.server.dao.SyllableTableDAO;

import com.example.shared.model.domain.Language;
import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IGetLanguageDataService;
import com.example.shared.model.service.ISyllableService;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;

import java.io.IOException;
import java.util.List;

/**
 * Contains the business logic to support the login operation.
 */
public class GetLanguageDataService implements IGetLanguageDataService {


    @Override
    public GetLanguageDataResponse getLanguageData(GetLanguageDataRequest request) throws Exception {
        GetLanguageDataResponse response = getLanguageDAO().getLanguageData(request);
        return response;
    }

    public LanguageTableDAO getLanguageDAO() {
        return new LanguageTableDAO();
    }
}