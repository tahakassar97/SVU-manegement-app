package com.tec.svu303_v2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tec.svu303_v2.adapters.ExamAdapter;
import com.tec.svu303_v2.adapters.TestTypeAdapter;
import com.tec.svu303_v2.sqlite_database.models.Exam;
import com.tec.svu303_v2.sqlite_database.models.Test;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {

    StaticData staticData;
    RecyclerView examsRV;
    RecyclerView testsTypesRV;
    ArrayList<Exam> exams;
    ImageView settingsIV;
    ImageView logoutIV;
    Button examsBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticData = StaticData.getInstance(this);
        setTheme(staticData.getMyTheme());
        setContentView(R.layout.activity_admin_main);
        testsTypesRV = findViewById(R.id.testsTypesRV);
        logoutIV = findViewById(R.id.logoutIV);
        settingsIV = findViewById(R.id.settingsIV);
        examsBTN = findViewById(R.id.examsBTN);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        testsTypesRV.setLayoutManager(layoutManager);
        testsTypesRV.setHasFixedSize(true);
        ArrayList<Test> testsTypes;
        testsTypes = Test.getAll(staticData.getDatabaseManager());
        testsTypesRV.setAdapter(new TestTypeAdapter(testsTypes, this));

        logoutIV.setOnClickListener(v -> {
            staticData.playSound();
            new AlertDialog.Builder(this)
                    .setTitle("Logging Out")
                    .setMessage("Sure to logout?")
                    .setNegativeButton("No", (dialog, which) -> {
                        staticData.playSound();
                        dialog.dismiss();
                    })
                    .setPositiveButton("Yes", (dialog, which) -> {
                        staticData.playSound();
                        staticData.setCurrentUserID(-1);
                        startActivity(new Intent(AdminMainActivity.this, LoginActivity.class));
                        finish();
                    })
                    .create()
                    .show();
        });

        settingsIV.setOnClickListener(v -> {
            staticData.playSound();
            startActivity(new Intent(AdminMainActivity.this, AppSettingsActivity.class));
            finish();
        });

        examsBTN.setOnClickListener(v -> {
            staticData.playSound();
            exams = new ArrayList<>();
            exams = Exam.getExams(staticData.getDatabaseManager());
            if (exams.size() != 0) {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.exams_dialog);
                examsRV = dialog.findViewById(R.id.examsRV);
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false);
                examsRV.setLayoutManager(layoutManager1);
                examsRV.setHasFixedSize(true);
                examsRV.setAdapter(new ExamAdapter(this, exams));
                dialog.show();
            } else {
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        staticData.playSound();
    }


}