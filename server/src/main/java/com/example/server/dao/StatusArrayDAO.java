package com.example.server.dao;

import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.StatusArrayResponse;

public class StatusArrayDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public StatusArrayResponse getStatusArray(StatusArrayRequest request, IStatuses_Observer statuses_observer) {
        return dataProvider.getStatusArray(request, statuses_observer);
    }
}