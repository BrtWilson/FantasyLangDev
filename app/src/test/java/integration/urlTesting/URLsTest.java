package integration.urlTesting;

import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.response.FollowerResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.client.model.net.ServerFacade;
import edu.byu.cs.client.model.service.FollowerService;
import edu.byu.cs.client.util.ByteArrayUtils;

public class URLsTest {

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

    // This is the hard coded followers data returned by the 'getFollowers()' method
    private static final String MALE_IMAGE_URL_1 = "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142";
    private static final String FEMALE_IMAGE_URL_1 = "https://www.seekpng.com/png/small/327-3273611_katara-vector-katara.png";

    private final User user21 = new User("Kin", "Jonahs", MALE_IMAGE_URL_1);
    private final User user22 = new User("Luke", "Skywalker", "https://www.vippng.com/png/detail/510-5106254_luke-skywalker-cliparts-luke-skywalker-star-wars-5.png");
    private final User user23 = new User("Anakin", "Skywalker", "https://images.immediate.co.uk/production/volatile/sites/3/2019/12/Episode_III_Revenge_Christensen07-8bbd9e4.jpg?quality=90&resize=620,413");
    private final User user24 = new User("Mace", "Windu", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXViPRNkk2tpSFPpyuGE6HoIz6SgMzhO27iA&usqp=CAU");
    private final User user25 = new User("Obi-wan", "Kenobi", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwIQVNGsBqqIYz6cMb3-xpBfurd2KXQr72kg&usqp=CAU");
    private final User user26 = new User("Sokka", "Sacapuntes", "https://oyster.ignimgs.com/mediawiki/apis.ign.com/avatar-the-last-airbender/a/ad/Sokka_img.jpg?width=325");
    private final User user27 = new User("Aang", "Jones", "https://www.kindpng.com/picc/m/25-251027_transparent-avatar-aang-png-avatar-aang-face-png.png");
    private final User user28 = new User("The Fatherlord", "Smith", "https://static.wikia.nocookie.net/avatar/images/4/4a/Ozai.png/revision/latest/scale-to-width-down/333?cb=20130612170743");
    private final User user29 = new User("Darth", "Vader", "https://static.wikia.nocookie.net/disney/images/8/80/Profile_-_Darth_Vader.png/revision/latest/scale-to-width-down/516?cb=20190314100842");
    private final User user30 = new User("Darth", "Plagueis", "https://static.wikia.nocookie.net/from-the-crazy/images/6/67/Plagueis.png/revision/latest/scale-to-width-down/310?cb=20180609204346");
    private final User user31 = new User("Chancellor", "Palpatine", "https://assets-jpcust.jwpsrv.com/thumbs/iko5Bilc-720.jpg");
    private final User user32 = new User("The", "Senate", "https://pbs.twimg.com/profile_images/647662588457676800/S8-ME1Jb_400x400.png");
    private final User user33 = new User("Toph", "The Destroyer", "https://i.pinimg.com/474x/84/b2/9b/84b29be844e69ed288bd45a257a72579.jpg");
    private final User user34 = new User("Cairne", "Bloodhoof", "https://static.wikia.nocookie.net/wowpedia/images/8/8f/Cairne-WC3.jpg/revision/latest/scale-to-width-down/102?cb=20051026232729");
    private final User user35 = new User("Prince", "Arthas", "https://www.personality-database.com/profile_images/7946.png?id=18875");
    private final User user36 = new User("Katara", "Sorg", FEMALE_IMAGE_URL_1);
    private final User user37 = new User("Cleric", "Uther", "https://www.guiaswowtbc.com/wp-content/uploads/2020/08/uther.png");
    private final User user38 = new User("Frost", "The Undertaker", "https://static.wikia.nocookie.net/descent2e/images/3/36/SkeletonArcherHM.jpg/revision/latest?cb=20150201194421");
    private final User user39 = new User("Nope", "Sauce", MALE_IMAGE_URL_1);
    private final User user40 = new User("Han", "Solo", "https://i.pinimg.com/736x/43/0a/2e/430a2e07513e5fb3e1ee95417bad1719.jpg");

    User testUser1 = new User("test", "user", "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142");
    User testUser2 = new User("test", "user2", "https://static.wikia.nocookie.net/avatar/images/4/4b/Zuko.png/revision/latest?cb=20180630112142");

    /**
     * Verify that for successful requests the {@link FollowerService#getFollowers(FollowerRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void myTest() throws IOException, TweeterRemoteException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user1.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user2.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user3.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user4.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user5.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user6.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user7.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user8.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user9.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user10.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user11.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user12.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user13.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user14.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user15.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user16.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user17.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user18.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user19.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user20.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user21.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user22.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user23.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user24.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user25.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user26.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user27.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user28.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user29.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user30.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user31.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user32.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user33.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user34.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user35.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user36.getImageUrl()); // <-
        bytes = ByteArrayUtils.bytesFromUrl(user37.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user38.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user39.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(user40.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(testUser1.getImageUrl());
        bytes = ByteArrayUtils.bytesFromUrl(testUser2.getImageUrl());

        /*for(User user : getDummy) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }*/
    }
    @Test
    public void myTest2() throws IOException, TweeterRemoteException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user36.getImageUrl()); // <-

        /*for(User user : getDummy) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }*/
    }

    @Test
    public void myOldTest() throws IOException, TweeterRemoteException {

        User user22 = new User("Luke", "Skywalker", "https://www.vippng.com/png/detail/510-5106254_luke-skywalker-cliparts-luke-skywalker-star-wars-5.png");
        User user23 = new User("Anakin", "Skywalker", "https://images.immediate.co.uk/production/volatile/sites/3/2019/12/Episode_III_Revenge_Christensen07-8bbd9e4.jpg?quality=90&resize=620,413");
        User user24 = new User("Mace", "Windu", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXViPRNkk2tpSFPpyuGE6HoIz6SgMzhO27iA&usqp=CAU");
        User user25 = new User("Obi-wan", "Kenobi", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwIQVNGsBqqIYz6cMb3-xpBfurd2KXQr72kg&usqp=CAU");
        User user26 = new User("Sokka", "Sacapuntes", "https://oyster.ignimgs.com/mediawiki/apis.ign.com/avatar-the-last-airbender/a/ad/Sokka_img.jpg?width=325");
        User user27 = new User("Aang", "Jones", "https://www.kindpng.com/picc/m/25-251027_transparent-avatar-aang-png-avatar-aang-face-png.png");
        User user28 = new User("The Fatherlord", "Smith", "https://static.wikia.nocookie.net/avatar/images/4/4a/Ozai.png/revision/latest/scale-to-width-down/333?cb=20130612170743");
        User user29 = new User("Darth", "Vader", "https://static.wikia.nocookie.net/disney/images/8/80/Profile_-_Darth_Vader.png/revision/latest/scale-to-width-down/516?cb=20190314100842");
        User user30 = new User("Darth", "Plagueis", "https://static.wikia.nocookie.net/from-the-crazy/images/6/67/Plagueis.png/revision/latest/scale-to-width-down/310?cb=20180609204346");
        User user31 = new User("Chancellor", "Palpatine", "https://assets-jpcust.jwpsrv.com/thumbs/iko5Bilc-720.jpg");
        User user32 = new User("The", "Senate", "https://pbs.twimg.com/profile_images/647662588457676800/S8-ME1Jb_400x400.png");
        User user33 = new User("Toph", "The Destroyer", "https://i.pinimg.com/474x/84/b2/9b/84b29be844e69ed288bd45a257a72579.jpg");

        User user34 = new User("Cairne", "Bloodhoof", "https://static.wikia.nocookie.net/wowpedia/images/8/8f/Cairne-WC3.jpg/revision/latest/scale-to-width-down/102?cb=20051026232729");
        User user35 = new User("Prince", "Arthas", "https://www.personality-database.com/profile_images/7946.png?id=18875"); // <- THIS ONE
        User user37 = new User("Cleric", "Uther", "https://www.guiaswowtbc.com/wp-content/uploads/2020/08/uther.png");
        User user38 = new User("Frost", "The Undertaker", "https://static.wikia.nocookie.net/descent2e/images/3/36/SkeletonArcherHM.jpg/revision/latest?cb=20150201194421");
        User user40 = new User("Han", "Solo", "https://i.pinimg.com/736x/43/0a/2e/430a2e07513e5fb3e1ee95417bad1719.jpg"); //<- THIS ONE
        List<User> getDummy = Arrays.asList(user22, user23, user24, user25, user26, user27, user28, user29, user30, user31, user32, user33, user34, user35, user37, user38, user40);

        for(User user : getDummy) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }
}
