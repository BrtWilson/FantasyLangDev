package edu.byu.cs.client.view.main.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.shared.model.domain.User;
import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.client.view.main.adapters.SectionsPagerAdapter;
import edu.byu.cs.tweeter.R;

public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String LANGUAGE_KEY = "LanguageKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("");

        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if (user == null) throw new RuntimeException("User not passed to activity");

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu:
                finish();
                return true;
            case R.id.languageName:
                getSupportActionBar().setTitle("Hi");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public static Intent newIntent(Context context, User user, String lang) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(CURRENT_USER_KEY, user);
        intent.putExtra(LANGUAGE_KEY, lang);
        return intent;
    }

    @Override
    public void onBackPressed() {}
}
