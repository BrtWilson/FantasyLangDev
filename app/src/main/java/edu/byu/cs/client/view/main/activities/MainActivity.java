package edu.byu.cs.client.view.main.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.shared.model.domain.Language;
import com.example.shared.model.domain.User;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.List;

import edu.byu.cs.client.view.main.adapters.SectionsPagerAdapter;
import edu.byu.cs.client.view.main.fragments.LanguageFragment;
import edu.byu.cs.tweeter.R;

public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String LANGUAGE_KEY = "Language";

    private List<Language> languages;
    private Language currentLanguage = null;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("(Add Language)");

        user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if (user == null) throw new RuntimeException("User not passed to activity");

        languages = (List<Language>) getIntent().getSerializableExtra(LANGUAGE_KEY);
        if (languages != null) {
            currentLanguage = languages.get(0);
            getSupportActionBar().setTitle(currentLanguage.getLanguageName());
            setTabs(user, currentLanguage);
        }
        else {
            showLanguageDialog();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logoutMenu:
                finish();
                return true;
            case R.id.languageName:
                showLanguageDialog();
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

    private void setTabs(User user, Language language) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, language);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void showLanguageDialog() {
        FragmentManager manager = getSupportFragmentManager();
        LanguageFragment fragment;
        if (languages == null) fragment = LanguageFragment.newInstance(user.getUserName());
        else fragment = LanguageFragment.newInstance(languages);
        fragment.show(manager, "fragment_alert");
        fragment.setDialogResult(new LanguageFragment.OnDialogResult() {
            @Override
            public void finish(int result, List<Language> languages1) {
                languages = languages1;
                currentLanguage = languages.get(result);
                getSupportActionBar().setTitle(currentLanguage.getLanguageName());
                setTabs(user, currentLanguage);
            }
        });
    }

    public static Intent newIntent(Context context, User user, List<Language> lang) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(CURRENT_USER_KEY, user);
        intent.putExtra(LANGUAGE_KEY, (Serializable) lang);
        return intent;
    }

    @Override
    public void onBackPressed() {
        //
    }
}
