package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import java.io.IOException;
import edu.byu.cs.tweeter.model.service.request.StatusArrayRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.StatusArrayResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
import edu.byu.cs.tweeter.presenter.StatusArrayPresenter;

/**
 * An {@link AsyncTask} for retrieving followers for a user.
 */
public class GetUserTask extends AsyncTask<UserRequest, Void, UserResponse> {

    private final StatusArrayPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void userRetrieved(UserResponse userResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followers.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetUserTask(StatusArrayPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve followers. This method is
     * invoked indirectly by calling {@link #execute(UserRequest...)}.
     *
     * @param userRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected UserResponse doInBackground(UserRequest... userRequests) {

        UserResponse response = null;

        try {
            response = presenter.getUserByAlias(userRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param userResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(UserResponse userResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.userRetrieved(userResponse);
        }
    }
}