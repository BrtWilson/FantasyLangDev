package edu.byu.cs.client.presenter;

import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

import edu.byu.cs.client.model.service.SignUpServiceProxy;

public class SignUpPresenter {

    private final View view;

    public interface View {}

    public SignUpPresenter(View view) {
        this.view = view;
    }

    public RegisterResponse signUp(RegisterRequest request) throws IOException {
        SignUpServiceProxy serviceProxy = new SignUpServiceProxy();
        return serviceProxy.register(request);
    }
}
