package edu.byu.cs.client.view.main.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.shared.model.domain.Language;
import com.example.shared.model.domain.User;

import java.io.Serializable;

import edu.byu.cs.tweeter.R;

public class translatorFragment extends Fragment {

    private static final String LANGUAGE_KEY = "";

    private Language language;

    private EditText firstEditText;
    private EditText secondEditText;
    private Button firstToSecondButton;
    private Button secondToFirstButton;

    public static translatorFragment newInstance(Language language) {
        translatorFragment fragment = new translatorFragment();

        Bundle args = new Bundle(1);
        args.putSerializable(LANGUAGE_KEY, (Serializable) language);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_translator, container, false);

        language = (Language) getArguments().getSerializable(LANGUAGE_KEY);

        firstEditText = (EditText) view.findViewById(R.id.sentenceToTranslate);
        firstEditText.addTextChangedListener(watcher);
        secondEditText = (EditText) view.findViewById(R.id.translation);
        secondEditText.addTextChangedListener(watcher);
        firstToSecondButton = (Button) view.findViewById(R.id.fantasyToTranslationButton);
        secondToFirstButton = (Button) view.findViewById(R.id.translationToFantasyButton);

        return view;
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (firstEditText.getText().length() != 0) {
                firstToSecondButton.setEnabled(true);
            }
            else firstToSecondButton.setEnabled(false);
            if (secondEditText.getText().length() != 0) secondToFirstButton.setEnabled(true);
            else secondToFirstButton.setEnabled(false);
        }
    };
}
