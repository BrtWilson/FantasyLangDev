package edu.byu.cs.tweeter.presenter;

import java.io.IOException;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

/**
 * The presenter for the login functionality of the application.
 */
public class LoginPresenter {

    private final View view;
    private LoginService loginService;

    public interface View { }

    public LoginPresenter(View view) {
        this.view = view;
    }

    public LoginResponse login(LoginRequest loginRequest) throws IOException {
        loginService = getLoginService();
        return loginService.login(loginRequest);
    }

    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException {
        loginService = LoginService.getInstance();
        return loginService.logout(logoutRequest);
    }

    public LoginService getLoginService() {
        if (loginService == null) {
            loginService = LoginService.getInstance();
        }
        return loginService;
    }

    public View getView() {
        return view;
    }
}
