package edu.byu.cs.client.view.main.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.GetLanguageDataRequest;
import com.example.shared.model.service.response.GetLanguageDataResponse;

import edu.byu.cs.client.presenter.GetLanguageDataPresenter;
import edu.byu.cs.client.view.asyncTasks.GetLanguageDataTask;
import edu.byu.cs.tweeter.R;

public class syllableFragment extends Fragment implements GetLanguageDataPresenter.View, GetLanguageDataTask.Observer {

    private static final String LANGUAGE_ID_KEY = "";

    private String languageID;

    private EditText beginningConsonantsEditText;
    private EditText vowelsEditText;
    private EditText endConsonantsEditText;

    private GetLanguageDataPresenter presenter;

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

        presenter = new GetLanguageDataPresenter(this);

        beginningConsonantsEditText = (EditText) view.findViewById(R.id.beginningConsonantsEditText);
        vowelsEditText = (EditText) view.findViewById(R.id.vowelsEditText);
        endConsonantsEditText = (EditText) view.findViewById(R.id.endConsonantsEditText);

        setSyllables();

        return view;
    }

    public void setSyllables() {
        GetLanguageDataTask task = new GetLanguageDataTask(presenter, syllableFragment.this);
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
    public void handleException(Exception e) {
        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
