package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.service.request.IListRequest;
import edu.byu.cs.tweeter.model.service.response.IListResponse;
import edu.byu.cs.tweeter.presenter.IStatuses_Observer;
import edu.byu.cs.tweeter.presenter.ListPresenterBase;

public interface IStatusListService {
    IListResponse getList(IListRequest listRequest, IStatuses_Observer statuses_observer);
}
