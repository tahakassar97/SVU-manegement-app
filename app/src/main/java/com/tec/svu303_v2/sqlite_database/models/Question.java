package com.tec.svu303_v2.sqlite_database.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private int id;
    private String title;
    private int testId;
    private int grade;

    public Question(int id, String title, int testId, int grade) {
        this.id = id;
        this.title = title;
        this.testId = testId;
        this.grade = grade;
    }

    public Question(String title, int testId, int grade) {
        this.title = title;
        this.testId = testId;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getTestId() {
        return testId;
    }

    public int getGrade() {
        return grade;
    }

    public void add(DatabaseManager databaseManager) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        String query = "INSERT INTO question(title, testId, grade)" +
                "VALUES('" + title + "', '" + testId + "', '" + grade + "' )";
        db.execSQL(query);
        db.close();
    }

    public static ArrayList<Question> getAll(DatabaseManager databaseManager, int testId) {
        SQLiteDatabase db = databaseManager.getReadableDatabase();
        String query = "SELECT * FROM question WHERE testId = '" + testId + "' ";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        ArrayList<Question> questions = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question(cursor.getInt(0), cursor.getString(1),
                        cursor.getInt(2), cursor.getInt(3));
                questions.add(question);
            } while (cursor.moveToNext());
            return questions;
        }
        return questions;
    }

    public static ArrayList<Answer> questionAnswers(DatabaseManager databaseManager,
                                                    int questionId) {
        SQLiteDatabase db = databaseManager.getReadableDatabase();
//        String query = "SELECT question.id AS questionId, question.title AS questionTitle," +
//                " grade, answer.id AS answerId, answer.title AS answerTitle, status " +
//                " FROM question INNER JOIN answer ON question.id = answer.questionId" +
//                " WHERE testId = '" + testId + "' AND questionId = '" + questionId + "' ";
        String query = "SELECT * FROM answer WHERE questionId = '" + questionId + "' ";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        ArrayList<Answer> answers = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Answer answer = new Answer(cursor.getInt(0),
                        cursor.getString(1), cursor.getInt(2),
                        cursor.getInt(3));
                answers.add(answer);
            } while (cursor.moveToNext());
            return answers;
        }
        return answers;
    }

    public static void deleteQuestion(DatabaseManager databaseManager, int id) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        String query = "DELETE FROM question WHERE id = '" + id + "' ";
        db.execSQL(query);
        db.close();
    }

    public static void updateQuestion(DatabaseManager databaseManager, int id, String title, int grade) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("grade", grade);
        db.update("question", values, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
