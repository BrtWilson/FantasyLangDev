package edu.byu.cs.client.view.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.shared.model.domain.User;

import edu.byu.cs.tweeter.R;

public class dictionaryFragment extends Fragment {

    private static final String USER_KEY = "";

    private User user;

    public static dictionaryFragment newInstance(User user) {
        dictionaryFragment fragment = new dictionaryFragment();

        Bundle args = new Bundle(1);
        args.putSerializable(USER_KEY, user);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dictionary, container, false);

        user = (User) getArguments().getSerializable(USER_KEY);

        return view;
    }
}
