package edu.byu.cs.client.presenter;


import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.LoginResponse;

import java.io.IOException;

import edu.byu.cs.client.model.service.LoginServiceProxy;

public class LoginPresenter {

    private final View view;

    public interface View {}

    public LoginPresenter(View view) {
        this.view = view;
    }

    public LoginResponse login(LoginRequest request) throws IOException, RemoteException {
        LoginServiceProxy serviceProxy = new LoginServiceProxy();
        return serviceProxy.login(request);
    }
}
