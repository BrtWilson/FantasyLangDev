package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.model.net.RemoteException;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.response.GetLanguageDataResponse;

import java.io.IOException;

import edu.byu.cs.client.presenter.GetLanguageDataPresenter;

public class GetLanguageDataTask extends AsyncTask<GetLanguageDataRequest, Void, GetLanguageDataResponse> {

    private final GetLanguageDataPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void getLanguageData(GetLanguageDataResponse response);
        void handleException(Exception exception);
    }

    public GetLanguageDataTask(GetLanguageDataPresenter presenter, Observer observer) {
        if (observer == null) throw new NullPointerException();

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GetLanguageDataResponse doInBackground(GetLanguageDataRequest... requests) {
        GetLanguageDataResponse response = null;

        try {
            response = presenter.getLanguageData(requests[0]);
        }
        catch (IOException | RemoteException ex) {
            exception = ex;
        }

        return response;
    }

    @Override
    protected void onPostExecute(GetLanguageDataResponse response) {
        if (exception != null) observer.handleException(exception);
        else observer.getLanguageData(response);
    }
}