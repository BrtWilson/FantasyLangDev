package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.response.BasicResponse;
import edu.byu.cs.client.presenter.LoginPresenter;

public class LogoutTask extends AsyncTask<LogoutRequest, Void, BasicResponse> {

    private final LoginPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void logoutSuccessful(BasicResponse logoutResponse);
        void logoutUnsuccessful(BasicResponse logoutResponse);
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
    protected BasicResponse doInBackground(LogoutRequest... logoutRequests) {
        BasicResponse logoutResponse = null;
        try {
            logoutResponse = presenter.logout(logoutRequests[0]);
            if(logoutResponse.isSuccess()) {
                System.out.println("Logout successful");
            }
            else {
                logoutResponse = new BasicResponse(false, "Logout failed in LogoutTask - doInBackground.");
            }
        } catch (IOException | TweeterRemoteException ex) {
            exception = ex;
        }
        return logoutResponse;
    }

    @Override
    protected void onPostExecute(BasicResponse logoutResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(logoutResponse.isSuccess()) {
            observer.logoutSuccessful(logoutResponse);
        } else {
            observer.logoutUnsuccessful(logoutResponse);
        }
    }
}
