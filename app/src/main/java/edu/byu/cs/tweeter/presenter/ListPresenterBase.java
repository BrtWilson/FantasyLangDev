package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.IStatusListService;
import edu.byu.cs.tweeter.model.service.request.IListRequest;
import edu.byu.cs.tweeter.model.service.response.IListResponse;

/**
 * The presenter for the "following" functionality of the application.
 */
public abstract class ListPresenterBase {



    /**
     * Returns an instance of { ListService}. Allows mocking of the FollowingService class
     * for testing purposes. All usages of FollowingService should get their FollowingService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    abstract IStatusListService getListService();
}
