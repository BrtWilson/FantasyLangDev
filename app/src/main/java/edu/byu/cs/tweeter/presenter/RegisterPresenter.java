package edu.byu.cs.tweeter.presenter;

import java.io.IOException;
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterPresenter {

    private final View view;
    private RegisterService registerService;

    public interface View { }

    public RegisterPresenter(View view) { this.view = view; }

    public RegisterResponse register(RegisterRequest registerRequest) throws IOException {
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
