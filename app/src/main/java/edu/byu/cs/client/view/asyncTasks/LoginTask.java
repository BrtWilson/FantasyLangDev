package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.LoginResponse;

import java.io.IOException;

import edu.byu.cs.client.presenter.LoginPresenter;

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginResponse> {

    private final LoginPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void login(LoginResponse loginResponse);
        void handleException(Exception exception);
    }

    public LoginTask(LoginPresenter presenter, Observer observer) {
        if (observer == null) throw new NullPointerException();

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... loginRequests) {
        LoginResponse loginResponse = null;

        try {
            loginResponse = presenter.login(loginRequests[0]);
        }
        catch (IOException ex) {
            exception = ex;
        }

        return loginResponse;
    }

    @Override
    protected void onPostExecute(LoginResponse loginResponse) {
        if (exception != null) observer.handleException(exception);
        else observer.login(loginResponse);
    }
}
