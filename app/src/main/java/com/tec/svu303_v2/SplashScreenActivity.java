package com.tec.svu303_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StaticData staticData = StaticData.getInstance(this);
        setTheme(staticData.getMyTheme());
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            if (staticData.getCurrentUserID() != -1) {
                if (staticData.getCurrentUserID() == 0) {
                    startActivity(new Intent(SplashScreenActivity.this, AdminMainActivity.class));
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
            } else {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            }
            finish();
        }, 2000);
    }
}