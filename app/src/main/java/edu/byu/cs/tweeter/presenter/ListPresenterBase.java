package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IListService;
import edu.byu.cs.tweeter.model.service.request.IListRequest;
import edu.byu.cs.tweeter.model.service.response.IListResponse;

/**
 * The presenter for the "following" functionality of the application.
 */
abstract class ListPresenterBase {

    private final View view;

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
    public ListPresenterBase(View view) {
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
    public IListResponse getDataList(IListRequest request) throws IOException {
        IListService listService = getListService();
        return listService.getList(request);
    }

    /**
     * Returns an instance of { ListService}. Allows mocking of the FollowingService class
     * for testing purposes. All usages of FollowingService should get their FollowingService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    abstract IListService getListService();
}
