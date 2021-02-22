package edu.byu.cs.tweeter.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterPresenter.View, RegisterTask.Observer {

    private static final String LOG_TAG = "RegisterActivity";

    private RegisterPresenter presenter;
    private Toast registerToast;
    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText password;
    private EditText url;
    private Button registerConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new RegisterPresenter(this);

        firstName = (EditText) findViewById(R.id.register_firstname);
        lastName = (EditText) findViewById(R.id.register_lastname);
        username = (EditText) findViewById(R.id.register_username);
        password = (EditText) findViewById(R.id.register_password);
        url = (EditText) findViewById(R.id.register_url);
        registerConfirm = findViewById(R.id.RegisterConfirm);


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerConfirm.setEnabled(!TextUtils.isEmpty(firstName.getText().toString()) &&
                        !TextUtils.isEmpty(lastName.getText().toString()) &&
                        !TextUtils.isEmpty(username.getText().toString()) &&
                        !TextUtils.isEmpty(password.getText().toString()) &&
                        !TextUtils.isEmpty(url.getText().toString()));
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        firstName.addTextChangedListener(watcher);
        lastName.addTextChangedListener(watcher);
        username.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);
        url.addTextChangedListener(watcher);

        registerConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registerToast = Toast.makeText(RegisterActivity.this, "Registering", Toast.LENGTH_LONG);
                registerToast.show();

                RegisterRequest registerRequest = new RegisterRequest(
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        username.getText().toString(),
                        password.getText().toString(),
                        url.getText().toString());
                RegisterTask registerTask = new RegisterTask(presenter, RegisterActivity.this);
                registerTask.execute(registerRequest);
            }
        });
    }

    @Override
    public void registerSuccessful(RegisterResponse registerResponse) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, registerResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, registerResponse.getAuthToken());

        registerToast.cancel();
        startActivity(intent);
    }

    @Override
    public void registerUnsuccessful(RegisterResponse registerResponse) {
        Toast.makeText(this, "Failed to register. " + registerResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to register because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
