package com.example.server.dao;

import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.NewStatusResponse;
import com.example.shared.model.service.response.StatusArrayResponse;

import java.util.ArrayList;
import java.util.List;

public class StoryTableDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    private static final String tableName = "Stories";

    public StatusArrayResponse getStatusArray(StatusArrayRequest request) {
        verifyLimit(request.getLimit());
        verifyAlias(request.getUserAlias());

        List<Status> allStatuses = dataProvider.getDummyStatuses();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int statusesIndex = getStatusesStartingIndex(request.getLastStatusDate(), allStatuses);

            for (int limitCounter = 0; statusesIndex < allStatuses.size() && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusesIndex));
            }

            hasMorePages = statusesIndex < allStatuses.size();
        }

        return new StatusArrayResponse(responseStatuses, hasMorePages);
    }

    private void verifyLimit(int limit) {
        if (limit < 0) {
            throw new AssertionError();
        }
    }

    private void verifyAlias(String userAlias) {
        if (userAlias == null) {
            throw new AssertionError();
        }
    }

    private int getStatusesStartingIndex(String lastStatusAlias, List<Status> allStatuses) {

        int statusesIndex = 0;

        if (lastStatusAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if (lastStatusAlias.equals(allStatuses.get(i).getDate())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusesIndex = i + 1;
                    break;
                }
            }
        }

        return statusesIndex;
    }

    //need this????
    public NewStatusResponse postNewStatus(NewStatusRequest request) {
        return new NewStatusResponse(new Status(request.getMessage(), request.getDate(), getAUser()));
        //return dataProvider.pushNewStatus(request);
    }

    private User getAUser() {
        return dataProvider.getSampleDummyUser();
    }
}
