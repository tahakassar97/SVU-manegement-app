package com.tec.svu303_v2.sqlite_database.models;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Test {

    private int id;
    private String title;

    public Test(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Test(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void add(DatabaseManager databaseManager) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        String query = "INSERT INTO test(title)" +
                "VALUES('" + title + "')";
        db.execSQL(query);
        db.close();
    }

    public static ArrayList<Test> getAll(DatabaseManager databaseManager) {
        SQLiteDatabase db = databaseManager.getReadableDatabase();
        String query = "SELECT * FROM test";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        ArrayList<Test> tests = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Test test = new Test(cursor.getInt(0), cursor.getString(1));
                tests.add(test);
            } while (cursor.moveToNext());
            return tests;
        }
        return null;
    }
}
