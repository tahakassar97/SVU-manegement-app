package com.tec.svu303_v2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.svu303_v2.adapters.TestTypeAdapter;
import com.tec.svu303_v2.sqlite_database.models.Exam;
import com.tec.svu303_v2.sqlite_database.models.Test;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {

    StaticData staticData;
    RecyclerView examsRV;
    RecyclerView testsTypesRV;
    TextView testsTV;
    ArrayList<Exam> exams;
    ImageView settingsIV;
    ImageView logoutIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticData = StaticData.getInstance(this);
        setTheme(staticData.getMyTheme());
        setContentView(R.layout.activity_admin_main);
        examsRV = findViewById(R.id.examsRV);
        testsTypesRV = findViewById(R.id.testsTypesRV);
        testsTV = findViewById(R.id.testsTV);
        logoutIV = findViewById(R.id.logoutIV);
        settingsIV = findViewById(R.id.settingsIV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        testsTypesRV.setLayoutManager(layoutManager);
        testsTypesRV.setHasFixedSize(true);
        ArrayList<Test> testsTypes = new ArrayList<>();
//        testsTypes = Test.getAll(staticData.getDatabaseManager());
        testsTypesRV.setAdapter(new TestTypeAdapter(testsTypes, this));
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        examsRV.setLayoutManager(layoutManager1);
        examsRV.setHasFixedSize(true);
        exams = new ArrayList<>();
        exams = Exam.getExams(staticData.getDatabaseManager());
        logoutIV.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Logging Out")
                    .setMessage("Sure to logout?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        staticData.setCurrentUserID(-1);
                        startActivity(new Intent(AdminMainActivity.this, LoginActivity.class));
                        finish();
                    })
                    .create()
                    .show();
        });

        settingsIV.setOnClickListener(v -> {
            startActivity(new Intent(AdminMainActivity.this, AppSettingsActivity.class));
            finish();
        });
    }

}