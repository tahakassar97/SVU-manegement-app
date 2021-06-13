package com.tec.svu303_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tec.svu303_v2.adapters.QuestionAdapter;
import com.tec.svu303_v2.sqlite_database.models.Question;

import java.util.ArrayList;
import java.util.Objects;

public class QuestionsManagementActivity extends AppCompatActivity {

    StaticData staticData;
    Button confirmAdd;
    EditText questionTitle;
    EditText gradeET;
    RecyclerView questionsRV;
    ArrayList<Question> questions;
    int testId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticData = StaticData.getInstance(this);
        setTheme(staticData.getMyTheme());
        setContentView(R.layout.activity_questions_management);
        confirmAdd = findViewById(R.id.confirmBTN);
        questionTitle = findViewById(R.id.questionTitleET);
        questionsRV = findViewById(R.id.questionsRV);
        gradeET = findViewById(R.id.gradeET);
        testId = (int) getIntent().getSerializableExtra("testId");
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        questionsRV.setLayoutManager(layoutManager);

        confirmAdd.setOnClickListener(v -> {
            staticData.playSound();
            if (questionTitle.getText().toString().trim().isEmpty() || gradeET.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Question And Grade", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Question question = new Question(questionTitle.getText().toString().trim(), testId,
                            Integer.parseInt(gradeET.getText().toString()));
                    question.add(staticData.getDatabaseManager());
                    getQuestions();
                    Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getQuestions() {
        questions = Question.getAll(staticData.getDatabaseManager(), testId);
        QuestionAdapter questionAdapter = new QuestionAdapter(questions, this);
        questionsRV.setAdapter(questionAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getQuestions();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        staticData.playSound();
    }


}