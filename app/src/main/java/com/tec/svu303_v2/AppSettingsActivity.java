package com.tec.svu303_v2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class AppSettingsActivity extends AppCompatActivity {
    private StaticData staticData;
    private Button save;
    private Button blueBTN, orangeBTN, burbleBTN;
    private int selectedTheme;

    private CheckBox turnOn;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticData = StaticData.getInstance(this);
        setTheme(staticData.getMyTheme());
        setContentView(R.layout.activity_app_settings);
        save = findViewById(R.id.saveBTN);
        blueBTN = findViewById(R.id.blueBTN);
        burbleBTN = findViewById(R.id.burbleBTN);
        orangeBTN = findViewById(R.id.orangeBTN);
        turnOn = findViewById(R.id.turnOnCB);
        selectedTheme = staticData.getMyTheme();
        if (staticData.getSound()) turnOn.setChecked(true);

        switch (selectedTheme) {
            case 2131755474: {
                staticData.setText(orangeBTN, "Selected");
                break;
            }
            case 2131755418: {
                staticData.setText(burbleBTN, "Selected");
                break;
            }
            case 2131755417: {
                staticData.setText(blueBTN, "Selected");
                break;
            }
        }

        save.setOnClickListener(v -> {
            staticData.playSound();
            staticData.setSound(turnOn.isChecked());
            staticData.setMyTheme(selectedTheme);
            finish();
            if (staticData.getCurrentUserID() == 0) {
                startActivity(new Intent(AppSettingsActivity.this, AdminMainActivity.class));
            } else {
                startActivity(new Intent(AppSettingsActivity.this, MainActivity.class));
            }
        });

        blueBTN.setOnClickListener(v ->
        {
            staticData.playSound();
            selectedTheme = R.style.Theme_Blue;
            staticData.setText((Button) v, "Selected");
            staticData.setText(burbleBTN, getResources().getString(R.string.burble));
            staticData.setText(orangeBTN, getResources().getString(R.string.orange));
        });

        burbleBTN.setOnClickListener(v ->
        {
            staticData.playSound();
            selectedTheme = R.style.Theme_Burble;
            staticData.setText(blueBTN, getResources().getString(R.string.blue));
            staticData.setText(burbleBTN, "Selected");
            staticData.setText(orangeBTN, getResources().getString(R.string.orange));
        });

        orangeBTN.setOnClickListener(v ->
        {
            staticData.playSound();
            selectedTheme = R.style.Theme_Svu303_v2;
            staticData.setText(blueBTN, getResources().getString(R.string.blue));
            staticData.setText(burbleBTN, getResources().getString(R.string.burble));
            staticData.setText(orangeBTN, "Selected");
        });

        turnOn.setOnClickListener(v -> staticData.playSound());

    }

    @Override
    public void onBackPressed() {
        staticData.playSound();
        finish();
        if (staticData.getCurrentUserID() == 0) {
            startActivity(new Intent(AppSettingsActivity.this, AdminMainActivity.class));
        } else {
            startActivity(new Intent(AppSettingsActivity.this, MainActivity.class));
        }
    }

}