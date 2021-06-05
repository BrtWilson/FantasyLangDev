package edu.byu.cs.client.presenter;

import android.view.View;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.response.GetLanguageDataResponse;

import java.io.IOException;

import edu.byu.cs.client.model.service.GetLanguageDataServiceProxy;

public class GetLanguageDataPresenter {
    private final View view;

    public interface View{}

    public GetLanguageDataPresenter(View view) { this.view = view; }

    public GetLanguageDataResponse getLanguageData(GetLanguageDataRequest request) throws IOException, RemoteException {
        GetLanguageDataServiceProxy serviceProxy = new GetLanguageDataServiceProxy();
        return serviceProxy.getLanguageData(request);
    }
}
