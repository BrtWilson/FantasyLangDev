package edu.byu.cs.client.view.main.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.shared.model.domain.User;

import edu.byu.cs.tweeter.R;

public class wordCreationFragment extends Fragment {

    private static final String LANGUAGE_ID_KEY = "";

    private String languageID;

    private Spinner partOfSpeechSpinner;
    private EditText partOfSpeechEditText;
    private Spinner translationSpinner;
    private EditText translationEditText;
    private EditText fantasyWordEditText;
    private Button submitWordButton;

    public static wordCreationFragment newInstance(String languageID) {
        wordCreationFragment fragment = new wordCreationFragment();

        Bundle args = new Bundle(1);
        args.putString(LANGUAGE_ID_KEY, languageID);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_wordcreation, container, false);

        languageID = (String) getArguments().getString(LANGUAGE_ID_KEY);

        partOfSpeechSpinner = (Spinner) view.findViewById(R.id.partOfSpeechSpinner);

        partOfSpeechEditText = (EditText) view.findViewById(R.id.partOfSpeechEditText);
        partOfSpeechEditText.addTextChangedListener(watcher);
        translationSpinner = (Spinner) view.findViewById(R.id.translationSpinner);
        translationSpinner.setEnabled(false);
        translationSpinner.setClickable(false);
        translationEditText = (EditText) view.findViewById(R.id.translationEditText);
        translationEditText.addTextChangedListener(watcher);
        fantasyWordEditText = (EditText) view.findViewById(R.id.fantasyWordEditText);
        fantasyWordEditText.addTextChangedListener(watcher);
        submitWordButton = (Button) view.findViewById(R.id.submitWord);

        submitWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        translationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()) {
                    case "--":
                        translationEditText.setText(null);
                        translationEditText.setEnabled(false);
                        translationSpinner.setEnabled(false);
                        translationSpinner.setClickable(false);
                        break;
                    case "Custom":
                        translationEditText.setEnabled(true);
                        translationEditText.setText(null);
                        translationSpinner.setEnabled(true);
                        translationSpinner.setClickable(true);
                        break;
                    default:
                        translationEditText.setText(parent.getItemAtPosition(position).toString());
                        translationEditText.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        partOfSpeechSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<CharSequence> adapter;
                switch (parent.getItemAtPosition(position).toString()) {
                    case "--":
                        partOfSpeechEditText.setText(null);
                        partOfSpeechEditText.setEnabled(false);
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translation, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(false);
                        fantasyWordEditText.setText(null);
                        break;
                    case "Custom":
                        partOfSpeechEditText.setEnabled(true);
                        partOfSpeechEditText.setText(null);
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenCustom, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    case "Verb":
                        partOfSpeechEditText.setEnabled(false);
                        partOfSpeechEditText.setText("Verb");
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenVerbs, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    case "Noun":
                        partOfSpeechEditText.setEnabled(false);
                        partOfSpeechEditText.setText("Noun");
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenNouns, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    case "Adjective":
                        partOfSpeechEditText.setEnabled(false);
                        partOfSpeechEditText.setText("Adjective");
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenAdjectives, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    case "Article/Determinant":
                        partOfSpeechEditText.setEnabled(false);
                        partOfSpeechEditText.setText("Article/Determinant");
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenArticlesDeterminants, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    case "Preposition":
                        partOfSpeechEditText.setEnabled(false);
                        partOfSpeechEditText.setText("Preposition");
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenPrepositions, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    case "Conjunction":
                        partOfSpeechEditText.setEnabled(false);
                        partOfSpeechEditText.setText("Conjunction");
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenConjunctions, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    case "Pronoun":
                        partOfSpeechEditText.setEnabled(false);
                        partOfSpeechEditText.setText("Pronoun");
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenPronouns, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    case "Prefix":
                        partOfSpeechEditText.setEnabled(false);
                        partOfSpeechEditText.setText("Prefix");
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenPrefix, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    case "Suffix":
                        partOfSpeechEditText.setEnabled(false);
                        partOfSpeechEditText.setText("Suffix");
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenSuffix, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    case "Most Used":
                        partOfSpeechEditText.setEnabled(false);
                        partOfSpeechEditText.setText("Most Used");
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenMostUsed, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    case "Other":
                        partOfSpeechEditText.setEnabled(false);
                        partOfSpeechEditText.setText("Other");
                        adapter = ArrayAdapter.createFromResource(getContext(), R.array.translationGivenOther, android.R.layout.simple_spinner_dropdown_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        translationSpinner.setAdapter(adapter);
                        fantasyWordEditText.setEnabled(true);
                        break;
                    default:
                        //
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

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
            if (partOfSpeechEditText.getText().length() != 0 && translationEditText.getText().length() != 0 && fantasyWordEditText.getText().length() != 0) {
                submitWordButton.setEnabled(true);
            }
            else submitWordButton.setEnabled(false);
        }
    };
}
