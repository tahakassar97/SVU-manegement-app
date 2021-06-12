package com.tec.svu303_v2.sqlite_database.models;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

public class Exam {
    private int studentId;
    private int testId;
    private int grad;
    private String testTitle;
    private String name;

    public Exam(int studentId, int testId, int grad) {
        this.studentId = studentId;
        this.testId = testId;
        this.grad = grad;
    }

    public Exam(int testId, int grad, String testTitle) {
        this.testId = testId;
        this.grad = grad;
        this.testTitle = testTitle;
    }

    public Exam(int testId, int grad, String testTitle, String name) {
        this.testId = testId;
        this.grad = grad;
        this.testTitle = testTitle;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getGrad() {
        return grad;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void add(DatabaseManager databaseManager) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        String query = "INSERT INTO exam(studentId, testId, grade)" +
                "VALUES('" + studentId + "', '" + testId + "', '" + grad + "' )";
        db.execSQL(query);
        db.close();
    }

    public static ArrayList<Exam> getStudentExams(DatabaseManager databaseManager, int studentId) {
        SQLiteDatabase db = databaseManager.getReadableDatabase();
        String query = "SELECT exam.testId AS testId, exam.grade AS grade," +
                " test.title AS title FROM exam" +
                " INNER JOIN test ON exam.testId = test.id" +
                " WHERE studentId = '" + studentId + "' ";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        ArrayList<Exam> exams = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Exam exam = new Exam(cursor.getInt(0),
                        cursor.getInt(1), cursor.getString(2));
                exams.add(exam);
            } while (cursor.moveToNext());
            return exams;
        }
        return exams;
    }

    public static ArrayList<Exam> getExams(DatabaseManager databaseManager) {
        SQLiteDatabase db = databaseManager.getReadableDatabase();
        String query = "SELECT exam.testId AS testId, exam.grade AS grade," +
                " test.title AS title, student.name AS name FROM exam" +
                " INNER JOIN test ON exam.testId = test.id INNER JOIN student ON exam.studentId = student.id";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        ArrayList<Exam> exams = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Exam exam = new Exam(cursor.getInt(0),
                        cursor.getInt(1), cursor.getString(2),
                        cursor.getString(3));
                exams.add(exam);
            } while (cursor.moveToNext());
            return exams;
        }
        return exams;
    }

}
