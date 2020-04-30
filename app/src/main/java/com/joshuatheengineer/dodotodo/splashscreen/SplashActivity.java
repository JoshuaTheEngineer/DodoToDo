package com.joshuatheengineer.dodotodo.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.joshuatheengineer.dodotodo.R;
import com.joshuatheengineer.dodotodo.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private final Long SPLASH_TIME_OUT = 3000L; // 3 seconds
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToMainActivity();
            }
        }, SPLASH_TIME_OUT);
    }

    private void goToMainActivity() {
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
}
