package com.tec.svu303_v2.sqlite_database.models;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Student {
    private int id;
    private String name;
    private String username;
    private String password;

    public Student(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Student(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void register(DatabaseManager databaseManager) {
        SQLiteDatabase db = databaseManager.getWritableDatabase();
        String query = "INSERT INTO student(name, username, password)" +
                "VALUES('" + name + "', " +
                " '" + username + "', '" + password + "')";
        db.execSQL(query);
        db.close();
    }

    public static Student login(String username, String password, DatabaseManager databaseManager) {
        SQLiteDatabase db = databaseManager.getReadableDatabase();
        String query = "SELECT * FROM student WHERE username = '" + username + "' AND password = '" + password + "' ";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return new Student(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
        }
        return null;
    }

}
