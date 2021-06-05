package com.example.shared.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.response.GetLanguageDataResponse;

import java.io.IOException;

public interface IGetLanguageDataService {
    public GetLanguageDataResponse getLanguageData(GetLanguageDataRequest request) throws IOException, RemoteException;
}
