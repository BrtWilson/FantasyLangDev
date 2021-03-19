package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import com.example.shared.model.service.request.FollowStatusRequest;
import com.example.shared.model.service.response.FollowStatusResponse;

import edu.byu.cs.client.presenter.FollowStatusPresenter;

public class GetFollowStatusTask extends AsyncTask<FollowStatusRequest,Void, FollowStatusResponse> {

    private final FollowStatusPresenter presenter; 
    private final Observer observer;
    private Exception exception;

    public GetFollowStatusTask(FollowStatusPresenter presenter, Observer observer) {
        if (observer == null)
            throw new NullPointerException();

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void followStatusRequestSuccessful(FollowStatusResponse followStatusResponse);
        void handleFollowStatusException(Exception exception);
    }

    @Override
    protected FollowStatusResponse doInBackground(FollowStatusRequest... followStatusRequests) {

        FollowStatusResponse response = null;
        try {
            response = presenter.getFollowStatus(followStatusRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }
        return response;
    }

    @Override
    protected void onPostExecute(FollowStatusResponse response) {
        if (exception != null)
            observer.handleFollowStatusException(exception);
        else
            observer.followStatusRequestSuccessful(response);
    }
}

