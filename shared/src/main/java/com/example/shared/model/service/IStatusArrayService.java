package com.example.shared.model.service;

import com.example.shared.model.service.request.IListRequest;
import com.example.shared.model.service.response.IListResponse;

import edu.byu.cs.client.presenter.IStatuses_Observer;

public interface IStatusArrayService {
    IListResponse getList(IListRequest listRequest, IStatuses_Observer statuses_observer);
}
