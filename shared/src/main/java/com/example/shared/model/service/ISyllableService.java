package com.example.shared.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;

import java.io.IOException;
import java.util.List;

public interface ISyllableService {

    public GetLanguageDataResponse getSyllableData(GetLanguageDataRequest request) throws IOException;
}