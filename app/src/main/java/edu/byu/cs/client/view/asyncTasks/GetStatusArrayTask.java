package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;
import java.io.IOException;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.StatusArrayResponse;
import edu.byu.cs.client.presenter.StatusArrayPresenter;

/**
 * An {@link AsyncTask} for retrieving followers for a user.
 */
public class GetStatusArrayTask extends AsyncTask<StatusArrayRequest, Void, StatusArrayResponse> {

    private final StatusArrayPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void statusArrayRetrieved(StatusArrayResponse statusArrayResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followers.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetStatusArrayTask(StatusArrayPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve followers. This method is
     * invoked indirectly by calling {@link #execute(StatusArrayRequest...)}.
     *
     * @param statusRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected StatusArrayResponse doInBackground(StatusArrayRequest... statusRequests) {

        StatusArrayResponse response = null;

        try {
            response = presenter.getStatusArray(statusRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param statusArrayResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(StatusArrayResponse statusArrayResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.statusArrayRetrieved(statusArrayResponse);
        }
    }
}