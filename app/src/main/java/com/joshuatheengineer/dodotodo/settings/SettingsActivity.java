package com.joshuatheengineer.dodotodo.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.joshuatheengineer.dodotodo.R;
import com.joshuatheengineer.dodotodo.databinding.SettingsActivityBinding;
import com.joshuatheengineer.dodotodo.utilities.OneTimeDelayWorker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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
                    if(newValue.toString().equals("On")){
                        Data.Builder data = new Data.Builder();
                        data.putString("msg", getCustomMessage());
                        Constraints constraints = new Constraints.Builder().build();
                        // will send a notification at set time
                        // the OneTimeWork request will trigger a periodic request to create notifications
                        // https://stackoverflow.com/questions/53048327/android-workmanager-how-to-delay-task
                        OneTimeWorkRequest dodoNoteRequest = new OneTimeWorkRequest.Builder(OneTimeDelayWorker.class)
                                .setInputData(data.build())
                                .setConstraints(constraints)
                                .setInitialDelay(getDelay(), TimeUnit.SECONDS)
                                .build();
                        WorkManager.getInstance(getContext()).enqueue(dodoNoteRequest);
                    } else {
                        WorkManager.getInstance(getContext()).cancelAllWork();
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

        private int getDelay(){
            ListPreference mTimePref = getPreferenceManager().findPreference("time");
            ListPreference mMeridianPref = getPreferenceManager().findPreference("meridian");
            // the first two are hours
            int prefHours = Integer.parseInt(mTimePref.getValue().substring(0,2));
            // if PM, you have to adjust it to 24 hour time
            if(mMeridianPref.getValue().equalsIgnoreCase("PM")) prefHours += 12;
            Calendar inst = Calendar.getInstance();
            int yourTime = inst.get(Calendar.HOUR_OF_DAY)*3600 + inst.get(Calendar.MINUTE)*60 + inst.get(Calendar.SECOND);
            int notifTime = prefHours*3600;
            int diff = notifTime - yourTime;
            int limit = 15 * 60; // 15 min limit due to WorkManager limit
            int day = 24 * 60 * 60;
            int delay;
            if(diff > 0 && diff <= limit) {
                // set notification for next day
                // due to limitations of Work Manager, 15 min limit
                delay = day + diff;
            } else if(diff > 0) {
                // set notification for that same day
                delay = diff;
            } else {
                // set notification for next day
                delay = day + diff;
            }
            return delay;
        }
    }
}