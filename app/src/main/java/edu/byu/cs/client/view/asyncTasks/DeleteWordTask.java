package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.DeleteWordRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.io.IOException;

import edu.byu.cs.client.presenter.DictionaryPresenter;

public class DeleteWordTask extends AsyncTask<DeleteWordRequest, Void, GeneralUpdateResponse> {

    private final DictionaryPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void deleteWord(GeneralUpdateResponse response);
        void handleException(Exception exception);
    }

    public DeleteWordTask(DictionaryPresenter presenter, Observer observer) {
        if (observer == null) throw new NullPointerException();

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GeneralUpdateResponse doInBackground(DeleteWordRequest... requests) {
        GeneralUpdateResponse response = null;

        try {
            response = presenter.deleteWord(requests[0]);
        }
        catch (IOException | RemoteException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GeneralUpdateResponse response) {
        if (exception != null) observer.handleException(exception);
        else observer.deleteWord(response);
    }
}