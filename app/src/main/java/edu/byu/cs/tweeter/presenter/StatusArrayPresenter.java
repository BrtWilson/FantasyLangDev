package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IStatusListService;
import edu.byu.cs.tweeter.model.service.StatusArrayServiceStatus;
import edu.byu.cs.tweeter.model.service.request.IListRequest;
import edu.byu.cs.tweeter.model.service.request.StatusArrayRequest;
import edu.byu.cs.tweeter.model.service.response.IListResponse;
import edu.byu.cs.tweeter.model.service.response.StatusArrayResponse;

/**
 * The presenter for the "following" functionality of the application.
 */
public class StatusArrayPresenter implements IStatuses_Observer{
    protected final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
        void update();
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public IListResponse getDataList(IListRequest request, IStatuses_Observer statuses_observer) throws IOException {
        IStatusListService listService = getListService();
        return listService.getList(request, statuses_observer);
    }
    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public StatusArrayPresenter(View view) {
        this.view = view;
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public StatusArrayResponse getStatusArray(StatusArrayRequest request) throws IOException {
        StatusArrayServiceStatus statusArrayService = getStatusArrayService();
        return statusArrayService.requestStatusArray(request, this);
    }

    /**
     * Returns an instance of {@link StatusArrayServiceStatus}. Allows mocking of the FollowingService class
     * for testing purposes. All usages of FollowingService should get their FollowingService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    StatusArrayServiceStatus getStatusArrayService() {
        return new StatusArrayServiceStatus();
    }

    IStatusListService getListService() {
        return (IStatusListService) getStatusArrayService();
    }

    @Override
    public void Update() {
        view.update();
    }
}
