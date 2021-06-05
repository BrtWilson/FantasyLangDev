package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.UpdateAlphabetRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.io.IOException;

import edu.byu.cs.client.presenter.UpdateAlphabetPresenter;

public class UpdateAlphabetTask extends AsyncTask<UpdateAlphabetRequest, Void, GeneralUpdateResponse> {

    private final UpdateAlphabetPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void updateAlphabet(GeneralUpdateResponse response);
        void handleException(Exception exception);
    }

    public UpdateAlphabetTask(UpdateAlphabetPresenter presenter, Observer observer) {
        if (observer == null) throw new NullPointerException();

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GeneralUpdateResponse doInBackground(UpdateAlphabetRequest... requests) {
        GeneralUpdateResponse response = null;

        try {
            response = presenter.updateAlphabet(requests[0]);
        }
        catch (IOException | RemoteException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GeneralUpdateResponse response) {
        if (exception != null) observer.handleException(exception);
        else observer.updateAlphabet(response);
    }
}
