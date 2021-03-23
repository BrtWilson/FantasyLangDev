package com.example.server.dao;

import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.FollowingResponse;

import java.util.ArrayList;
import java.util.List;

public class FollowsTableDAO {
    DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    public FollowerResponse getFollowers(FollowerRequest request) {
        //return dataProvider.getFollowers(request);

        // Used in place of assert statements because Android does not support them
        verifyRequestLimit(request.getLimit());
        verifyRequestUserAlias(request.getUserAlias());

        List<User> allFollowers = retrieveFollowers();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int followersIndex = getFollowsStartingIndex(request.getLastFollowerAlias(), allFollowers);

            for (int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followersIndex));
            }

            hasMorePages = followersIndex < allFollowers.size();
        }

        return new FollowerResponse(responseFollowers, hasMorePages);
    }

    public FollowerResponse getNumFollowers(FollowerRequest request) {
        return new FollowerResponse(13);
    }

    private void verifyRequestUserAlias(String requestAlias) {
        if (requestAlias == null) {
            throw new AssertionError();
        }
    }

    private void verifyRequestLimit(int requestLimit) {
        if (requestLimit < 0) {
            throw new AssertionError();
        }
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        //return dataProvider.getFollowees(request);

        // Used in place of assert statements because Android does not support them
        //if (BuildConfig.DEBUG) {
        verifyRequestLimit(request.getLimit());
        verifyRequestUserAlias(request.getFollowingAlias());

        List<User> allFollowees = retrieveFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int followeesIndex = getFollowsStartingIndex(request.getLastFolloweeAlias(), allFollowees);

            for (int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowees.add(allFollowees.get(followeesIndex));
            }

            hasMorePages = followeesIndex < allFollowees.size();
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }


    public FollowingResponse getNumFollowing(FollowingRequest request) {
        return new FollowingResponse(13);
    }

    /**
     * Determines the index for the first follower in the specified 'allFollowers' list that should
     * be returned in the current request. This will be the index of the next follower after the
     * specified 'lastFollower'.
     *
     * @param lastFollowAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollows      the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFollowsStartingIndex(String lastFollowAlias, List<User> allFollows) {

        int followsIndex = 0;

        if (lastFollowAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollows.size(); i++) {
                if (lastFollowAlias.equals(allFollows.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followsIndex = i + 1;
                    break;
                }
            }
        }

        return followsIndex;
    }

    private List<User> retrieveFollowees() {
        return dataProvider.getDummyFollowees();
    }

    private List<User> retrieveFollowers() {
        return dataProvider.getDummyFollowers();
    }
}
