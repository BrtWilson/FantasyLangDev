package com.example.server.service;

import com.example.server.dao.SyllableTableDAO;

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
public class SyllableService implements ISyllableService {

    /**
     * Returns an instance of {}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    public SyllableTableDAO getSyllableDao() {
        return new SyllableTableDAO();
    }

    @Override
    public GetLanguageDataResponse getSyllableData(GetLanguageDataRequest request) throws IOException {
        List<String> syllables = getSyllableDao().getSyllableData(request);
        String languageID = request.getLanguageID();

        GetLanguageDataResponse response = new GetLanguageDataResponse(null);

        return response;
    }
}