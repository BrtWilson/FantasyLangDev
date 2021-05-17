package edu.byu.cs.client.view.main.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fantasylangdev.R;
import com.example.fantasylangdev.client.view.main.fragments.PlaceholderFragment;
import com.example.fantasylangdev.client.view.main.fragments.dictionaryFragment;
import com.example.fantasylangdev.client.view.main.fragments.languageCreationFragment;
import com.example.fantasylangdev.client.view.main.fragments.translatorFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int LANGUAGE_CREATION_FRAGMENT_POSITION = 0;
    private static final int TRANSLATOR_FRAGMENT_POSITION = 1;
    private static final int DICTIONARY_FRAGMENT_POSITION = 2;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.languageCreationTabTitle, R.string.translatorTabTitle, R.string.dictionaryTabTitle};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public androidx.fragment.app.Fragment getItem(int position) {
        switch (position) {
            case LANGUAGE_CREATION_FRAGMENT_POSITION:
                return new languageCreationFragment();
            case TRANSLATOR_FRAGMENT_POSITION:
                return new translatorFragment();
            case DICTIONARY_FRAGMENT_POSITION:
                return new dictionaryFragment();
            default:
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
        // Show 3 total pages.
        return 3;
    }
}
