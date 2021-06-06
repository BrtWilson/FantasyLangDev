package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.io.IOException;

import edu.byu.cs.client.presenter.UpdateSyllablesPresenter;

public class UpdateSyllablesTask extends AsyncTask<UpdateSyllablesRequest, Void, GeneralUpdateResponse> {

    private final UpdateSyllablesPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void updateSyllables(GeneralUpdateResponse response);
        void handleException(Exception exception);
    }

    public UpdateSyllablesTask(UpdateSyllablesPresenter presenter, Observer observer) {
        if (observer == null) throw new NullPointerException();

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GeneralUpdateResponse doInBackground(UpdateSyllablesRequest... requests) {
        GeneralUpdateResponse response = null;

        try {
            response = presenter.updateSyllables(requests[0]);
        }
        catch (IOException | RemoteException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GeneralUpdateResponse response) {
        if (exception != null) observer.handleException(exception);
        else observer.updateSyllables(response);
    }
}