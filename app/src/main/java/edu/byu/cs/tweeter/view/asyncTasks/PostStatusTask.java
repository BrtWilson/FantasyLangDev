package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.NewStatusRequest;
import edu.byu.cs.tweeter.model.service.response.NewStatusResponse;
import edu.byu.cs.tweeter.presenter.NewStatusPresenter;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

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
        } catch (IOException ex) {
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