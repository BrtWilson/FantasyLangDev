package edu.byu.cs.tweeter.presenter;

import java.io.IOException;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class MainPresenter {

    private final View view;

    public interface  View { }

    public MainPresenter(View view) { this.view = view; }

    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException {
        LogoutService logoutService = new LogoutService();
        return logoutService.logout(logoutRequest);
    }
}
