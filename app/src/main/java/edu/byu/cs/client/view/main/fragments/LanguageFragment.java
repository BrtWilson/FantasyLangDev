package edu.byu.cs.client.view.main.fragments;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

import com.example.shared.model.domain.Language;

import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.R;

public class LanguageFragment extends DialogFragment {

    private static final String LANGUAGES_KEY = "Languages";
    private static final String USER_KEY = "User";

    private String user = null;
    private List<Language> languages = null;
    OnDialogResult dialogResult;

    private EditText createLanguageEditText;
    private Button createLanguageButton;

    public LanguageFragment() {}

    public static LanguageFragment newInstance(String username) {
        LanguageFragment fragment = new LanguageFragment();

        Bundle args = new Bundle(1);
        args.putString(USER_KEY, username);

        fragment.setArguments(args);
        return fragment;
    }

    public static LanguageFragment newInstance(List<Language> languages) {
        LanguageFragment fragment = new LanguageFragment();

        Bundle args = new Bundle(1);
        args.putSerializable(LANGUAGES_KEY, (Serializable) languages);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_language, null);

        languages = (List<Language>) getArguments().getSerializable(LANGUAGES_KEY);
        user = (String) getArguments().getString(USER_KEY);

        createLanguageEditText = (EditText) view.findViewById(R.id.addLanguageEditText);
        createLanguageEditText.addTextChangedListener(watcher);
        createLanguageButton = (Button) view.findViewById(R.id.addLanguageButton);

        builder.setTitle("Add or Change Language:");
        builder.setView(view);

        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        if (languages != null) {
            for (int i = 0; i < languages.size(); i++) {
                Button button = new Button(getContext());
                button.setText(languages.get(i).getLanguageName());
                int finalI = i;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialogResult != null) dialogResult.finish(finalI);
                        dialog.dismiss();
                    }
                });

                LinearLayout ll = (LinearLayout)view.findViewById(R.id.list_layout);
                ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                ll.addView(button, lp);
            }
        }

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                    }
                });

                Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        return dialog;
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
            if (createLanguageEditText.getText().length() != 0) {
                createLanguageButton.setEnabled(true);
            }
            else createLanguageButton.setEnabled(false);
        }
    };

    public void setDialogResult(OnDialogResult result) {
        dialogResult = result;
    }

    public interface OnDialogResult {
        void finish(int result);
    }
}