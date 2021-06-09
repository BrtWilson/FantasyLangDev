package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.SearchWordRequest;
import com.example.shared.model.service.response.DictionaryPageResponse;

import java.io.IOException;

import edu.byu.cs.client.presenter.DictionaryPresenter;

public class SearchWordTask extends AsyncTask<SearchWordRequest, Void, DictionaryPageResponse> {

    private final DictionaryPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void searchWord(DictionaryPageResponse response);
        void handleException(Exception exception);
    }

    public SearchWordTask(DictionaryPresenter presenter, Observer observer) {
        if (observer == null) throw new NullPointerException();

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected DictionaryPageResponse doInBackground(SearchWordRequest... requests) {
        DictionaryPageResponse response = null;

        try {
            response = presenter.searchWord(requests[0]);
        }
        catch (IOException | RemoteException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(DictionaryPageResponse response) {
        if (exception != null) observer.handleException(exception);
        else observer.searchWord(response);
    }
}