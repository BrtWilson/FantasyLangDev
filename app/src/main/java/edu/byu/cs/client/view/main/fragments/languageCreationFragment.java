package edu.byu.cs.client.view.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.shared.model.domain.User;
import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.client.view.main.adapters.CreationSectionsPagerAdapter;
import edu.byu.cs.tweeter.R;

public class languageCreationFragment extends Fragment {

    private static final String USER_KEY = "";

    private User user;

    public static languageCreationFragment newInstance(User user) {
        languageCreationFragment fragment = new languageCreationFragment();

        Bundle args = new Bundle(1);
        args.putSerializable(USER_KEY, user);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_languagecreation, container, false);

        user = (User) getArguments().getSerializable(USER_KEY);

        CreationSectionsPagerAdapter adapter = new CreationSectionsPagerAdapter(getContext(), getChildFragmentManager(), user);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        return view;
    }
}
