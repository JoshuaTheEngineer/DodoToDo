package com.joshuatheengineer.dodotodo.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.joshuatheengineer.dodotodo.R;
import com.joshuatheengineer.dodotodo.databinding.SettingsActivityBinding;
import com.joshuatheengineer.dodotodo.utilities.NotificationManager;

public class SettingsActivity extends AppCompatActivity {

    private SettingsActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ListPreference mListPreference = getPreferenceManager().findPreference("notification");
            mListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    final NotificationManager nManager = new NotificationManager(getContext(), getCustomMessage());
                    if(newValue.toString().equals("On")){
                        nManager.addNotification();
                    } else {
                        nManager.removeNotification();
                    }
                    return true;
                }
            });
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        private String getCustomMessage(){
            EditTextPreference mTextPreference = getPreferenceManager().findPreference("customize");
            String customMessage = "Ready to Do do some tasks!!!";
            if(mTextPreference.getText() != null || mTextPreference.getText().isEmpty() || mTextPreference.getText().equals("")){
                customMessage = mTextPreference.getText();
            }
            return customMessage;
        }
    }
}