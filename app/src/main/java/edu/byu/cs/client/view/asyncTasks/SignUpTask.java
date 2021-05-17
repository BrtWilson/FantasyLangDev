package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.IOException;

import edu.byu.cs.client.presenter.SignUpPresenter;

public class SignUpTask extends AsyncTask<RegisterRequest, Void, RegisterResponse> {

    private final SignUpPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void signUp(RegisterResponse response);
        void handleException(Exception exception);
    }

    public SignUpTask(SignUpPresenter presenter, Observer observer) {
        if (observer == null) throw new NullPointerException();

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected RegisterResponse doInBackground(RegisterRequest... requests) {
        RegisterResponse response = null;

        try {
            response = presenter.signUp(requests[0]);
        }
        catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(RegisterResponse response) {
        if (exception != null) observer.handleException(exception);
        else observer.signUp(response);
    }
}
