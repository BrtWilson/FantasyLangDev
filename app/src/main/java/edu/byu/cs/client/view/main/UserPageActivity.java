package edu.byu.cs.client.view.main;

import android.graphics.Color;
import android.os.Bundle;

import com.example.shared.model.service.request.FollowStatusRequest;
import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowStatusResponse;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.FollowingResponse;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.client.R;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;

import edu.byu.cs.client.presenter.FollowStatusPresenter;
import edu.byu.cs.client.presenter.FollowerPresenter;
import edu.byu.cs.client.presenter.FollowingPresenter;
import edu.byu.cs.client.view.asyncTasks.GetFollowStatusTask;
import edu.byu.cs.client.view.asyncTasks.GetFollowerTask;
import edu.byu.cs.client.view.asyncTasks.GetFollowingTask;
import edu.byu.cs.client.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class UserPageActivity extends AppCompatActivity implements GetFollowingTask.Observer,
        GetFollowerTask.Observer, GetFollowStatusTask.Observer {

    private static final String LOG_TAG = "UserPageActivity";

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    public static final String TARGET_USER_KEY = "TargetUser";

    private User user;
    private AuthToken authToken;
    private User targetUser;

    private TextView followeeCount;
    private TextView followerCount;

    private FollowStatusPresenter followStatusPresenter;
    private Button followButton;

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

        followeeCount = findViewById(R.id.followeeCount1);
        GetFollowingTask followingTask = new GetFollowingTask(new FollowingPresenter(null),this);
        followingTask.execute(new FollowingRequest());

        followerCount = findViewById(R.id.followerCount1);
        GetFollowerTask followersTask = new GetFollowerTask(new FollowerPresenter(null), this);
        followersTask.execute(new FollowerRequest());

        followStatusPresenter = new FollowStatusPresenter(null);
        followButton = findViewById(R.id.followButton);
        setFollowButtonText();

        followButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(OtherUserActivity.this,"Follow/Unfollow button pressed",Toast.LENGTH_SHORT).show();
                FollowStatusRequest followStatusRequest;
                if (followButton.getText().equals("Follow"))
                    followStatusRequest = new FollowStatusRequest(targetUser, user, FollowStatusRequest.FOLLOW,authToken);
                else
                    followStatusRequest = new FollowStatusRequest(targetUser, user, FollowStatusRequest.UNFOLLOW,authToken);
                GetFollowStatusTask getFollowStatusTask = new GetFollowStatusTask(followStatusPresenter,UserPageActivity.this);
                getFollowStatusTask.execute(followStatusRequest);
            }
        });
    }

    /**
     * Method for retrieving whether the logged-in user follows the displayed user
     */
    private void setFollowButtonText() {
        //default is not following, we only make a change if there exists a relationship
        FollowStatusRequest followStatusRequest = new FollowStatusRequest(targetUser, user, FollowStatusRequest.GET,authToken);
        GetFollowStatusTask getFollowStatusTask = new GetFollowStatusTask(followStatusPresenter,UserPageActivity.this);
        getFollowStatusTask.execute(followStatusRequest);
    }

    @Override
    public void followStatusRequestSuccessful(FollowStatusResponse followStatusResponse) {
        if (followStatusResponse.relationshipExists())
            followButton.setText(R.string.followButtonText2);
        else
            followButton.setText(R.string.followButtonText);
    }

    @Override
    public void handleFollowStatusException(Exception exception) {
        Toast.makeText(this,"Could not process follow request",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void followersRetrieved(FollowerResponse followerResponse) {
        followerCount.setText(getString(R.string.followerCount, followerResponse.getNumFollowers()));
    }

    @Override
    public void followeesRetrieved(FollowingResponse followingResponse) {
        followeeCount.setText(getString(R.string.followeeCount, followingResponse.getNumFollowing()));
    }

    @Override
    public void handleException(Exception exception) {
        if (exception.getMessage() != null)
            Log.e(LOG_TAG, exception.getMessage());
        Toast.makeText(this,
                "Task failed because of exception: " + exception.getMessage(),
                Toast.LENGTH_LONG).show();
    }
}