package com.tec.svu303_v2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tec.svu303_v2.sqlite_database.models.Answer;

import java.util.Objects;

public class AnswerManagementActivity extends AppCompatActivity {


    StaticData appData;
    TextView answerTV;
    EditText titleET;
    RadioButton trueRB;
    RadioButton falseRB;
    Button confirm;
    int questionId;
    Answer answer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appData = StaticData.getInstance(this);
        setTheme(appData.getMyTheme());
        setContentView(R.layout.activity_answer_management);
        answerTV = findViewById(R.id.answerTV);
        titleET = findViewById(R.id.answerTitleET);
        trueRB = findViewById(R.id.trueRB);
        falseRB = findViewById(R.id.falseRB);
        confirm = findViewById(R.id.confirmBTN);
        try {
            questionId = (int) getIntent().getSerializableExtra("questionId");
        } catch (Exception e) {
            answer = (Answer) getIntent().getSerializableExtra("answer");
            answerTV.setText("Edit Answer");
            titleET.setText(answer.getTitle());
            confirm.setText("Edit");
            if (answer.getStatus() == 0) falseRB.setChecked(true);
            else trueRB.setChecked(true);
        }

        confirm.setOnClickListener(v -> {
            appData.playSound();
            if (answer == null) {
                if (titleET.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please Enter All Information", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Answer answer;
                        if (trueRB.isChecked()) {
                            answer = new Answer(titleET.getText().toString().trim(), questionId, 1);
                        } else {
                            answer = new Answer(titleET.getText().toString().trim(), questionId, 0);
                        }
                        answer.add(appData.getDatabaseManager());
                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (titleET.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please Enter All Information", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int status = answer.getStatus();
                        if (trueRB.isChecked()) {
                            status = 1;
                        }
                        Answer.updateAnswer(appData.getDatabaseManager(), answer.getId(), titleET.getText().toString().trim(),
                                status);
                        Toast.makeText(this, "Done, Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appData.playSound();
    }
}