package com.example.shared.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;

import java.io.IOException;
import java.rmi.ServerException;

public interface IDictionaryService {
    DictionaryPageResponse dictionary(DictionaryPageRequest request) throws IOException, ServerException, RemoteException;//, RequestException, edu.byu.cs.client.model.net.ServerException;
}
