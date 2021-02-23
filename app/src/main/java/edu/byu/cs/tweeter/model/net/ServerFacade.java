package edu.byu.cs.tweeter.model.net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
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

    // This is the hard coded followers data returned by the 'getFollowers()' method
    private static final String MALE_IMAGE_URL_1 = "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142";
    private static final String FEMALE_IMAGE_URL_1 = "https://c0.klipartz.com/pngpicture/250/535/sticker-png-katara-avatar-the-last-airbender-aang-korra-zuko-aang-child-face-black-hair-hand-head.png";

    private final User user21 = new User("Bob","Jones",  MALE_IMAGE_URL_1);
   // user21.addFollower(user1);
    //user21.addFollowing(user2);
    private final User user22 = new User("Bobette","Smith",  FEMALE_IMAGE_URL_1);
    private final User user23 = new User("Bobson","Jones",  MALE_IMAGE_URL_1);
    private final User user24 = new User("Bobby","Smith",  FEMALE_IMAGE_URL_1);
    private final User user25 = new User("Bobbb","Jones",  MALE_IMAGE_URL_1);
    private final User user26 = new User("Bobbee","Smith",  FEMALE_IMAGE_URL_1);
    private final User user27 = new User("Bob the 4th","Jones",  MALE_IMAGE_URL_1);
    private final User user28 = new User("Karen","Smith",  FEMALE_IMAGE_URL_1);
    private final User user29 = new User("Not Bob","Jones",  MALE_IMAGE_URL_1);
    private final User user30 = new User("KK","Smith",  FEMALE_IMAGE_URL_1);
    private final User user31 = new User("2nd Bob","Jones",  MALE_IMAGE_URL_1);
    private final User user32 = new User("Karen 2.0","Smith",  FEMALE_IMAGE_URL_1);
    private final User user33 = new User("Kinda Bob","Jones",  MALE_IMAGE_URL_1);
    private final User user34 = new User("Caren","Smith",  FEMALE_IMAGE_URL_1);
    private final User user35 = new User("Political Bob","Jones",  MALE_IMAGE_URL_1);
    private final User user36 = new User("Opinionated Karen","Smith",  FEMALE_IMAGE_URL_1);
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

        User testUser2 = new User("new", "user", "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142");
        testUser2.setPassword("password");

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
        if(usersMap != null) {
            if(usersMap.containsKey(request.getUsername())) {
                User user = usersMap.get(request.getUsername());
                if(user.getPassword().equals(request.getPassword())) {
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
        user.setPassword(password);

        usersMap.put(user.getAlias(), user);
        loggedInUser = user;
        return new RegisterResponse(user, new AuthToken(), true);
    }

    public LogoutResponse logout(LogoutRequest request) {
        if (usersMap != null) {
            if (request.getUser() == loggedInUser) {
                loggedInUser = null;
                return new LogoutResponse(true, "Logout successful.");
            }
            return new LogoutResponse(false, "Logout failed. Logged in user does not match.");
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
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowingAlias() == null) {
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
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUserAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followersIndex = getFollowersStartingIndex(request.getLastFollowerAlias(), allFollowers);

            for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
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
     * @param allFollowers the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFollowersStartingIndex(String lastFollowerAlias, List<User> allFollowers) {

        int followersIndex = 0;

        if(lastFollowerAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollowerAlias.equals(allFollowers.get(i).getAlias())) {
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
}
