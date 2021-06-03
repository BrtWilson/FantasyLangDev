package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.model.service.request.DictionaryPageRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;

import java.io.IOException;

import edu.byu.cs.client.presenter.DictionaryPresenter;

public class DictionaryTask extends AsyncTask<DictionaryPageRequest, Void, DictionaryPageResponse> {

    private final DictionaryPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void dictionariesRetrieved(DictionaryPageResponse response);
        void handleException(Exception exception);
    }

    public DictionaryTask(DictionaryPresenter presenter, Observer observer) {
        if (observer == null) throw new NullPointerException();

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected DictionaryPageResponse doInBackground(DictionaryPageRequest... requests) {
        DictionaryPageResponse response = null;

        try {
            response = presenter.dictionary(requests[0]);
        }
        catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(DictionaryPageResponse response) {
        if (exception != null) observer.handleException(exception);
        else observer.dictionariesRetrieved(response);
    }
}