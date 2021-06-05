package edu.byu.cs.client.presenter;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.UpdateAlphabetRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.io.IOException;

import edu.byu.cs.client.model.service.UpdateAlphabetServiceProxy;

public class UpdateAlphabetPresenter {

    private final View view;

    public interface View {}

    public UpdateAlphabetPresenter(View view) {
        this.view = view;
    }

    public GeneralUpdateResponse updateAlphabet(UpdateAlphabetRequest request) throws IOException, RemoteException {
        UpdateAlphabetServiceProxy serviceProxy = new UpdateAlphabetServiceProxy();
        return serviceProxy.updateAlphabet(request);
    }
}
