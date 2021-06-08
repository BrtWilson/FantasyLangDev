package edu.byu.cs.client.presenter;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.request.NewWordRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;
import com.example.shared.model.service.response.NewWordResponse;

import java.io.IOException;

import edu.byu.cs.client.model.service.DictionaryServiceProxy;

public class DictionaryPresenter {

    private final View view;

    public interface View {}

    public DictionaryPresenter(View view) {
        this.view = view;
    }

    public DictionaryPageResponse dictionary(DictionaryPageRequest request) throws IOException, RemoteException {
        DictionaryServiceProxy serviceProxy = new DictionaryServiceProxy();
        return serviceProxy.dictionary(request);
    }

    public NewWordResponse insertNewWord(NewWordRequest request) throws IOException, RemoteException {
        DictionaryServiceProxy serviceProxy = new DictionaryServiceProxy();
        return serviceProxy.insertNewWord(request);
    }
}
