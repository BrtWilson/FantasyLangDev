package edu.byu.cs.client.view.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.client.R;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import edu.byu.cs.client.view.main.following.FollowingFragment;
import edu.byu.cs.client.view.main.following.FollowerFragment;
import edu.byu.cs.client.view.main.following.StatusFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to one of the sections/tabs/pages
 * of the Main Activity.
 */
class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static int FEED_FRAGMENT_POSITION = 0;
    private static int STORY_FRAGMENT_POSITION = 1;
    private static int FOLLOWING_FRAGMENT_POSITION = 2;
    private static int FOLLOWER_FRAGMENT_POSITION = 3;

    @StringRes
    private static int[] TAB_TITLES = new int[]{R.string.feedTabTitle, R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;
    private final User user;
    private final AuthToken authToken;
    private int count = 4;

    public SectionsPagerAdapter(Context context, FragmentManager fm, User user, AuthToken authToken) {
        super(fm);
        if(context instanceof UserPageActivity) {
            TAB_TITLES = new int[]{R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
            FOLLOWING_FRAGMENT_POSITION = 1;
            FOLLOWER_FRAGMENT_POSITION = 2;
            count = 3;
        }
        mContext = context;
        this.user = user;
        this.authToken = authToken;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == FOLLOWING_FRAGMENT_POSITION) {
            return FollowingFragment.newInstance(user, authToken);
        } else if (position == FOLLOWER_FRAGMENT_POSITION) {
            return FollowerFragment.newInstance(user, authToken);
        } else if (position == FEED_FRAGMENT_POSITION) {
            return StatusFragment.newInstance(user, authToken, true);
        } else if (position == STORY_FRAGMENT_POSITION) {
            return StatusFragment.newInstance(user, authToken, false);
        }
        else {
            return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return count;
    }
}