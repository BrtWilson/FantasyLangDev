package edu.byu.cs.client.view.main.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shared.model.domain.User;

import edu.byu.cs.client.view.main.fragments.alphabetFragment;
import edu.byu.cs.client.view.main.fragments.placeholderFragment;
import edu.byu.cs.client.view.main.fragments.syllableFragment;
import edu.byu.cs.client.view.main.fragments.wordCreationFragment;
import edu.byu.cs.tweeter.R;

public class CreationSectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int LANGUAGE_CREATION_FRAGMENT_POSITION = 0;
    private static final int TRANSLATOR_FRAGMENT_POSITION = 1;
    private static final int DICTIONARY_FRAGMENT_POSITION = 2;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.alphabetTabTitle, R.string.syllableTabTitle, R.string.wordCreationTabTitle};
    private final Context mContext;
    private final User user;

    public CreationSectionsPagerAdapter (Context context, FragmentManager fm, User user) {
        super(fm);
        mContext = context;
        this.user = user;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case LANGUAGE_CREATION_FRAGMENT_POSITION:
                return alphabetFragment.newInstance(user);
            case TRANSLATOR_FRAGMENT_POSITION:
                return syllableFragment.newInstance(user);
            case DICTIONARY_FRAGMENT_POSITION:
                return wordCreationFragment.newInstance(user);
            default:
                return placeholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}
