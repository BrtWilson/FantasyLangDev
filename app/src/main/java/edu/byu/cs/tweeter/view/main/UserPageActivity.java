package edu.byu.cs.tweeter.view.main;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class UserPageActivity extends AppCompatActivity {

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    public static final String TARGET_USER_KEY = "TargetUser";

    private User user;
    private AuthToken authToken;
    private User targetUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        targetUser = (User) getIntent().getSerializableExtra(TARGET_USER_KEY);
        user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null || targetUser == null) {
            throw new RuntimeException("User not passed to activity");
        }

        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        TextView userName = findViewById(R.id.userName);
        userName.setText(targetUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(targetUser.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(targetUser.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount1);
        followeeCount.setText(getString(R.string.followeeCount, targetUser.getFolloweeCount()));

        TextView followerCount = findViewById(R.id.followerCount1);
        followerCount.setText(getString(R.string.followerCount, targetUser.getFollowerCount()));

        TextView followButton = findViewById(R.id.followButton);
        if(user.checkFollowStatus(targetUser)) {
            followButton.setText(getString(R.string.followButtonText2));
            followButton.setBackgroundColor(Color.GRAY);
        }
        else {
            followButton.setText(getString(R.string.followButtonText));
            followButton.setBackgroundColor(Color.RED);
        }

        followButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(followButton.getText().toString().equals("Following")) {
                    followButton.setText(getString(R.string.followButtonText));
                    followButton.setBackgroundColor(Color.RED);
                    user.removeFollowee(targetUser);
                    targetUser.removeFollower(user);
                }
                else {
                    followButton.setText(getString(R.string.followButtonText2));
                    followButton.setBackgroundColor(Color.GRAY);
                    targetUser.addFollower(user);
                    user.addFollowing(targetUser);
                }
                followerCount.setText(getString(R.string.followerCount, targetUser.getFollowerCount()));
            }
        });
    }
}