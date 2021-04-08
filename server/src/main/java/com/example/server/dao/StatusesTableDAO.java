package com.example.server.dao;

import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.NewStatusResponse;
import com.example.shared.model.service.response.StatusArrayResponse;

import java.util.ArrayList;
import java.util.List;

public class StatusesTableDAO {
    //DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public StatusArrayResponse getStatusArray(StatusArrayRequest request) {
        //return dataProvider.getStatusArray(request);
        // Used in place of assert statements because Android does not support them
        //if (BuildConfig.DEBUG) {
        verifyLimit(request.getLimit());
        verifyAlias(request.getUserAlias());
        //}

        List<Status> allStatuses = retrieveStatuses(request.getFeedInstead());
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int statusesIndex = getStatusesStartingIndex(request.getLastStatusDate(), allStatuses);

            for (int limitCounter = 0; statusesIndex < allStatuses.size() && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusesIndex));
            }

            hasMorePages = statusesIndex < allStatuses.size();
        }

        return null; //new StatusArrayResponse(responseStatuses, hasMorePages);
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

    private List<Status> retrieveStatuses(boolean feedInstead) {
        if (feedInstead) {
            return retrieveFeed();
        } else {
            return retrieveStory();
        }
    }

    private List<Status> retrieveFeed() {
        return null;//dataProvider.getDummyFeed();
    }

    private List<Status> retrieveStory() {
        return null;//dataProvider.getDummyStatuses();
    }

    public NewStatusResponse postNewStatus(NewStatusRequest request) {
        return new NewStatusResponse(new Status(request.getMessage(), request.getDate(), getAUser()));
        //return dataProvider.pushNewStatus(request);
    }

    private User getAUser() {
        return null;//dataProvider.getSampleDummyUser();
    }
}