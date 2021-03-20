package com.example.server.dao;

import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.NewStatusResponse;
import com.example.shared.model.service.response.StatusArrayResponse;

public class StatusesTableDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public StatusArrayResponse getStatusArray(StatusArrayRequest request) {
        return dataProvider.getStatusArray(request);
    }

    public NewStatusResponse pushNewStatus(NewStatusRequest request) {
        return dataProvider.pushNewStatus(request);
    }
}