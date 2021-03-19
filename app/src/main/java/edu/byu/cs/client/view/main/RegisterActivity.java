package edu.byu.cs.client.view.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import edu.byu.cs.client.R;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;

import java.io.ByteArrayOutputStream;

import edu.byu.cs.client.presenter.RegisterPresenter;
import edu.byu.cs.client.view.asyncTasks.RegisterTask;

public class RegisterActivity extends AppCompatActivity implements RegisterPresenter.View, RegisterTask.Observer {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String LOG_TAG = "RegisterActivity";

    private RegisterPresenter presenter;
    private Toast registerToast;

    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText password;
    //private EditText url;
    private Button registerConfirm;

    //private byte [] imageBytes = null;
    private String encodedImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        presenter = new RegisterPresenter(this);

        firstName = (EditText) findViewById(R.id.register_firstname);
        lastName = (EditText) findViewById(R.id.register_lastname);
        username = (EditText) findViewById(R.id.register_username);
        password = (EditText) findViewById(R.id.register_password);
        //url = (EditText) findViewById(R.id.register_url);
        registerConfirm = findViewById(R.id.RegisterConfirm);


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerConfirm.setEnabled(!TextUtils.isEmpty(firstName.getText().toString()) &&
                        !TextUtils.isEmpty(lastName.getText().toString()) &&
                        !TextUtils.isEmpty(username.getText().toString()) &&
                        !TextUtils.isEmpty(password.getText().toString()));
                        //&& !TextUtils.isEmpty(url.getText().toString()));
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        firstName.addTextChangedListener(watcher);
        lastName.addTextChangedListener(watcher);
        username.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);
        //url.addTextChangedListener(watcher);

        Button photoButton = findViewById(R.id.profilePhotoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dispatchTakePictureIntent();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(RegisterActivity.this,"Could not open camera.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                registerToast = Toast.makeText(RegisterActivity.this, "Registering", Toast.LENGTH_LONG);
                registerToast.show();
                RegisterRequest registerRequest;
                registerRequest = new RegisterRequest(firstName.getText().toString(), lastName.getText().toString(),
                        username.getText().toString(), password.getText().toString(), encodedImage);
                RegisterTask registerTask = new RegisterTask(presenter, RegisterActivity.this);
                registerTask.execute(registerRequest);
            }
        });
    }

    /**
     * The callback method invoked upon successful capture of a photo
     * @param requestCode the request code sent to the ImageCapture intent
     * @param resultCode the result code received from the ImageCapture intent
     * @param data contains the captured image data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            //imageBytes = stream.toByteArray();
            encodedImage = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
            imageBitmap.recycle();
        }
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
