package edu.byu.cs.client.presenter.oldfiles;

import com.example.shared.model.net.RemoteException;

import java.io.IOException;

import edu.byu.cs.client.model.service.oldfiles.FollowStatusService;

public class FollowStatusPresenter {

    private final FollowStatusPresenter.View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public FollowStatusPresenter(FollowStatusPresenter.View view) {
        this.view = view;
    }

    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) throws IOException, RemoteException {
        FollowStatusService followStatusService = getFollowStatusService();
        return followStatusService.getFollowStatus(request);
    }

    FollowStatusService getFollowStatusService() { return new FollowStatusService(); }
}

