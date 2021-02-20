package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.service.request.IListRequest;
import edu.byu.cs.tweeter.model.service.response.IListResponse;

public interface IListService {
    public IListResponse getList(IListRequest listRequest);
}
