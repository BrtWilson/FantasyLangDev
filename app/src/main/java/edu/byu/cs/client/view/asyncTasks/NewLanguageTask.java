package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.NewLanguageRequest;
import com.example.shared.model.service.response.NewLanguageResponse;

import java.io.IOException;

import edu.byu.cs.client.presenter.NewLanguagePresenter;

public class NewLanguageTask extends AsyncTask<NewLanguageRequest, Void, NewLanguageResponse> {

    private final NewLanguagePresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void newLanguage(NewLanguageResponse response);
        void handleException(Exception exception);
    }

    public NewLanguageTask(NewLanguagePresenter presenter, Observer observer) {
        if (observer == null) throw new NullPointerException();

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected NewLanguageResponse doInBackground(NewLanguageRequest... requests) {
        NewLanguageResponse response = null;

        try {
            response = presenter.newLanguage(requests[0]);
        }
        catch (IOException | RemoteException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(NewLanguageResponse response) {
        if (exception != null) observer.handleException(exception);
        else observer.newLanguage(response);
    }
}