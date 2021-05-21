package edu.byu.cs.client.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import edu.byu.cs.client.presenter.SignUpPresenter;
import edu.byu.cs.client.view.asyncTasks.SignUpTask;
import edu.byu.cs.client.view.main.activities.MainActivity;
import edu.byu.cs.tweeter.R;

public class SignupActivity extends AppCompatActivity implements SignUpPresenter.View, SignUpTask.Observer{

    private int BACK_NUM = 0;

    private Button back;
    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText password;
    private Button signup;

    private SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().show();
            this.getSupportActionBar().setTitle("Sign Up");
        }
        catch (NullPointerException ne) {
            //
        }
        setContentView(R.layout.activity_signup);
        Intent intent = getIntent();

        presenter = new SignUpPresenter(this);

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        firstName = (EditText) findViewById(R.id.firstName);
        firstName.addTextChangedListener(watcher);

        lastName = (EditText) findViewById(R.id.lastName);
        lastName.addTextChangedListener(watcher);

        username = (EditText) findViewById(R.id.username);
        username.addTextChangedListener(watcher);

        password = (EditText) findViewById(R.id.password);
        password.addTextChangedListener(watcher);

        signup = (Button) findViewById(R.id.signup);
        signup.setEnabled(false);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SignUpTask task = new SignUpTask(presenter, SignupActivity.this);
                RegisterRequest request = new RegisterRequest(firstName.toString() + " " + lastName.toString(), username.toString(), password.toString());
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
                firstName.setText("");
                lastName.setText("");
                username.setText("");
                password.setText("");
                finish();
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
                signup.setEnabled(true);
            }
            else signup.setEnabled(false);
        }
    };

    @Override
    public void signUp(RegisterResponse response) {
        if (response.isSuccess()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.CURRENT_USER_KEY, response.getUser());
            Toast.makeText(this, "Welcome, " + response.getUser().getName() + "!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        else Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleException(Exception e) {
        Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (BACK_NUM == 0) {
            BACK_NUM++;
            Toast.makeText(this, "Press again to go back to login", Toast.LENGTH_SHORT).show();
        }
        else {
            super.onBackPressed();
        }
    }
}
