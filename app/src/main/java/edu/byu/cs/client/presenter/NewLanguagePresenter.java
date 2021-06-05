package edu.byu.cs.client.presenter;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.response.NewLanguageResponse;

import java.io.IOException;

import edu.byu.cs.client.model.service.NewLanguageServiceProxy;

public class NewLanguagePresenter {

    private final View view;

    public interface View {}

    public NewLanguagePresenter(View view) {
        this.view = view;
    }

    public NewLanguageResponse newLanguage(NewLanguageRequest request) throws IOException, RemoteException {
        NewLanguageServiceProxy serviceProxy = new NewLanguageServiceProxy();
        return serviceProxy.newLanguage(request);
    }
}
