package com.tec.svu303_v2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tec.svu303_v2.sqlite_database.models.Answer;
import com.tec.svu303_v2.sqlite_database.models.Exam;
import com.tec.svu303_v2.sqlite_database.models.Question;

import java.util.ArrayList;
import java.util.HashMap;

public class TestActivity extends AppCompatActivity {

    StaticData appData;
    int testId;
    ArrayList<Question> questions;
    ArrayList<Answer> answers;
    static int questionNumber;
    static int questionId;
    Button next;
    Button back;
    TextView question;
    TextView ans1;
    TextView ans2;
    TextView ans3;
    TextView ans4;
    ArrayList<Answer> studentAnswers;
    HashMap<Integer, Integer> mMap;
    Button endExamBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appData = StaticData.getInstance(this);
        setTheme(appData.getMyTheme());
        setContentView(R.layout.activity_test);
        endExamBTN = findViewById(R.id.endExam);
        ans1 = findViewById(R.id.firstAns);
        ans2 = findViewById(R.id.secondAns);
        ans3 = findViewById(R.id.thirdAns);
        ans4 = findViewById(R.id.fourthAns);
        next = findViewById(R.id.nextIB);
        back = findViewById(R.id.backIB);
        question = findViewById(R.id.questionTitleTV);
        answers = new ArrayList<>();
        studentAnswers = new ArrayList<>();
        testId = (int) getIntent().getSerializableExtra("testId");
        questions = Question.getAll(appData.getDatabaseManager(), testId);
        questionNumber = 0;
        questionId = questions.get(questionNumber).getId();
        mMap = new HashMap<>();

        initQuestion(questionId, questionNumber);

        next.setOnClickListener(v -> {
            appData.playSound();
            if (mMap.get(questionNumber) == null) {
                studentAnswers.add(questionNumber,
                        new Answer(questionId, answers.get(0).getId(), 0));
                mMap.put(questionNumber, 0);
            }
            appData.setBackgroundNoColor(ans1, this);
            appData.setBackgroundNoColor(ans2, this);
            appData.setBackgroundNoColor(ans3, this);
            appData.setBackgroundNoColor(ans4, this);
            questionNumber++;
            if (questionNumber == questions.size() - 1) {
                v.setVisibility(View.GONE);
            }
            back.setVisibility(View.VISIBLE);
            questionId = questions.get(questionNumber).getId();
            initQuestion(questionId, questionNumber);
        });

        back.setOnClickListener(v -> {
            appData.playSound();
            questionNumber--;
            if (questionNumber == 0) {
                v.setVisibility(View.GONE);
            }
            next.setVisibility(View.VISIBLE);
            questionId = questions.get(questionNumber).getId();
            initQuestion(questionId, questionNumber);
        });

        ans1.setOnClickListener(v -> {
            appData.playSound();
            appData.setBackgroundColor((TextView) v, this);
            appData.setBackgroundNoColor(ans2, this);
            appData.setBackgroundNoColor(ans3, this);
            appData.setBackgroundNoColor(ans4, this);
            try {
                studentAnswers.remove(questionNumber);
            } catch (Exception e) {

            }
            studentAnswers.add(questionNumber,
                    new Answer(questionId, answers.get(0).getId(), answers.get(0).getStatus()));
            mMap.put(questionNumber, 1);
        });
        ans2.setOnClickListener(v -> {
            appData.playSound();
            appData.setBackgroundColor((TextView) v, this);
            appData.setBackgroundNoColor(ans1, this);
            appData.setBackgroundNoColor(ans3, this);
            appData.setBackgroundNoColor(ans4, this);
            try {
                studentAnswers.remove(questionNumber);
            } catch (Exception e) {

            }
            studentAnswers.add(questionNumber,
                    new Answer(questionId, answers.get(1).getId(), answers.get(1).getStatus()));
            mMap.put(questionNumber, 2);
        });
        ans3.setOnClickListener(v -> {
            appData.playSound();
            appData.setBackgroundColor((TextView) v, this);
            appData.setBackgroundNoColor(ans2, this);
            appData.setBackgroundNoColor(ans1, this);
            appData.setBackgroundNoColor(ans4, this);
            try {
                studentAnswers.remove(questionNumber);
            } catch (Exception e) {

            }
            studentAnswers.add(questionNumber,
                    new Answer(questionId, answers.get(2).getId(), answers.get(2).getStatus()));
            mMap.put(questionNumber, 3);
        });
        ans4.setOnClickListener(v -> {
            appData.playSound();
            appData.setBackgroundNoColor(ans2, this);
            appData.setBackgroundNoColor(ans3, this);
            appData.setBackgroundNoColor(ans1, this);
            appData.setBackgroundColor((TextView) v, this);
            try {
                studentAnswers.remove(questionNumber);
            } catch (Exception e) {

            }
            studentAnswers.add(questionNumber,
                    new Answer(questionId, answers.get(3).getId(), answers.get(3).getStatus()));
            mMap.put(questionNumber, 4);
        });

        endExamBTN.setOnClickListener(v -> {
            appData.playSound();
            finishExam();
        });
    }

    private void finishExam() {
        new AlertDialog.Builder(this)
                .setMessage("Sure to end exam?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    appData.playSound();
                    int result = 0;
                    for (int i = 0; i < studentAnswers.size(); i++) {
                        if (studentAnswers.get(i).getStatus() == 1) {
                            int grade = questions.get(i).getGrade();
                            result += grade;
                        }
                    }
                    int finalResult = result;
                    new AlertDialog.Builder(this)
                            .setMessage("Your grade is: \n " + result)
                            .setPositiveButton("Confirm", (dialog1, which1) -> {
                                try {
                                    Exam exam = new Exam(appData.getCurrentUserID(), testId, finalResult);
                                    exam.add(appData.getDatabaseManager());
                                    finish();
                                } catch (Exception e) {
                                    Toast.makeText(this, "Something wrong happened",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create()
                            .show();
                })
                .create()
                .show();
    }

    private void initQuestion(int questionId, int questionNumber) {
        try {
            answers.clear();
            answers = Question.questionAnswers(appData.getDatabaseManager(), questionId);
            question.setText(questions.get(questionNumber).getTitle());
            ans1.setText(answers.get(0).getTitle());
            ans2.setText(answers.get(1).getTitle());
            ans3.setText(answers.get(2).getTitle());
            ans4.setText(answers.get(3).getTitle());
            if (mMap.get(questionNumber) != null) {
                if (mMap.get(questionNumber) == 1) {
                    appData.setBackgroundColor(ans1, this);
                    appData.setBackgroundNoColor(ans2, this);
                    appData.setBackgroundNoColor(ans3, this);
                    appData.setBackgroundNoColor(ans4, this);
                } else if (mMap.get(questionNumber) == 2) {
                    appData.setBackgroundColor(ans2, this);
                    appData.setBackgroundNoColor(ans1, this);
                    appData.setBackgroundNoColor(ans3, this);
                    appData.setBackgroundNoColor(ans4, this);
                } else if (mMap.get(questionNumber) == 3) {
                    appData.setBackgroundNoColor(ans2, this);
                    appData.setBackgroundNoColor(ans1, this);
                    appData.setBackgroundNoColor(ans4, this);
                    appData.setBackgroundColor(ans3, this);
                } else if (mMap.get(questionNumber) == 4) {
                    appData.setBackgroundNoColor(ans2, this);
                    appData.setBackgroundNoColor(ans3, this);
                    appData.setBackgroundNoColor(ans1, this);
                    appData.setBackgroundColor(ans4, this);
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error happened", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appData.playSound();
    }
}