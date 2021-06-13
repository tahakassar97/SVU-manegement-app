package com.tec.svu303_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tec.svu303_v2.sqlite_database.models.Student;

public class LoginActivity extends AppCompatActivity {

    StaticData staticData;
    EditText usernameET;
    EditText passwordET;
    Button loginBTN;
    CheckBox isAdmin;
    TextView newAccountTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticData = StaticData.getInstance(this);
        setTheme(staticData.getMyTheme());
        setContentView(R.layout.activity_login);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        loginBTN = findViewById(R.id.loginBTN);
        isAdmin = findViewById(R.id.adminCB);
        newAccountTV = findViewById(R.id.needRegisterTV);

        newAccountTV.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        loginBTN.setOnClickListener(v -> {
            staticData.playSound();
            if (usernameET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Username And Password", Toast.LENGTH_SHORT).show();
            } else {
                if (!isAdmin.isChecked()) {
                    Student student =
                            Student.login(usernameET.getText().toString().trim(), passwordET.getText().toString().trim(),
                                    staticData.getDatabaseManager());
                    if (student != null) {
                        staticData.setCurrentUserID(student.getId());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Please Check Your Information",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (usernameET.getText().toString().trim().equals("admin@303") &&
                            passwordET.getText().toString().trim().equals("admin0000")) {
                        staticData.setCurrentUserID(0);
                        startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Please Check Your Information",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        staticData.playSound();
    }
}