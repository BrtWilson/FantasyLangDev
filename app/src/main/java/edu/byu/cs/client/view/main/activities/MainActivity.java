package edu.byu.cs.client.view.main.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shared.model.domain.User;

import edu.byu.cs.tweeter.R;

public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_USER_KEY = "CurrentUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if (user == null) throw new RuntimeException("User not passed to activity");

//        Button button = (Button)findViewById(R.id.goToMainButt);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                switchActivities();
//            }
//        });
    }

//    private void switchActivities() {
//        Intent switchActivityIntent = new Intent(this, MainActivity.class);
//        startActivity(switchActivityIntent);
//    }

    public static Intent newIntent(Context context, User user) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(CURRENT_USER_KEY, user);
        return intent;
    }
}
