package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.StatusArrayRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.StatusArrayResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.presenter.StatusArrayPresenter;

/**
 * An {@link AsyncTask} for retrieving followees for a user.
 */
public class GetStatusesTask extends AsyncTask<StatusArrayRequest, Void, StatusArrayResponse> {

    private final StatusArrayPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void statusesRetrieved(StatusArrayResponse statusesResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followees.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetStatusesTask(StatusArrayPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve followees. This method is
     * invoked indirectly by calling {@link #execute(FollowingRequest...)}.
     *
     * @param statusesRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected StatusArrayResponse doInBackground(StatusArrayRequest... statusesRequests) {

        StatusArrayResponse response = null;

        try {
            response = presenter.getStatusArray(statusesRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param statusesResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(StatusArrayResponse statusesResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.statusesRetrieved(statusesResponse);
        }
    }
}
