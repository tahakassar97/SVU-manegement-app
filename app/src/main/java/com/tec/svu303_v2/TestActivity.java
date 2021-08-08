package com.tec.svu303_v2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tec.svu303_v2.sqlite_database.models.Answer;
import com.tec.svu303_v2.sqlite_database.models.Exam;
import com.tec.svu303_v2.sqlite_database.models.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
    TextView questionMarkTV;
    ArrayList<Answer> studentAnswers;
    HashMap<Integer, Integer> mMap;
    Button endExamBTN;
    ImageView cancleAnswerIV;

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
        questionMarkTV = findViewById(R.id.questionMarkTV);
        question = findViewById(R.id.questionTitleTV);
        cancleAnswerIV = findViewById(R.id.cancleAnswer);
        answers = new ArrayList<>();
        studentAnswers = new ArrayList<>();
        testId = (int) getIntent().getSerializableExtra("testId");
        questions = Question.getAll(appData.getDatabaseManager(), testId);
        questions = checkAnswers(questions);
        questions = shuffleArray(questions);
        questionNumber = 0;
        questionId = questions.get(questionNumber).getId();
        mMap = new HashMap<>();

        initQuestion(questionId, questionNumber);

        next.setOnClickListener(v -> {
            appData.playSound();
            if (mMap.get(questionNumber) == null) {
                studentAnswers.add(questionNumber,
                        new Answer(questionId, -1, 0));
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
            try {
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
            } catch (Exception e) {

            }
        });
        ans2.setOnClickListener(v -> {
            try {

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
            } catch (Exception e) {

            }
        });
        ans3.setOnClickListener(v -> {
            try {

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
            } catch (Exception e) {

            }
        });
        ans4.setOnClickListener(v -> {
            try {

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
            } catch (Exception e) {

            }
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
            questionMarkTV.setText(String.valueOf(questions.get(questionNumber).getGrade()));
            question.setText(questions.get(questionNumber).getTitle());
            ans1.setText(answers.get(0).getTitle());
            ans2.setText(answers.get(1).getTitle());
            ans3.setText(answers.get(2).getTitle());
            ans4.setText(answers.get(3).getTitle());
            if (mMap.get(questionNumber) != null) {
                switch (questionNumber) {
                    case 1: {
                        appData.setBackgroundColor(ans1, this);
                        appData.setBackgroundNoColor(ans2, this);
                        appData.setBackgroundNoColor(ans3, this);
                        appData.setBackgroundNoColor(ans4, this);
                        break;
                    }
                    case 2: {
                        appData.setBackgroundColor(ans2, this);
                        appData.setBackgroundNoColor(ans1, this);
                        appData.setBackgroundNoColor(ans3, this);
                        appData.setBackgroundNoColor(ans4, this);
                        break;
                    }
                    case 3: {
                        appData.setBackgroundNoColor(ans2, this);
                        appData.setBackgroundNoColor(ans1, this);
                        appData.setBackgroundNoColor(ans4, this);
                        appData.setBackgroundColor(ans3, this);
                        break;
                    }
                    case 4: {
                        appData.setBackgroundNoColor(ans2, this);
                        appData.setBackgroundNoColor(ans3, this);
                        appData.setBackgroundNoColor(ans1, this);
                        appData.setBackgroundColor(ans4, this);
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }

        cancleAnswerIV.setOnClickListener(v -> {
            appData.playSound();
            try {
                studentAnswers.remove(questionNumber);
            } catch (Exception e) {

            }
            studentAnswers.add(questionNumber,
                    new Answer(questionId, answers.get(0).getId(), answers.get(0).getStatus()));
            mMap.put(questionNumber, 0);
            appData.setBackgroundNoColor(ans1, this);
            appData.setBackgroundNoColor(ans2, this);
            appData.setBackgroundNoColor(ans3, this);
            appData.setBackgroundNoColor(ans4, this);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appData.playSound();
    }

    private static ArrayList<Question> shuffleArray(ArrayList<Question> array) {
        int index;
        Question temp;
        Random random = new Random();
        for (int i = array.size() - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array.get(index);
            array.set(index, array.get(i));
            array.set(i, temp);
        }
        return array;
    }

    private ArrayList<Question> checkAnswers(ArrayList<Question> questions) {
        for (Question question : questions) {
            answers = Question.questionAnswers(appData.getDatabaseManager(), question.getId());
            if (answers.size() == 0) {
                questions.remove(question);
            }
        }
        return questions;
    }
}