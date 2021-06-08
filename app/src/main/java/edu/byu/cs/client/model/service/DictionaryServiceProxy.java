package edu.byu.cs.client.model.service;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.IDictionaryService;
import com.example.shared.model.service.request.DeleteWordRequest;
import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.request.NewWordRequest;
import com.example.shared.model.service.request.SearchWordRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.NewWordResponse;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;

public class DictionaryServiceProxy implements IDictionaryService {
    private static final String URL_PATH = "/dictionary";

    public DictionaryPageResponse dictionary(DictionaryPageRequest request) throws IOException, RemoteException {
        ServerFacade serverFacade = getServerFacade();
        DictionaryPageResponse response = serverFacade.dictionary(request);//, URL_PATH);

        return response;
    }

//    @Override
    public DictionaryPageResponse getWordArray(DictionaryPageRequest request) throws IOException, RemoteException {
        return null;
    }

    @Override
    public DictionaryPageResponse searchWord(SearchWordRequest request) throws IOException, RemoteException {
        ServerFacade serverFacade = getServerFacade();
        DictionaryPageResponse response = serverFacade.searchWord(request);//, URL_PATH);

        return response;
    }

    @Override
    public Boolean checkWordExists(NewWordRequest request) {
        return null;
    }

    @Override
    public NewWordResponse insertNewWord(NewWordRequest request)  throws IOException, RemoteException {
        ServerFacade serverFacade = getServerFacade();
        NewWordResponse response = serverFacade.newWord(request);//, URL_PATH);

        return response;
    }

    @Override
    public NewWordResponse updateWordAddToAttributes(NewWordRequest request) {
        return null;
    }

    @Override
    public NewWordResponse updateWord(NewWordRequest request) throws IOException, RemoteException {
        ServerFacade serverFacade = getServerFacade();
        NewWordResponse response = serverFacade.updateWord(request);//, URL_PATH);

        return response;
    }

    @Override
    public GeneralUpdateResponse deleteWord(DeleteWordRequest request) throws IOException, RemoteException {
        ServerFacade serverFacade = getServerFacade();
        GeneralUpdateResponse response = serverFacade.deleteWord(request);//, URL_PATH);

        return response;
    }

    ServerFacade getServerFacade() { return new ServerFacade(); }
}
