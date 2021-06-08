package com.example.shared.model.service;
import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.DeleteWordRequest;
import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.NewWordRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.request.SearchWordRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.NewWordResponse;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

public interface IDictionaryService {
    public DictionaryPageResponse dictionary(DictionaryPageRequest request) throws IOException, RemoteException;//, RequestException, edu.byu.cs.client.model.net.ServerException;
    public DictionaryPageResponse searchWord(SearchWordRequest request) throws IOException, RemoteException;
    public Boolean checkWordExists(NewWordRequest request) throws IOException, RemoteException;
    public NewWordResponse insertNewWord(NewWordRequest request) throws IOException, RemoteException;
    public NewWordResponse updateWordAddToAttributes(NewWordRequest request) throws IOException, RemoteException;
    public NewWordResponse updateWord(NewWordRequest request) throws IOException, RemoteException;
    public GeneralUpdateResponse deleteWord(DeleteWordRequest request) throws IOException, RemoteException;
}
