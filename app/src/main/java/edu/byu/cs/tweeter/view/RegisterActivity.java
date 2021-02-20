package edu.byu.cs.tweeter.view;

import androidx.appcompat.app.AppCompatActivity;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.RegisterTask;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements RegisterPresenter.View, RegisterTask.RegisterObserver {

    private static final String LOG_TAG = "RegisterActivity";
    private RegisterPresenter registerPresenter;
    private Toast registerInToast;

    private View.OnClickListener register = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          EditText userName_input = findViewById(R.id.userName_input);
          EditText password_input = findViewById(R.id.password_input);
          EditText firstName_input = findViewById(R.id.firstName_input);
          EditText lastName_input = findViewById(R.id.lastName_input);
          EditText imageURL_input = findViewById(R.id.imageURL_input);

          String userName = userName_input.getText().toString();
          String password = password_input.getText().toString();
          String firstName = firstName_input.getText().toString();
          String lastName = lastName_input.getText().toString();
          String imageURL = imageURL_input.getText().toString();

          RegisterRequest registerRequest = new RegisterRequest(userName, password, firstName, lastName, imageURL);
          RegisterTask registerTask = new RegisterTask(registerPresenter, RegisterActivity.this);
          registerTask.execute(registerRequest);
        }
    };

    private View.OnClickListener signIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signIn();
        }
    };

    public void signIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenter(this);

        findViewById(R.id.RegisterButton).setOnClickListener(register);
        findViewById(R.id.RegisterButton).setOnClickListener(signIn);
    }

    @Override
    public void registerSuccess(RegisterResponse registerResponse){
        Toast.makeText(this, "successful", Toast.LENGTH_LONG).show();
    }

    @Override
    public void registerFail(RegisterResponse registerResponse){
        Toast.makeText(this, "successful", Toast.LENGTH_LONG).show();
    }
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to register because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}