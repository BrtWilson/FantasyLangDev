package edu.byu.cs.client.view.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;

public class alphabetFragment extends Fragment {

    private static final String LANGUAGE_ID_KEY = "";

    private String languageID;
    private EditText alphabetEditText;
    private Button alphabetSubmitButton;

    public static alphabetFragment newInstance(String languageID) {
        alphabetFragment fragment = new alphabetFragment();

        Bundle args = new Bundle(1);
        args.putString(LANGUAGE_ID_KEY, languageID);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_alphabet, container, false);

        languageID = (String) getArguments().getString(LANGUAGE_ID_KEY);

//        TextView textView = view.findViewById(R.id.editTexView);
//        textView.setText(language);

        alphabetEditText = (EditText) view.findViewById(R.id.alphabetEditText);
        alphabetSubmitButton = (Button) view.findViewById(R.id.alphabetSubmit);

        return view;
    }
}
