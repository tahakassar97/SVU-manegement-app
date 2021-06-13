package com.tec.svu303_v2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.tec.svu303_v2.sqlite_database.models.Answer;
import com.tec.svu303_v2.sqlite_database.models.Question;
import com.tec.svu303_v2.sqlite_database.models.Test;

public class SplashScreenActivity extends AppCompatActivity {


    String testsTypes[] = new String[]{
            "Java", "SQL",
            "HTML", "CSS"
    };

    String questions1 =
            "To define a string variable in Java we write?";

    String questions2 =
            "How many data types in java?";

    String questions3 =
            "To read an input from terminal in Java we use?";

    String questions4 =
            "What is the output of: \n System.out.println(20%12);?";

    String questions5 =
            "We use .... to make a column increased automatically.";

    String questions6 =
            "The execution order in SQL is:";

    String questions7 =
            "We use * to select?";

    String questions8 =
            "How many types of normalization?";

    String questions9 =
            "What the meaning of HTML?";

    String questions10 =
            "The first tag we write in HTML page is?";

    String questions11 =
            "The tag p used to add?";

    String questions12 =
            "To add an image to HTML page we use?";

    String questions13 =
            "What the meaning of CSS?";

    String questions14 =
            "Color attribute used to change?";

    String questions15 =
            "Border attribute used to?";

    String questions16 = "To change background color we use?";

    String answers1[] = new String[]{
            "str", "String", "string", "s",
    };

    String answers2[] = new String[]{
            "One", "Eight", "Six", "Four",
    };

    String answers3[] = new String[]{
            "Input Class", "Scanner Class", "We Cannot do that", "cin",
    };

    String answers4[] = new String[]{
            "2", "8", "1", "Error",
    };

    String answers5[] = new String[]{
            "AUTO", "AUTOINCREMENT", "AUTOINCREASE", "INCREMENT",
    };

    String answers6[] = new String[]{
            "SELECT FROM WHERE", "FROM WHERE SELECT", "WHERE FROM SELECT", "SELECT WHERE FROM",
    };

    String answers7[] = new String[]{
            "First Column", "All Columns", "Last Column", "Second Column",
    };

    String answers8[] = new String[]{
            "Three", "Four", "Five", "Six",
    };

    String answers9[] = new String[]{
            "Hyper Text Mean Language", "Hyper Text Markup Language", "Hyper Typography Markup Language",
            "H1 Text Markup Language",
    };

    String answers10[] = new String[]{
            "body", "html", "head", "title",
    };

    String answers11[] = new String[]{
            "A paragraph", "A big paragraph", "A small paragraph", "An image",
    };

    String answers12[] = new String[]{
            "p tag", "img tag", "h5 tag", "input tag",
    };

    String answers13[] = new String[]{
            "Cascade Structured Sheet", "Cascade Style Sheet", "Cascade Structured Simple",
            "C Structured Sheet",
    };

    String answers14[] = new String[]{
            "Background color", "Text color", "Text size", "Text width",
    };

    String answers15[] = new String[]{
            "Left border", "Border", "Shadow", "New color",
    };

    String answers16[] = new String[]{
            "Color", "Background color", "Select", "Font Family"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StaticData staticData = StaticData.getInstance(this);
        setTheme(staticData.getMyTheme());
        setContentView(R.layout.activity_splash_screen);

        if (!staticData.checkTests()) {
            for (String testsType : testsTypes) {
                Test test = new Test(testsType);
                test.add(staticData.getDatabaseManager());
            }
            
            Question question_1 = new Question(questions1, 1, 25);
            Question question_2 = new Question(questions2, 1, 25);
            Question question_3 = new Question(questions3, 1, 25);
            Question question_4 = new Question(questions4, 1, 25);
            question_1.add(staticData.getDatabaseManager());
            question_2.add(staticData.getDatabaseManager());
            question_3.add(staticData.getDatabaseManager());
            question_4.add(staticData.getDatabaseManager());

            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers1[i], 1, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers2[i], 2, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers3[i], 3, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers4[i], 4, status);
                answer.add(staticData.getDatabaseManager());
            }

            Question question_5 = new Question(questions5, 2, 25);
            Question question_6 = new Question(questions6, 2, 25);
            Question question_7 = new Question(questions7, 2, 25);
            Question question_8 = new Question(questions8, 2, 25);
            question_5.add(staticData.getDatabaseManager());
            question_6.add(staticData.getDatabaseManager());
            question_7.add(staticData.getDatabaseManager());
            question_8.add(staticData.getDatabaseManager());

            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers5[i], 5, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers6[i], 6, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers7[i], 7, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers8[i], 8, status);
                answer.add(staticData.getDatabaseManager());
            }

            Question question_9 = new Question(questions9, 3, 25);
            Question question_10 = new Question(questions10, 3, 25);
            Question question_11 = new Question(questions11, 3, 25);
            Question question_12 = new Question(questions12, 3, 25);
            question_9.add(staticData.getDatabaseManager());
            question_10.add(staticData.getDatabaseManager());
            question_11.add(staticData.getDatabaseManager());
            question_12.add(staticData.getDatabaseManager());

            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers9[i], 9, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers10[i], 10, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers11[i], 11, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers12[i], 12, status);
                answer.add(staticData.getDatabaseManager());
            }

            Question question_13 = new Question(questions13, 4, 25);
            Question question_14 = new Question(questions14, 4, 25);
            Question question_15 = new Question(questions15, 4, 25);
            Question question_16 = new Question(questions16, 4, 25);
            question_13.add(staticData.getDatabaseManager());
            question_14.add(staticData.getDatabaseManager());
            question_15.add(staticData.getDatabaseManager());
            question_16.add(staticData.getDatabaseManager());

            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers13[i], 13, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers14[i], 14, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers15[i], 15, status);
                answer.add(staticData.getDatabaseManager());
            }
            for (int i = 0; i < 4; i++) {
                int status = i == 1 ? 1 : 0;
                Answer answer = new Answer(answers16[i], 16, status);
                answer.add(staticData.getDatabaseManager());
            }

            staticData.setCheckTests();
        }

        new Handler().postDelayed(() -> {
            if (staticData.getCurrentUserID() != -1) {
                if (staticData.getCurrentUserID() == 0) {
                    startActivity(new Intent(SplashScreenActivity.this, AdminMainActivity.class));
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
            } else {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            }
            finish();
        }, 2000);
    }
}