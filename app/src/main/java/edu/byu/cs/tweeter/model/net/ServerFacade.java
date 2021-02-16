package edu.byu.cs.tweeter.model.net;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.domain.status_members.Status;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.NewStatusRequest;
import edu.byu.cs.tweeter.model.service.request.StatusArrayRequest;
import edu.byu.cs.tweeter.model.service.response.*;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
    // This is the hard coded followee data returned by the 'getFollowees()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);

    private final Status status1 = new Status("I have a sister.", "8:00pm", user1);
    private final Status status2 = new Status("Luke, I am your father.", "8:01pm", user2);
    private final Status status3 = new Status("No, I am your father", "8:02pm", user3);
    private final Status status4 = new Status("Oh, I am my daddy.", "8:03pm", user4);
    private final Status status5 = new Status("Wait, what?", "8:04pm", user5);
    private final Status status6 = new Status("No tomatoes", "8:05pm", user6);
    private final Status status7 = new Status("It's over Anakin.", "8:06pm", user7);
    private final Status status8 = new Status("I have the high ground.", "8:07pm", user8);
    private final Status status9 = new Status("You underestimate my power.", "8:08pm", user9);
    private final Status status10 = new Status("Don't try it.", "8:09pm", user10);
    private final Status status11 = new Status("You were my brother, Anakin!", "8:10pm", user11);
    private final Status status12 = new Status("You were supposed to bring balance to the Force!", "8:20pm", user12);
    private final Status status13 = new Status("You were to destroy the Sith, not join them!", "8:30pm", user13);
    private final Status status14 = new Status("I loved you like a brother.", "8:40pm", user14);
    private final Status status15 = new Status("Barbeque accident.", "8:50pm", user15);
    private final Status status16 = new Status("Hello there.", "9:00pm", user16);
    private final Status status17 = new Status("General Kenobi.", "7:00pm", user17);
    private final Status status18 = new Status("Come closer, my little friend.", "6:00pm", user18);
    private final Status status19 = new Status("These are not the droids you are looking for.", "5:00pm", user19);
    private final Status status20 = new Status("He's not worth anything to me dead.", "4:00pm", user20);

    private final Status status1b = new Status("I have a sister.", "8:00pm", user11);
    private final Status status2b = new Status("Luke, I am your father.", "8:01pm", user11);
    private final Status status3b = new Status("No, I am your father", "8:02pm", user11);
    private final Status status4b = new Status("Oh, I am my daddy.", "8:03pm", user11);
    private final Status status5b = new Status("Wait, what?", "8:04pm", user11);
    private final Status status6b = new Status("No tomatoes", "8:05pm", user11);
    private final Status status7b = new Status("It's over Anakin.", "8:06pm", user11);
    private final Status status8b = new Status("I have the high ground.", "8:07pm", user11);
    private final Status status9b = new Status("You underestimate my power.", "8:08pm", user11);
    private final Status status10b = new Status("Don't try it.", "8:09pm", user11);
    private final Status status11b = new Status("You were my brother, Anakin!", "8:10pm", user11);
    private final Status status12b = new Status("You were supposed to bring balance to the Force!", "8:20pm", user11);
    private final Status status13b = new Status("You were to destroy the Sith, not join them!", "8:30pm", user11);
    private final Status status14b = new Status("I loved you like a brother.", "8:40pm", user11);
    private final Status status15b = new Status("Barbeque accident.", "8:50pm", user11);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new LoginResponse(user, new AuthToken());
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowerAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followeesIndex = getFolloweesStartingIndex(request.getLastFolloweeAlias(), allFollowees);

            for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowees.add(allFollowees.get(followeesIndex));
            }

            hasMorePages = followeesIndex < allFollowees.size();
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }


    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(String lastFolloweeAlias, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFolloweeAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFolloweeAlias.equals(allFollowees.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    public BasicResponse pushNewStatus(NewStatusRequest request) {
        //Pretend to save Status

        return new BasicResponse(true);
    }

    public StatusArrayResponse getStatusArray(StatusArrayRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUserAlias() == null) {
                throw new AssertionError();
            }
        }

        List<Status> allStatuses = getDummyStatuses();
        if (request.getFeedInstead()) {
            allStatuses = getDummyFeed();
        }

        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int statusesIndex = getStatusesStartingIndex(request.getLastStatusDate(), allStatuses);

            for(int limitCounter = 0; statusesIndex < allStatuses.size() && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusesIndex));
            }

            hasMorePages = statusesIndex < allStatuses.size();
        }

        return new StatusArrayResponse(responseStatuses, hasMorePages);
    }

    private int getStatusesStartingIndex(String lastStatusAlias, List<Status> allStatuses) {

        int statusesIndex = 0;

        if(lastStatusAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatusAlias.equals(allStatuses.get(i).getDate())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusesIndex = i + 1;
                    break;
                }
            }
        }

        return statusesIndex;
    }

    List<Status> getDummyStatuses() {
        return Arrays.asList(status1, status2, status3, status4, status5, status6, status7, status8,
                status9, status10, status11, status12, status13, status14, status15, status16, status17,
                status18, status19, status20);
    }

    List<Status> getDummyFeed() {
        return Arrays.asList(status1b, status2b, status3b, status4b, status5b, status6b, status7b, status8b,
                status9b, status10b, status11b, status12b, status13b, status14b, status15b);
    }
}
