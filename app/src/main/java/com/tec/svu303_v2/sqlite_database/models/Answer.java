package com.tec.svu303_v2.sqlite_database.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.io.Serializable;
import java.util.ArrayList;

public class Answer implements Serializable {
    private int id;
    private String title;
    private int questionId;
    private int status;

    public Answer(int id, String title, int questionId, int status) {
        this.id = id;
        this.title = title;
        this.questionId = questionId;
        this.status = status;
    }

    public Answer(String title, int questionId, int status) {
        this.title = title;
        this.questionId = questionId;
        this.status = status;
    }
    public Answer(int questionId, int answerId, int status) {
        this.id = answerId;
        this.questionId = questionId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getStatus() {
        return status;
    }

    public void add(DatabaseManager databaseManager) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        String query = "INSERT INTO answer(title, questionId, status)" +
                "VALUES('" + title + "', '" + questionId + "', '" + status + "' )";
        db.execSQL(query);
        db.close();
    }

    public static ArrayList<Answer> getAll(DatabaseManager databaseManager, int questionId) {
        SQLiteDatabase db = databaseManager.getReadableDatabase();
        String query = "SELECT * FROM answer WHERE questionId = '" + questionId + "' ";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        ArrayList<Answer> answers = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Answer answer = new Answer(cursor.getInt(0), cursor.getString(1),
                        cursor.getInt(2), cursor.getInt(3));
                answers.add(answer);
            } while (cursor.moveToNext());
            return answers;
        }
        return answers;
    }

    public static void deleteAnswer(DatabaseManager databaseManager, int id) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        String query = "DELETE FROM answer WHERE id = '" + id + "' ";
        db.execSQL(query);
        db.close();
    }

    public static void updateAnswer(DatabaseManager databaseManager, int id, String title, int status) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("status", status);
        db.update("answer", values, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
