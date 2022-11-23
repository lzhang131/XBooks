package com.example.books.ui;

import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.example.books.MainApplicationStart;
import com.example.books.R;

/**
 * SplashScreenActivity
 */
public class SplashScreenActivity extends AppCompatActivity {

    private final Handler mHideHandler = new Handler(Looper.myLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // delay 2s start Activity
        mHideHandler.postDelayed(() -> {
            Class toActivity = LoginActivity.class;
            if (null != MainApplicationStart.currentUser) {
                toActivity = HomeActivity.class;
            }
            Intent intent = new Intent(getApplicationContext(), toActivity);
            startActivity(intent);
            SplashScreenActivity.this.finish();// finish
        }, 2 * 1000);
    }

}