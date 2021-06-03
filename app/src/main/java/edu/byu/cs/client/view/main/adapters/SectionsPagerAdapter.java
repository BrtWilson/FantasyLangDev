package edu.byu.cs.client.view.main.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shared.model.domain.Language;
import com.example.shared.model.domain.User;

import edu.byu.cs.client.view.main.fragments.dictionaryFragment;
import edu.byu.cs.client.view.main.fragments.languageCreationFragment;
import edu.byu.cs.client.view.main.fragments.placeholderFragment;
import edu.byu.cs.client.view.main.fragments.translatorFragment;
import edu.byu.cs.tweeter.R;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int LANGUAGE_CREATION_FRAGMENT_POSITION = 0;
    private static final int TRANSLATOR_FRAGMENT_POSITION = 1;
    private static final int DICTIONARY_FRAGMENT_POSITION = 2;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.languageCreationTabTitle, R.string.translatorTabTitle, R.string.dictionaryTabTitle};
    private final Context mContext;
    private final User user;
    private final Language language;

    public SectionsPagerAdapter (Context context, FragmentManager fm, User user, Language language) {
        super(fm);
        mContext = context;
        this.user = user;
        this.language = language;
        System.out.println("LANGUAGE: " + language.getLanguageName());
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case LANGUAGE_CREATION_FRAGMENT_POSITION:
                return languageCreationFragment.newInstance(language);
            case TRANSLATOR_FRAGMENT_POSITION:
                return translatorFragment.newInstance(language);
            case DICTIONARY_FRAGMENT_POSITION:
                return dictionaryFragment.newInstance(language);
            default:
                return placeholderFragment.newInstance(position + 1);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        //
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
