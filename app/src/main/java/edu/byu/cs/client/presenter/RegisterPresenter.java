package edu.byu.cs.client.presenter;

import java.io.IOException;
import edu.byu.cs.client.model.service.RegisterService;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

public class RegisterPresenter {

    private final View view;
    private RegisterService registerService;

    public interface View { }

    public RegisterPresenter(View view) { this.view = view; }

    public RegisterResponse register(RegisterRequest registerRequest) throws IOException, TweeterRemoteException {
        registerService = getRegisterService();
        return registerService.register(registerRequest);
    }

    public RegisterService getRegisterService() {
        if (registerService == null) {
            registerService = new RegisterService();
        }
        return registerService;
    }
}
