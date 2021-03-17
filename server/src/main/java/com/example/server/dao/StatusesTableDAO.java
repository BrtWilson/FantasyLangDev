package com.example.server.dao;

import com.example.shared.model.service.IStatuses_Observer;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.NewStatusResponse;
import com.example.shared.model.service.response.StatusArrayResponse;

public class StatusesTableDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public StatusArrayResponse getStatusArray(StatusArrayRequest request, IStatuses_Observer statuses_observer) {
        return dataProvider.getStatusArray(request, statuses_observer);
    }

    public NewStatusResponse pushNewStatus(NewStatusRequest request) {
        return dataProvider.pushNewStatus(request);
    }
}