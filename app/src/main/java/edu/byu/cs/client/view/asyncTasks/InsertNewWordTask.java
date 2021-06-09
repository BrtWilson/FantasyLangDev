package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.NewWordRequest;
import com.example.shared.model.service.response.NewWordResponse;

import java.io.IOException;

import edu.byu.cs.client.presenter.DictionaryPresenter;

public class InsertNewWordTask extends AsyncTask<NewWordRequest, Void, NewWordResponse> {

    private final DictionaryPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void insertNewWord(NewWordResponse response);
        void handleException(Exception exception);
    }

    public InsertNewWordTask(DictionaryPresenter presenter, Observer observer) {
        if (observer == null) throw new NullPointerException();

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected NewWordResponse doInBackground(NewWordRequest... requests) {
        NewWordResponse response = null;

        try {
            response = presenter.insertNewWord(requests[0]);
        }
        catch (IOException | RemoteException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(NewWordResponse response) {
        if (exception != null) observer.handleException(exception);
        else observer.insertNewWord(response);
    }
}
