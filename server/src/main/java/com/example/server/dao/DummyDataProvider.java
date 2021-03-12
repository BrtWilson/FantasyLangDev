package com.example.server.dao;

import com.example.server.service.NewStatusNotifier_Subject;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.IStatuses_Observer;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.FollowingResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.LogoutResponse;
import com.example.shared.model.service.response.NewStatusResponse;
import com.example.shared.model.service.response.RegisterResponse;
import com.example.shared.model.service.response.StatusArrayResponse;
import com.example.shared.model.service.response.UserResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class DummyDataProvider extends NewStatusNotifier_Subject {

    // This is the hard coded following data returned by the 'getFollowees()' method
    private static final String MALE_IMAGE_URL = "https://i.pinimg.com/originals/e5/9b/e7/e59be7316543f2b7c94bcf693c2ad9f3.png";
    private static final String FEMALE_IMAGE_URL = "https://i.pinimg.com/originals/5f/79/d6/5f79d6d933f194dbcb74ec5e5ce7a759.jpg";

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
    private final Status status2 = new Status("@Luke, I am your father.", "8:01pm", user2);
    private final Status status3 = new Status("No, I am your father", "8:02pm", user3);
    private final Status status4 = new Status("Oh, I am my daddy.", "8:03pm", user4);
    private final Status status5 = new Status("Wait, what?", "8:04pm", user5);
    private final Status status6 = new Status("No tomatoes \n https://youtu.be/yRw1onpgFJA", "8:05pm", user6);
    private final Status status7 = new Status("It's over @Anakin.", "8:06pm", user7);
    private final Status status8 = new Status("I have the high ground.", "8:07pm", user8);
    private final Status status9 = new Status("You underestimate my power.", "8:08pm", user9);
    private final Status status10 = new Status("Don't try it.", "8:09pm", user10);
    private final Status status11 = new Status("You were my brother, @Anakin!", "8:10pm", user11);
    private final Status status12 = new Status("You were supposed to bring balance to the Force!", "8:20pm", user12);
    private final Status status13 = new Status("You were to destroy the Sith, not join them!", "8:30pm", user13);
    private final Status status14 = new Status("I loved you like a brother.", "8:40pm", user14);
    private final Status status15 = new Status("Barbeque accident. \n https://youtu.be/_MHusGl9BeM", "8:50pm", user15);
    private final Status status16 = new Status("Hello there.", "9:00pm", user16);
    private final Status status17 = new Status("General Kenobi.", "7:00pm", user17);
    private final Status status18 = new Status("Come closer, my little friend.", "6:00pm", user18);
    private final Status status19 = new Status("These are not the droids you are looking for.", "5:00pm", user19);
    private final Status status20 = new Status("He's not worth anything to me dead.", "4:00pm", user20);

    private final Status status1b = new Status("Avatar state, yip, yip!.", "8:00pm", user11);
    private final Status status2b = new Status("That's rough, buddy.", "8:01pm", user11);
    private final Status status3b = new Status("Maybe it's friendly!", "8:02pm", user11);
    private final Status status4b = new Status("It's the quenchiest!", "8:03pm", user11);
    private final Status status5b = new Status("The Boulder feels conflicted.", "8:04pm", user11);
    private final Status status6b = new Status("My cabbages! \n https://youtu.be/dQw4w9WgXcQ", "8:05pm", user11);
    private final Status status7b = new Status("No, I am Melon Lord.", "8:06pm", user11);
    private final Status status8b = new Status("@SparkySparkyBoomMan", "8:07pm", user11);
    private final Status status9b = new Status("https://youtu.be/vYud9sZ91Mc", "8:08pm", user11);
    private final Status status10b = new Status("But I don't want to cure cancer. I want to turn people into dinosaurs. \n https://youtu.be/HfoVqap3ar4", "8:09pm", user11);
    private final Status status11b = new Status("Tactical buttcheeks", "8:10pm", user11);
    private final Status status12b = new Status("Pocket sand!", "8:20pm", user11);
    private final Status status13b = new Status("In self defense \n A knife protects \n I bring my lunch \n No one suspects", "8:30pm", user11);
    private final Status status14b = new Status("And when it's time \n To end a life \n Deceptive fruit \n Banana knife.", "8:40pm", user11);
    private final Status status15b = new Status("I would like to see the baby.", "8:50pm", user11);


    // This is the hard coded followers data returned by the 'getFollowers()' method
    private static final String MALE_IMAGE_URL_1 = "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142";
    private static final String FEMALE_IMAGE_URL_1 = "https://c0.klipartz.com/pngpicture/250/535/sticker-png-katara-avatar-the-last-airbender-aang-korra-zuko-aang-child-face-black-hair-hand-head.png";

    private final User user21 = new User("Bob", "Jones", MALE_IMAGE_URL_1);
    // user21.addFollower(user1);
    //user21.addFollowing(user2);
    private final User user22 = new User("Bobette", "Smith", FEMALE_IMAGE_URL_1);
    private final User user23 = new User("Bobson", "Jones", MALE_IMAGE_URL_1);
    private final User user24 = new User("Bobby", "Smith", FEMALE_IMAGE_URL_1);
    private final User user25 = new User("Bobbb", "Jones", MALE_IMAGE_URL_1);
    private final User user26 = new User("Bobbee", "Smith", FEMALE_IMAGE_URL_1);
    private final User user27 = new User("Bob the 4th", "Jones", MALE_IMAGE_URL_1);
    private final User user28 = new User("Karen", "Smith", FEMALE_IMAGE_URL_1);
    private final User user29 = new User("Not Bob", "Jones", MALE_IMAGE_URL_1);
    private final User user30 = new User("KK", "Smith", FEMALE_IMAGE_URL_1);
    private final User user31 = new User("2nd Bob", "Jones", MALE_IMAGE_URL_1);
    private final User user32 = new User("Karen 2.0", "Smith", FEMALE_IMAGE_URL_1);
    private final User user33 = new User("Kinda Bob", "Jones", MALE_IMAGE_URL_1);
    private final User user34 = new User("Caren", "Smith", FEMALE_IMAGE_URL_1);
    private final User user35 = new User("Political Bob", "Jones", MALE_IMAGE_URL_1);
    private final User user36 = new User("Opinionated Karen", "Smith", FEMALE_IMAGE_URL_1);
    private final User user37 = new User("Bob Bob", "Jones", MALE_IMAGE_URL_1);
    private final User user38 = new User("Kairren", "Smith", FEMALE_IMAGE_URL_1);
    private final User user39 = new User("The Bobbiest", "Jones", MALE_IMAGE_URL_1);
    private final User user40 = new User("Karrin", "Smith", FEMALE_IMAGE_URL_1);

    private HashMap<String, User> usersMap;
    private User loggedInUser;

    public void setUpUsers() {
        if (usersMap == null) {
            usersMap = new HashMap<>();
        }
        User testUser1 = new User("test", "user", "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142");
        testUser1.setPassword("password");

        User testUser2 = new User("test", "user2", "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142");
        testUser2.setPassword("password");

        testUser1.addFollowing(user1);
        user1.addFollower(testUser1);
        testUser1.addFollowing(user2);
        user2.addFollower(testUser1);
        testUser1.addFollowing(user3);
        user3.addFollower(testUser1);
        testUser1.addFollowing(user4);
        user4.addFollower(testUser1);
        testUser1.addFollowing(user5);
        user5.addFollower(testUser1);
        testUser1.addFollowing(user6);
        user6.addFollower(testUser1);
        testUser1.addFollowing(user7);
        user7.addFollower(testUser1);
        testUser1.addFollowing(user8);
        user8.addFollower(testUser1);
        testUser1.addFollowing(user9);

        testUser2.addFollowing(user1);
        user1.addFollower(testUser2);
        testUser2.addFollowing(user2);
        user2.addFollower(testUser2);
        testUser2.addFollowing(user3);
        user3.addFollower(testUser2);
        testUser2.addFollowing(user4);
        user4.addFollower(testUser2);
        testUser2.addFollowing(user5);
        user5.addFollower(testUser2);
        testUser2.addFollowing(user6);
        user6.addFollower(testUser2);
        testUser2.addFollowing(user7);
        user7.addFollower(testUser2);
        testUser2.addFollowing(user8);
        user8.addFollower(testUser2);
        testUser2.addFollowing(user9);
        user9.addFollower(testUser2);
        testUser2.addFollowing(user20);
        user20.addFollower(testUser2);
        testUser2.addFollowing(user21);
        user21.addFollower(testUser2);
        testUser2.addFollowing(user22);
        user22.addFollower(testUser2);
        testUser2.addFollowing(user23);
        user23.addFollower(testUser2);
        testUser2.addFollowing(user24);
        user24.addFollower(testUser2);
        testUser2.addFollowing(user25);
        user25.addFollower(testUser2);
        testUser2.addFollowing(user26);
        user26.addFollower(testUser2);
        testUser2.addFollowing(user27);
        user27.addFollower(testUser2);
        testUser2.addFollowing(user28);
        user28.addFollower(testUser2);

        testUser1.addFollower(user21);
        user21.addFollowing(testUser1);
        testUser1.addFollower(user22);
        user22.addFollowing(testUser1);
        testUser1.addFollower(user23);
        user23.addFollowing(testUser1);
        testUser1.addFollower(user24);
        user24.addFollowing(testUser1);
        testUser1.addFollower(user25);
        user25.addFollowing(testUser1);
        testUser1.addFollower(user10);
        user10.addFollowing(testUser1);
        testUser1.addFollower(user11);
        user11.addFollowing(testUser1);
        testUser1.addFollower(user12);
        user12.addFollowing(testUser1);
        testUser1.addFollower(user13);
        user13.addFollowing(testUser1);

        testUser2.addFollower(user26);
        user26.addFollowing(testUser2);
        testUser2.addFollower(user2);
        user2.addFollowing(testUser2);
        testUser2.addFollower(user21);
        user21.addFollowing(testUser2);
        testUser2.addFollower(user22);
        user22.addFollowing(testUser2);

        usersMap.put(testUser1.getAlias(), testUser1);
        usersMap.put(testUser2.getAlias(), testUser2);
    }

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) {
        setUpUsers();

        if (usersMap != null) {
            if (usersMap.containsKey(request.getUsername())) {
                User user = usersMap.get(request.getUsername());
                if (user.getPassword().equals(request.getPassword())) {
                    loggedInUser = user;
                    return new LoginResponse(loggedInUser, new AuthToken());
                }
                return new LoginResponse("Password does not match.");
            }
            return new LoginResponse("Username does not exist.");
        }
        return new LoginResponse("User does not exist.");
    }

    public RegisterResponse register(RegisterRequest request) {
        setUpUsers();

        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String alias = request.getUserName();
        String password = request.getPassword();
        String url = request.getImageURL();
        User user = new User(firstName, lastName, alias, url);

        if (!usersMap.containsKey(user.getAlias())) {
            user.setPassword(password);
            usersMap.put(user.getAlias(), user);
            loggedInUser = user;
            return new RegisterResponse(user, new AuthToken(), true);
        }
        return new RegisterResponse("Username already taken. User different username.", false);
    }

    public LogoutResponse logout(LogoutRequest request) {
        if (usersMap != null) {
            if (request.getUser().getAlias().equals(loggedInUser.getAlias())) {
                loggedInUser = null;
                return new LogoutResponse(true, "Logout successful.");
            } else {
                return new LogoutResponse(false, "Logout failed. Logged in user does not match.");
            }
        }

        return new LogoutResponse(false, "Logout failed. No user logged in.");
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
        //if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getFollowingAlias() == null) {
                throw new AssertionError();
            }
        //}

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int followeesIndex = getFolloweesStartingIndex(request.getLastFolloweeAlias(), allFollowees);

            for (int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
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
     * @param allFollowees      the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(String lastFolloweeAlias, List<User> allFollowees) {

        int followeesIndex = 0;

        if (lastFolloweeAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if (lastFolloweeAlias.equals(allFollowees.get(i).getAlias())) {
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

    /* NEW CODE */

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowerResponse getFollowers(FollowerRequest request) {

        // Used in place of assert statements because Android does not support them
        //if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getUserAlias() == null) {
                throw new AssertionError();
            }
        //}

        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int followersIndex = getFollowersStartingIndex(request.getLastFollowerAlias(), allFollowers);

            for (int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followersIndex));
            }

            hasMorePages = followersIndex < allFollowers.size();
        }

        return new FollowerResponse(responseFollowers, hasMorePages);
    }

    /**
     * Determines the index for the first follower in the specified 'allFollowers' list that should
     * be returned in the current request. This will be the index of the next follower after the
     * specified 'lastFollower'.
     *
     * @param lastFollowerAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollowers      the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFollowersStartingIndex(String lastFollowerAlias, List<User> allFollowers) {

        int followersIndex = 0;

        if (lastFollowerAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if (lastFollowerAlias.equals(allFollowers.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                    break;
                }
            }
        }

        return followersIndex;
    }

    List<User> getDummyFollowers() {
        return Arrays.asList(user21, user22, user23, user24, user25, user26, user27,
                user28, user29, user30, user31, user32, user33, user34, user35, user36, user37, user38,
                user39, user40);
    }


    public NewStatusResponse pushNewStatus(NewStatusRequest request) {
        //Pretend to save Status
        //Updates all status observers; we will need to figure observers properly to do this
        updateObservers();
        //if successful:
        return new NewStatusResponse(new Status(request.getMessage(), request.getDate(), user8));
    }

    public StatusArrayResponse getStatusArray(StatusArrayRequest request, IStatuses_Observer statuses_observer) {

        // Used in place of assert statements because Android does not support them
        //if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getUserAlias() == null) {
                throw new AssertionError();
            }
        //}

        register(statuses_observer);

        List<Status> allStatuses = getDummyStatuses();
        if (request.getFeedInstead()) {
            allStatuses = getDummyFeed();
        }

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

    List<Status> getDummyStatuses() {
        return Arrays.asList(status1, status2, status3, status4, status5, status6, status7, status8,
                status9, status10, status11, status12, status13, status14, status15, status16, status17,
                status18, status19, status20);
    }

    List<Status> getDummyFeed() {
        return Arrays.asList(status1b, status2b, status3b, status4b, status5b, status6b, status7b, status8b,
                status9b, status10b, status11b, status12b, status13b, status14b, status15b);
    }


    //OBSERVER SETUP
    //STATUSES OBSERVERS
    List<IStatuses_Observer> statusObservers = new LinkedList<>();

    public void register(IStatuses_Observer observer) {
        statusObservers.add(observer);
    }

    public void updateObservers() {
        for (IStatuses_Observer o : statusObservers) {
            o.Update();
        }
    }

    //GetUser for Status
    public UserResponse getUserByAlias(UserRequest userRequest) {
        return new UserResponse(user8);
    }

    static DummyDataProvider getInstance() {
        return new DummyDataProvider();
    }
}
