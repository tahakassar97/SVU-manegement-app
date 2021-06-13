package com.tec.svu303_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tec.svu303_v2.sqlite_database.models.Student;

public class RegisterActivity extends AppCompatActivity {

    StaticData staticData;
    EditText usernameET;
    EditText passwordET;
    EditText nameET;
    Button registerBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticData = StaticData.getInstance(this);
        setTheme(staticData.getMyTheme());
        setContentView(R.layout.activity_register);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        nameET = findViewById(R.id.nameET);
        registerBTN = findViewById(R.id.registerBTN);

        registerBTN.setOnClickListener(v -> {
            staticData.playSound();
            if (usernameET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty() ||
                    nameET.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Student student = new Student(nameET.getText().toString().trim(), usernameET.getText().toString().trim(),
                            passwordET.getText().toString().trim());
                    student.register(staticData.getDatabaseManager());
                    Toast.makeText(this, "Done, Login to your account",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "An Error happened", Toast.LENGTH_SHORT).show();
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