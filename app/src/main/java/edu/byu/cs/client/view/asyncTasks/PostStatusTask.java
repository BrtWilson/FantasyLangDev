package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;
import com.example.shared.model.domain.User;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;
import edu.byu.cs.client.presenter.NewStatusPresenter;

public class PostStatusTask extends AsyncTask<NewStatusRequest, Void, NewStatusResponse> {

    private final NewStatusPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void newStatusSuccessful(NewStatusResponse newStatusResponse);
        void newStatusUnsuccessful(NewStatusResponse newStatusResponse);
        void handleException(Exception ex);
    }

    public PostStatusTask(NewStatusPresenter presenter, Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected NewStatusResponse doInBackground(NewStatusRequest... newStatusRequests) {
        NewStatusResponse newStatusResponse = null;

        try {
            newStatusResponse = presenter.newStatus(newStatusRequests[0]);
        } catch (IOException | TweeterRemoteException ex) {
            exception = ex;
        }

        return newStatusResponse;
    }

    @Override
    protected void onPostExecute(NewStatusResponse newStatusResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(newStatusResponse.isSuccess()) {
            observer.newStatusSuccessful(newStatusResponse);
        } else {
            observer.newStatusUnsuccessful(newStatusResponse);
        }
    }
}