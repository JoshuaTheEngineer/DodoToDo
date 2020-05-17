package com.joshuatheengineer.dodotodo.settings;

import android.app.PendingIntent;
import android.content.Intent;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.joshuatheengineer.dodotodo.R;
import com.joshuatheengineer.dodotodo.databinding.SettingsActivityBinding;
import com.joshuatheengineer.dodotodo.main.MainActivity;
import com.joshuatheengineer.dodotodo.utilities.Constants;

public class SettingsActivity extends AppCompatActivity {

    private SettingsActivityBinding binding;

    NotificationCompat.Builder builder;
    final String APP_TITLE = "Dodo To-Do";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.settings_activity);
//        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // How to respond to a tap from user
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder = new NotificationCompat.Builder(this, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.dodo_todo_logo_mini)
                .setContentTitle(APP_TITLE)
                .setContentText("This is some content text...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                //automatically removes the notification when the user taps on it
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(Constants.NOTIFICATIONS_KEY, builder.build());
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
                    Toast.makeText(getContext(), "This is the value : "+newValue.toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    // to display the notification ID
    private void addNotification(){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(Constants.NOTIFICATIONS_KEY, builder.build());
    }
}