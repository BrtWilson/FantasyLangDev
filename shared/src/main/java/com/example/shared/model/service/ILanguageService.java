package com.example.shared.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.request.UpdateAlphabetRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;
import com.example.shared.model.service.response.NewLanguageResponse;

import java.io.IOException;
import java.util.List;

public interface ILanguageService {

    public List<String> getLanguages(LoginRequest request);
}