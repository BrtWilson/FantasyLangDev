package edu.byu.cs.client.view.main.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.request.UpdateSyllablesRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;

import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.client.presenter.GetLanguageDataPresenter;
import edu.byu.cs.client.presenter.UpdateSyllablesPresenter;
import edu.byu.cs.client.view.asyncTasks.GetLanguageDataTask;
import edu.byu.cs.client.view.asyncTasks.UpdateSyllablesTask;
import edu.byu.cs.tweeter.R;

public class syllableFragment extends Fragment implements GetLanguageDataPresenter.View, GetLanguageDataTask.Observer, UpdateSyllablesPresenter.View, UpdateSyllablesTask.Observer {

    private static final String LANGUAGE_ID_KEY = "";

    private String languageID;

    private EditText beginningConsonantsEditText;
    private EditText vowelsEditText;
    private EditText endConsonantsEditText;
    private Button syllableSubmit;

    private GetLanguageDataPresenter languageDataPresenter;
    private UpdateSyllablesPresenter syllablesPresenter;

    public static syllableFragment newInstance(String languageID) {
        syllableFragment fragment = new syllableFragment();

        Bundle args = new Bundle(1);
        args.putString(LANGUAGE_ID_KEY, languageID);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_syllable, container, false);

        languageID = (String) getArguments().getString(LANGUAGE_ID_KEY);

        languageDataPresenter = new GetLanguageDataPresenter(this);
        syllablesPresenter = new UpdateSyllablesPresenter(this);

        beginningConsonantsEditText = (EditText) view.findViewById(R.id.beginningConsonantsEditText);
        vowelsEditText = (EditText) view.findViewById(R.id.vowelsEditText);
        endConsonantsEditText = (EditText) view.findViewById(R.id.endConsonantsEditText);
        syllableSubmit = (Button) view.findViewById(R.id.syllableSubmit);

        setSyllables();

        syllableSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateSyllablesTask task = new UpdateSyllablesTask(syllablesPresenter, syllableFragment.this);
                Map<Integer, String> syllables = new HashMap<Integer, String>();
                syllables.put(0, beginningConsonantsEditText.getText().toString());
                syllables.put(1, vowelsEditText.getText().toString());
                syllables.put(2, endConsonantsEditText.getText().toString());
                UpdateSyllablesRequest request = new UpdateSyllablesRequest(languageID, "CVC",syllables);
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,request);
            }
        });

        return view;
    }

    public void setSyllables() {
        GetLanguageDataTask task = new GetLanguageDataTask(languageDataPresenter, syllableFragment.this);
        GetLanguageDataRequest request = new GetLanguageDataRequest(languageID);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,request);
    }

    @Override
    public void getLanguageData(GetLanguageDataResponse response) {
        if (response.getSyllableData() != null) {
            if (response.getSyllableData().get(0) != null) beginningConsonantsEditText.setText(response.getSyllableData().get(0));
            if (response.getSyllableData().get(1) != null) vowelsEditText.setText(response.getSyllableData().get(1));
            if (response.getSyllableData().get(2) != null) endConsonantsEditText.setText(response.getSyllableData().get(2));
        }
    }

    @Override
    public void updateSyllables(GeneralUpdateResponse response) {
        if (response.getLanguageID() != null) Toast.makeText(getContext(), "Syllables updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleException(Exception e) {
        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
