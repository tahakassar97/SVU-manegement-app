package com.tec.svu303_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tec.svu303_v2.adapters.AnswerAdapter;
import com.tec.svu303_v2.sqlite_database.models.Answer;
import com.tec.svu303_v2.sqlite_database.models.Question;

import java.util.ArrayList;
import java.util.Objects;

public class UpdateQuestionActivity extends AppCompatActivity {

    StaticData appData;
    EditText titleET;
    EditText gradeET;
    Button confirmBTN;
    Button addAnswerBTN;
    Question question;
    RecyclerView answersRV;
    AnswerAdapter answerAdapter;
    ArrayList<Answer> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appData = StaticData.getInstance(this);
        setTheme(appData.getMyTheme());
        setContentView(R.layout.activity_update_question);
        titleET = findViewById(R.id.questionTitleET);
        gradeET = findViewById(R.id.gradeET);
        confirmBTN = findViewById(R.id.confirmBTN);
        answersRV = findViewById(R.id.answersRV);
        addAnswerBTN = findViewById(R.id.addAnswerBTN);
        question = (Question) getIntent().getSerializableExtra("question");
        titleET.setText(question.getTitle());
        gradeET.setText(String.valueOf(question.getGrade()));

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        answersRV.setLayoutManager(layoutManager);

        answers = new ArrayList<>();

        confirmBTN.setOnClickListener(v -> {
            appData.playSound();
            if (titleET.getText().toString().isEmpty() || gradeET.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Question And Grade", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Question.updateQuestion(appData.getDatabaseManager(), question.getId(),
                            titleET.getText().toString().trim(),
                            Integer.parseInt(gradeET.getText().toString()));
                    Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addAnswerBTN.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateQuestionActivity.this, AnswerManagementActivity.class);
            intent.putExtra("questionId", question.getId());
            startActivity(intent);
        });
    }

    private void getAnswers() {
        answers = Answer.getAll(appData.getDatabaseManager(), question.getId());
        answerAdapter = new AnswerAdapter(this, answers);
        answersRV.setAdapter(new AnswerAdapter(this, answers));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAnswers();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appData.playSound();
    }
}