package com.tec.svu303_v2.sqlite_database.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(Context context, String database_name, int version) {
        super(context, database_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStuTable = "CREATE TABLE student(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT," +
                " username TEXT, password TEXT, mobile TEXT)";
        db.execSQL(createStuTable);

        String createTestTable = "CREATE TABLE test(id INTEGER PRIMARY KEY," +
                " title TEXT)";
        db.execSQL(createTestTable);

        String createQuestionTable = "CREATE TABLE question(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, testId INTEGER, grade INTEGER, FOREIGN KEY(testId) REFERENCES test(id))";
        db.execSQL(createQuestionTable);

        String createAnswerTable = "CREATE TABLE answer(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, questionId INTEGER, status INTEGER, FOREIGN KEY(questionId) REFERENCES question(id))";
        db.execSQL(createAnswerTable);

        String createExamTable = "CREATE TABLE exam(studentId INTEGER, testId INTEGER," +
                " grade INTEGER DEFAULT 0, " +
                "FOREIGN KEY(studentId) " +
                "REFERENCES student(id), FOREIGN KEY(testId) REFERENCES test(id))";
        db.execSQL(createExamTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
