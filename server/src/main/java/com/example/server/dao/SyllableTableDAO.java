package com.example.server.dao;

import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.dao.dbstrategies.ResultsPage;
import com.example.server.dao.util.ListTypeItemTransformer;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;

import java.util.List;

public class SyllableTableDAO {
    //DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    private static final String tableName = "Syllables";
    //private static final String partionKey = "FollowerAlias";
    //private static final String sortKey = "FolloweeAlias";

    private static final String SERVER_SIDE_ERROR = "[Server Error]";

    /**
     *
     * @param request provides languageID
     * @return a List of String containing the syllable data corresponding to the languageID if any exists
     */
    public List<String> getSyllableData(GetLanguageDataRequest request) {
        return null;
    }

    public GeneralUpdateResponse updateSyllables(UpdateSyllablesRequest request) {
        return null;
    }

/*
    public FollowerResponse getFollowers(FollowerRequest request) {
        pageSize = request.getLimit();
        verifyRequestLimit(request.getLimit());
        verifyRequestUserAlias(request.getUserAlias());
        System.out.println("User: " + request.getUserAlias() + " LastFollower: " +request.getLastFollowerAlias());
        return retrieveFollowers(request.getUserAlias(), request.getLastFollowerAlias());
    }

    //
    //public FollowerResponse getNumFollowers(FollowerRequest request) {
      //  return new FollowerResponse(13);
    //}

    private void verifyRequestUserAlias(String requestAlias) {//We have followers that hits this point, and just blows up
        if (requestAlias == null) {
            throw new AssertionError();
        }
    }

    private void verifyRequestLimit(int requestLimit) {
        if (requestLimit < 0) {
            pageSize = PAGE_SIZE_DEFAULT;
        }
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        pageSize = request.getLimit();
        verifyRequestLimit(request.getLimit());
        verifyRequestUserAlias(request.getFollowingAlias());

        System.out.println("User: " + request.getFollowingAlias() + " LastFollower: " +request.getLastFolloweeAlias());

        return retrieveFollowees(request.getFollowingAlias(), request.getLastFolloweeAlias());
    }

    //DEPRACATE
    //public FollowingResponse getNumFollowing(FollowingRequest request) {
      //  return new FollowingResponse(13);
    //}

    private FollowingResponse retrieveFollowees(String targetAlias, String lastRetrieved) {
        System.out.println("Followees targetAlias: " + targetAlias + " and " + lastRetrieved);

        ResultsPage resultsPage = getDatabaseInteractor().getListByString(tableName, partionKey, targetAlias, pageSize, sortKey, lastRetrieved, false, null);
        boolean hasMorePages = (resultsPage.hasLastKey());
        String newLastRetrieved = resultsPage.getLastKey();
        List<User> usersList = ListTypeItemTransformer.transformToUser(resultsPage.getValues());
        FollowingResponse response = new FollowingResponse(usersList, hasMorePages, newLastRetrieved);
        return response;
    }

    private FollowerResponse retrieveFollowers(String targetAlias, String lastRetrieved) {
        //TODO: verify whether this works, or if sortKey and partitionKey should stay normal
        System.out.println("Followers targetAlias: " + targetAlias + " and " + lastRetrieved);

        ResultsPage resultsPage = getDatabaseInteractor().getListByString(tableName, sortKey, targetAlias, pageSize, partionKey, lastRetrieved, true, "FolloweeAlias-FollowerAlias-index");
        boolean hasMorePages = (resultsPage.hasLastKey());
        String newLastRetrieved = resultsPage.getLastKey();
        List<User> usersList = ListTypeItemTransformer.transformToUser(resultsPage.getValues());
        FollowerResponse response = new FollowerResponse(usersList, hasMorePages, newLastRetrieved);
        return response;
    }

    public FollowStatusResponse unfollow(FollowStatusRequest request) {
        try {
            getDatabaseInteractor().deleteItemWithDualKey(tableName, partionKey, request.getCurrentUser(), sortKey, request.getOtherUser());

            UsersTableDAO usersTableDAO = new UsersTableDAO();
            usersTableDAO.unfollow(request);
            return new FollowStatusResponse(false);
        } catch (Exception e) {
            return new FollowStatusResponse(SERVER_SIDE_ERROR + ": " + e.getMessage());
        }
    }

    public FollowStatusResponse follow(FollowStatusRequest request) {
        try {
            getDatabaseInteractor().createItemWithDualKey(tableName, partionKey, request.getCurrentUser(), sortKey, request.getOtherUser());

            UsersTableDAO usersTableDAO = new UsersTableDAO();
            usersTableDAO.follow(request);
            return new FollowStatusResponse(true);
        } catch (Exception e) {
            return new FollowStatusResponse(SERVER_SIDE_ERROR + ": " + e.getMessage()); //folllw lambda is hitting this
        }
    }

    public FollowStatusResponse getFollowStatus(FollowStatusRequest request) {
        try {
            Object followTableMatch = getDatabaseInteractor().basicGetItemWithDualKey(tableName, partionKey, request.getCurrentUser(), sortKey, request.getOtherUser());
            return new FollowStatusResponse((followTableMatch != null));
        } catch (Exception e) {
            return new FollowStatusResponse(SERVER_SIDE_ERROR + ": " + e.getMessage());
        }
    }




    /** DEPRACATED: AS DYNAMODB WILL HANDLE THIS, I BELIEVE
     * Determines the index for the first follower in the specified 'allFollowers' list that should
     * be returned in the current request. This will be the index of the next follower after the
     * specified 'lastFollower'.
     *
     * @/param lastFollowAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @/param allFollows      the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
   /* private int getFollowsStartingIndex(String lastFollowAlias, List<User> allFollows) {

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
    }*/
    public DynamoDBStrategy getDatabaseInteractor() {
        return new DynamoDBStrategy();
    }
}
