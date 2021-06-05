package com.example.shared.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.response.NewLanguageResponse;

import java.io.IOException;

public interface INewLanguageService {
    public NewLanguageResponse newLanguage(NewLanguageRequest request) throws IOException, RemoteException;
}
