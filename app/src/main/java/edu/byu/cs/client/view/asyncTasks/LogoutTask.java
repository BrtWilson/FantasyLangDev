package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.response.LogoutResponse;
import edu.byu.cs.client.presenter.LoginPresenter;

public class LogoutTask extends AsyncTask<LogoutRequest, Void, LogoutResponse> {

    private final LoginPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void logoutSuccessful(LogoutResponse logoutResponse);
        void logoutUnsuccessful(LogoutResponse logoutResponse);
        void handleException(Exception ex);
    }

    public LogoutTask(LoginPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected LogoutResponse doInBackground(LogoutRequest... logoutRequests) {
        LogoutResponse logoutResponse = null;
        try {
            logoutResponse = presenter.logout(logoutRequests[0]);
            if(logoutResponse.isSuccess()) {
                System.out.println("Logout successful");
            }
            else {
                logoutResponse = new LogoutResponse(false, "Logout failed in LogoutTask - doInBackground.");
            }
        } catch (IOException | TweeterRemoteException ex) {
            exception = ex;
        }
        return logoutResponse;
    }

    @Override
    protected void onPostExecute(LogoutResponse logoutResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(logoutResponse.isSuccess()) {
            observer.logoutSuccessful(logoutResponse);
        } else {
            observer.logoutUnsuccessful(logoutResponse);
        }
    }
}
