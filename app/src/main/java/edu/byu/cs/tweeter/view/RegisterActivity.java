package edu.byu.cs.tweeter.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterPresenter.View, RegisterTask.Observer {

    private static final String LOG_TAG = "RegisterActivity";

    private RegisterPresenter presenter;
    private Toast registerToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new RegisterPresenter(this);

        Button registerConfirm = findViewById(R.id.RegisterConfirm);
        registerConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registerToast = Toast.makeText(RegisterActivity.this, "Registering", Toast.LENGTH_LONG);
                registerToast.show();
            }
        });
    }

    @Override
    public void registerSuccessful(RegisterResponse registerResponse) {
        // TODO: registerSuccessful
        Intent intent = new Intent(this, MainActivity.class);
        System.out.println("in register successful");
    }

    @Override
    public void registerUnsuccessful(RegisterResponse registerResponse) {
        // TODO: registerUnsuccessful
        System.out.println("in register unsuccessful");
    }

    @Override
    public void handleException(Exception exception) {
        // TODO: handleException
        System.out.println("in register handleEcception");
    }
}
