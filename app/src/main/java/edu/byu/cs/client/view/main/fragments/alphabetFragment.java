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
import com.example.shared.model.service.request.UpdateAlphabetRequest;
import com.example.shared.model.service.response.GeneralUpdateResponse;
import com.example.shared.model.service.response.GetLanguageDataResponse;

import edu.byu.cs.client.presenter.GetLanguageDataPresenter;
import edu.byu.cs.client.presenter.UpdateAlphabetPresenter;
import edu.byu.cs.client.view.asyncTasks.GetLanguageDataTask;
import edu.byu.cs.client.view.asyncTasks.UpdateAlphabetTask;
import edu.byu.cs.tweeter.R;

public class alphabetFragment extends Fragment implements GetLanguageDataPresenter.View, GetLanguageDataTask.Observer, UpdateAlphabetPresenter.View, UpdateAlphabetTask.Observer {

    private static final String LANGUAGE_ID_KEY = "";

    private String languageID;
    private String languageName;
    private String userName;

    private EditText alphabetEditText;
    private Button alphabetSubmitButton;

    private GetLanguageDataPresenter languageDataPresenter;
    private UpdateAlphabetPresenter alphabetPresenter;

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
        languageName = "";
        userName = "";

        alphabetEditText = (EditText) view.findViewById(R.id.alphabetEditText);
        alphabetSubmitButton = (Button) view.findViewById(R.id.alphabetSubmit);

        languageDataPresenter = new GetLanguageDataPresenter(this);
        alphabetPresenter = new UpdateAlphabetPresenter(this);

        setAlphabet();

        alphabetSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateAlphabetTask task = new UpdateAlphabetTask(alphabetPresenter, alphabetFragment.this);
                UpdateAlphabetRequest request = new UpdateAlphabetRequest(userName, languageName, languageID, alphabetEditText.getText().toString());
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
            }
        });

        return view;
    }

    public void setAlphabet() {
        GetLanguageDataTask task = new GetLanguageDataTask(languageDataPresenter, alphabetFragment.this);
        GetLanguageDataRequest request = new GetLanguageDataRequest(languageID);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,request);
    }

    @Override
    public void updateAlphabet(GeneralUpdateResponse response) {
        if (response.getLanguageID() != null) Toast.makeText(getContext(), "Alphabet updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getLanguageData(GetLanguageDataResponse response) {
        if (response.getAlphabet() != null) alphabetEditText.setText(response.getAlphabet());
        userName = response.getUserName();
        languageName = response.getLanguageName();
    }

    @Override
    public void handleException(Exception e) {
        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
