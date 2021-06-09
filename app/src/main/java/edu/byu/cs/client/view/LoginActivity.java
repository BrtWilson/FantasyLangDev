package edu.byu.cs.client.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.response.LoginResponse;

import java.io.Serializable;

import edu.byu.cs.client.presenter.LoginPresenter;
import edu.byu.cs.client.view.asyncTasks.LoginTask;
import edu.byu.cs.client.view.main.activities.MainActivity;
import edu.byu.cs.tweeter.R;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View, LoginTask.Observer {

    private int BACK_NUM = 0;

    private static final String LOG_TAG = "loginActivity";

    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView signupButton;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException ne) {
            //
        }
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();

        presenter = new LoginPresenter(this);

        username = findViewById(R.id.username);
        username.addTextChangedListener(watcher);

        password = findViewById(R.id.password);
        password.addTextChangedListener(watcher);

        loginButton = findViewById(R.id.login);
        loginButton.setEnabled(false);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginTask loginTask = new LoginTask(presenter, LoginActivity.this);
                LoginRequest request = new LoginRequest(username.getText().toString(), password.getText().toString());
                loginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
                username.setText("");
                password.setText("");
            }
        });

        signupButton = findViewById(R.id.signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivities();
            }
        });
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
            if (username.getText().length() != 0 && password.getText().length() != 0) {
                loginButton.setEnabled(true);
            }
            else loginButton.setEnabled(false);
        }
    };

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, SignupActivity.class);
        startActivity(switchActivityIntent);
    }

    @Override
    public void login(LoginResponse response) {
        if (response.isSuccess()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.CURRENT_USER_KEY, response.getUser());
            intent.putExtra(MainActivity.LANGUAGE_KEY, (Serializable) response.getUserLanguages());
            Toast.makeText(this, "Welcome, " + response.getUser().getName() + "!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
//            finish();
        }
        else {
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handleException(Exception e) {
        Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (BACK_NUM == 0) {
            BACK_NUM++;
            Toast.makeText(this, "Press again to exit app", Toast.LENGTH_SHORT).show();
        }
        else {
            super.onBackPressed();
        }
    }
}