package edu.byu.cs.client.presenter;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.io.IOException;

import edu.byu.cs.client.model.service.UpdateSyllablesServiceProxy;

public class UpdateSyllablesPresenter {

    private final View view;

    public interface View {}

    public UpdateSyllablesPresenter(View view) {
        this.view = view;
    }

    public GeneralUpdateResponse updateSyllables(UpdateSyllablesRequest request) throws IOException, RemoteException {
        UpdateSyllablesServiceProxy serviceProxy = new UpdateSyllablesServiceProxy();
        return serviceProxy.updateSyllables(request);
    }
}
