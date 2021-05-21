package edu.byu.cs.client.view.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.shared.model.domain.User;

import edu.byu.cs.tweeter.R;

public class syllableFragment extends Fragment {

    private static final String USER_KEY = "";

    private User user;

    public static syllableFragment newInstance(User user) {
        syllableFragment fragment = new syllableFragment();

        Bundle args = new Bundle(1);
        args.putSerializable(USER_KEY, user);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_syllable, container, false);

        user = (User) getArguments().getSerializable(USER_KEY);

        return view;
    }
}
