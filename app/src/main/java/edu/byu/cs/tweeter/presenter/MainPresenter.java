package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class MainPresenter {

    private final View view;

    public interface  View { }

    public MainPresenter(View view) { this.view = view; }

    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException {
        LoginService loginService = new LoginService();
        return loginService.logout(logoutRequest);
    }
}
