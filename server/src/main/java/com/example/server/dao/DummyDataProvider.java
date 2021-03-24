package com.example.server.dao;

import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.FollowStatusResponse;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.FollowingResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.BasicResponse;
import com.example.shared.model.service.response.NewStatusResponse;
import com.example.shared.model.service.response.RegisterResponse;
import com.example.shared.model.service.response.StatusArrayResponse;
import com.example.shared.model.service.response.UserResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class DummyDataProvider {

    // This is the hard coded following data returned by the 'getFollowees()' method
    private static final String MALE_IMAGE_URL = "https://i.pinimg.com/originals/e5/9b/e7/e59be7316543f2b7c94bcf693c2ad9f3.png";
    private static final String FEMALE_IMAGE_URL = "https://i.pinimg.com/originals/5f/79/d6/5f79d6d933f194dbcb74ec5e5ce7a759.jpg";

    private final User user1 = new User("Ash", "Ahketchum", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngitem.com%2Fmiddle%2FwomThJ_ash-ketchum-hd-png-download%2F&psig=AOvVaw2h43_Bi3x5gdd1y2tRmAhq&ust=1616605412770000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJjVytTyxu8CFQAAAAAdAAAAABAL");
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bross", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.bobross.com%2F&psig=AOvVaw0aQWlEayotht6kNKp2WOPT&ust=1616605355172000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKjex7Pyxu8CFQAAAAAdAAAAABAD");
    private final User user4 = new User("Bonnie", "Betty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Captain", "Chris", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fofficialpsds.com%2Fcaptain-america-psd-rn8w69&psig=AOvVaw0gijtq-0wEiaR3JqGpoxjg&ust=1616605627876000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCMj25LXzxu8CFQAAAAAdAAAAABAd");
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Dumbledoor", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Giovanni", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Hagrid", "Henderson", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.kindpng.com%2Fimgv%2FhmmmRTo_harrypotter-harry-potter-hagrid-hagrid-harry%2F&psig=AOvVaw3qg8vyZscEHfeDkgtvRfpx&ust=1616605470020000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPDYnOnyxu8CFQAAAAAdAAAAABAD");
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Draco", "Malphoy", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.seekpng.com%2Fipng%2Fu2t4y3i1q8q8u2a9_draco-malfoy-part-one-draco-malfoy%2F&psig=AOvVaw0TbOCVolKLAO-iRN02FPgq&ust=1616605740864000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCOig4O3zxu8CFQAAAAAdAAAAABAD");
    private final User user19 = new User("Luna", "Lovegood", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F173740498113704091%2F&psig=AOvVaw3ps5GGxj5mASJ6-Ozf1Zta&ust=1616605568363000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCOiU1J_zxu8CFQAAAAAdAAAAABAP");
    private final User user20 = new User("Harry", "Potter", "https://www.google.com/url?sa=i&url=https%3A%2F%2Ffreepngimg.com%2Fpng%2F12537-harry-potter-png-file&psig=AOvVaw1ViZtnx2zrCGQNnZd6gpbh&ust=1616605493215000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCNDQ5Ibzxu8CFQAAAAAdAAAAABAg");

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

    private final User user21 = new User("Kin", "Jonahs", MALE_IMAGE_URL_1);
    // user21.addFollower(user1);
    //user21.addFollowing(user2);
    private final User user22 = new User("Luke", "Skywalker", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngkey.com%2Fpngs%2Fluke-skywalker%2F&psig=AOvVaw2LGNOW4cr8dVngv285QVpQ&ust=1616605786114000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCIiorIH0xu8CFQAAAAAdAAAAABA-");
    private final User user23 = new User("Anakin", "Skywalker", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fclipartstation.com%2Fdictator-clipart-clear-background-3-2%2F&psig=AOvVaw2LGNOW4cr8dVngv285QVpQ&ust=1616605786114000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCIiorIH0xu8CFQAAAAAdAAAAABBY");
    private final User user24 = new User("Mace", "Windu", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngkey.com%2Fpngs%2Fstar-wars%2F14%2F&psig=AOvVaw1zXzYCXWSGkTYPUHdhE-VT&ust=1616605895382000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKCb6LP0xu8CFQAAAAAdAAAAABAY");
    private final User user25 = new User("Obi-wan", "Kenobi", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.nicepng.com%2Fourpic%2Fu2w7q8i1i1e6o0t4_u9vdtdzvcr8u-obi-wan-kenobi-png%2F&psig=AOvVaw1k0Z39Gu3L0S1q7JiisErk&ust=1616605931102000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPis6MX0xu8CFQAAAAAdAAAAABA_");
    private final User user26 = new User("Sokka", "Sacapuntes", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.ign.com%2Fwikis%2Favatar-the-last-airbender-legend-of-korra%2FSokka&psig=AOvVaw2Q3W5pZvrEmirnx8WMGf0w&ust=1616606003501000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKiLhej0xu8CFQAAAAAdAAAAABAd");
    private final User user27 = new User("Aang", "Jones", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngkey.com%2Fpngs%2Faang%2F&psig=AOvVaw0CYHLcCikNvWIonFnrzpkq&ust=1616606066149000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCID4kIj1xu8CFQAAAAAdAAAAABAg");
    private final User user28 = new User("The Fatherlord", "Smith", "https://www.google.com/url?sa=i&url=https%3A%2F%2Favatar.fandom.com%2Fwiki%2FOzai&psig=AOvVaw0zACQWsm6otBK2214HPsNA&ust=1616606211953000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJDX4M31xu8CFQAAAAAdAAAAABAe");
    private final User user29 = new User("Darth", "Vader", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fdisney.fandom.com%2Fwiki%2FDarth_Vader&psig=AOvVaw1naLm6RL-oShE0znTkhOyM&ust=1616606271530000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPCzvef1xu8CFQAAAAAdAAAAABAs");
    private final User user30 = new User("Darth", "Plagueis", "https://www.google.com/url?sa=i&url=https%3A%2F%2Ffrom-the-crazy.fandom.com%2Fwiki%2FDarth_Plagueis&psig=AOvVaw3etI1aHSeMuk_fCP5o2CGH&ust=1616606348656000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJjRrI32xu8CFQAAAAAdAAAAABAW");
    private final User user31 = new User("Chancellor", "Palpatine", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.kindpng.com%2Fimgv%2FiRJhbib_darth-sidious-png-emperor-star-wars-transparent-png%2F&psig=AOvVaw1jiw904qQs23Tu4dQR1su3&ust=1616606392871000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCIiQv6H2xu8CFQAAAAAdAAAAABAd");
    private final User user32 = new User("The", "Senate", "https://www.google.com/url?sa=i&url=https%3A%2F%2Ftwitter.com%2Fsheevpalpatine_&psig=AOvVaw1jiw904qQs23Tu4dQR1su3&ust=1616606392871000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCIiQv6H2xu8CFQAAAAAdAAAAABBL");
    private final User user33 = new User("Toph", "The Destroyer", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F388787380310074372%2F&psig=AOvVaw2fgAbhvmkeOfrDb9qhGDKl&ust=1616606488463000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCLjKw8_2xu8CFQAAAAAdAAAAABAW");
    private final User user34 = new User("Cairne", "Bloodhoof", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwow.gamepedia.com%2FCairne_Bloodhoof&psig=AOvVaw33ufZe-_J95NTojjTk9Lcz&ust=1616606519824000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCOiVxuD2xu8CFQAAAAAdAAAAABAO");
    private final User user35 = new User("Prince", "Arthas", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fes.wowhead.com%2Fnews%2Fprince-arthas-statue-now-on-sale-from-blizzard-gear-shop-317636&psig=AOvVaw13--pGeY0dxWRF9MRk6YVm&ust=1616606566095000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCID60fr2xu8CFQAAAAAdAAAAABAc");
    private final User user36 = new User("Opinionated Karen", "Smith", FEMALE_IMAGE_URL_1);
    private final User user37 = new User("Cleric", "Uther", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.guiaswowtbc.com%2Fguias-paladin%2F&psig=AOvVaw3GMx9ySs08TEtajQ6K-3fK&ust=1616606650996000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPDFjJz3xu8CFQAAAAAdAAAAABAU");
    private final User user38 = new User("Frost", "The Undertaker", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fdescent2e.fandom.com%2Fwiki%2FSkeleton_Archer&psig=AOvVaw1pqIi8hEW9Wifsqj47luGX&ust=1616606702415000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJjcoO73xu8CFQAAAAAdAAAAABAZ");
    private final User user39 = new User("Nope", "Sauce", MALE_IMAGE_URL_1);
    private final User user40 = new User("Han", "Solo", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fdlpng.com%2Fpng%2F514853&psig=AOvVaw0Q_Sv8wukyR9LTMFcdZJU5&ust=1616606854962000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPiGhf33xu8CFQAAAAAdAAAAABAX");

    private HashMap<String, User> usersMap;
    private User loggedInUser;

    public HashMap<String, User> setUpUsers() {
        if (usersMap == null) {
            usersMap = new HashMap<>();
        }
        User testUser1 = new User("test", "user", "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142");
        testUser1.setPassword("password");

        User testUser2 = new User("test", "user2", "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142");
        testUser2.setPassword("password");

        /*testUser1.addFollowing(user1);
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
        user22.addFollowing(testUser2);*/

        usersMap.put(testUser1.getAlias(), testUser1);
        usersMap.put(testUser2.getAlias(), testUser2);

        return usersMap;
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
//        String url = request.getImageURL();
        String url = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        User user = new User(firstName, lastName, alias, url);

        if (!usersMap.containsKey(user.getAlias())) {
            user.setPassword(password);
            usersMap.put(user.getAlias(), user);
            loggedInUser = user;
            return new RegisterResponse(user, new AuthToken(), true);
        }
        return new RegisterResponse("Username already taken. User different username.", false);
    }

    public BasicResponse logout(LogoutRequest request) {
        if (usersMap != null) {
            if (request.getUser().getAlias().equals(loggedInUser.getAlias())) {
                loggedInUser = null;
                return new BasicResponse(true, "Logout successful.");
            } else {
                return new BasicResponse(false, "Logout failed. Logged in user does not match.");
            }
        }

        return new BasicResponse(false, "Logout failed. No user logged in.");
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
        return new NewStatusResponse(new Status(request.getMessage(), request.getDate(), user8));
    }

    public StatusArrayResponse getStatusArray(StatusArrayRequest request) {

        // Used in place of assert statements because Android does not support them
        //if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getUserAlias() == null) {
                throw new AssertionError();
            }
        //}

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

    //GetUser for Status
    public UserResponse getUserByAlias(UserRequest userRequest) {
        return new UserResponse(user8);
    }

    public static DummyDataProvider getInstance() {
        return new DummyDataProvider();
    }

    public User getSampleDummyUser() {
        return user8;
    }

    public FollowStatusResponse unfollowResponse() {
        return new FollowStatusResponse(true);
    }

    public FollowStatusResponse followResponse() {
        return new FollowStatusResponse(false);
    }
}
