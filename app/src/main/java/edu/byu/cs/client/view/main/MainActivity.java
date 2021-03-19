package edu.byu.cs.client.view.main;

import android.content.Intent;
import android.os.Bundle;

import com.example.shared.model.service.request.FollowerRequest;
import com.example.shared.model.service.request.FollowingRequest;
import com.example.shared.model.service.response.FollowerResponse;
import com.example.shared.model.service.response.FollowingResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import edu.byu.cs.client.R;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.LogoutResponse;
import com.example.shared.model.service.response.NewStatusResponse;

import edu.byu.cs.client.presenter.FollowerPresenter;
import edu.byu.cs.client.presenter.FollowingPresenter;
import edu.byu.cs.client.presenter.LoginPresenter;
import edu.byu.cs.client.presenter.NewStatusPresenter;
import edu.byu.cs.client.view.asyncTasks.GetFollowerTask;
import edu.byu.cs.client.view.asyncTasks.GetFollowingTask;
import edu.byu.cs.client.view.asyncTasks.LogoutTask;
import edu.byu.cs.client.view.asyncTasks.PostStatusTask;
import edu.byu.cs.client.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements LoginPresenter.View,
        LogoutTask.Observer, StatusDialog.SDListener, PostStatusTask.Observer,
        GetFollowerTask.Observer, GetFollowingTask.Observer {

    public static final String LOG_TAG = "MainActivity";
    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private LoginPresenter loginPresenter;
    private Toast logoutToast;
    private User user;

    TextView followerCount;
    TextView followeeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginPresenter = new LoginPresenter(this);

        user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStatusDialogueBegin();
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        followeeCount = findViewById(R.id.followeeCount);
        GetFollowingTask followingTask = new GetFollowingTask(new FollowingPresenter(null),this);
        followingTask.execute(new FollowingRequest());

        followerCount = findViewById(R.id.followerCount);
        GetFollowerTask followersTask = new GetFollowerTask(new FollowerPresenter(null), this);
        followersTask.execute(new FollowerRequest());
    }

    public void openStatusDialogueBegin() {
        StatusDialog statusDialog = new StatusDialog();
        statusDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void saveMessage(String message) {
        NewStatusPresenter presenter = new NewStatusPresenter(loginPresenter.getView());

        Toast statusPostedToast = Toast.makeText(MainActivity.this, "Status Posted", Toast.LENGTH_LONG);
        statusPostedToast.show();

        postStatusDialogueConclude(message, presenter);
    }

    public void postStatusDialogueConclude(String message, NewStatusPresenter presenter) {
        //SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
        //String format = s.format(new Date());
        Long tsLong = System.currentTimeMillis()/1000;
        String date = tsLong.toString();

        NewStatusRequest newStatusRequest = new NewStatusRequest(
                user.getAlias(),
                message,
                date);
        PostStatusTask postStatusTask = new PostStatusTask(presenter, MainActivity.this);
        postStatusTask.execute(newStatusRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logoutMenu) {
            LogoutTask logoutTask = new LogoutTask(loginPresenter, MainActivity.this);
            LogoutRequest logoutRequest = new LogoutRequest(user);
            logoutTask.execute(logoutRequest);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void logoutSuccessful(LogoutResponse logoutResponse) {
        Intent intent = new Intent(this, LoginActivity.class);
        logoutToast = Toast.makeText(MainActivity.this, "Logging Out", Toast.LENGTH_LONG);
        logoutToast.show();
        startActivity(intent);
    }

    @Override
    public void logoutUnsuccessful(LogoutResponse logoutResponse) {
        logoutToast.makeText(this, "Failed to logout. " + logoutResponse.getMessage(), Toast.LENGTH_LONG);
    }

    @Override
    public void newStatusSuccessful(NewStatusResponse newStatusResponse) {
        logoutToast.makeText(this, "Status posted.", Toast.LENGTH_LONG);
    }

    @Override
    public void newStatusUnsuccessful(NewStatusResponse newStatusResponse) {
        logoutToast.makeText(this, "Status failed to post. " + newStatusResponse.getMessage(), Toast.LENGTH_LONG);
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
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to logout because of exception: " + exception.getMessage(), Toast.LENGTH_LONG);
    }
}