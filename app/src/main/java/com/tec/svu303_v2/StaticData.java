package com.tec.svu303_v2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.tec.svu303_v2.sqlite_database.models.DatabaseManager;


public class StaticData {

    @SuppressLint("StaticFieldLeak")
    private static StaticData basicData = null;
    private final static int defaultTheme = R.style.Theme_Svu303_v2;
    private final static String theme = "my theme";
    private final static String sound = "my sounds";
    private final static String user = "USER";
    private final static String test = "TEST";
    private Context context;
    private static DatabaseManager databaseManager;

    private StaticData() {
    }

    public static StaticData getInstance(Context context) {
        if (basicData == null) {
            basicData = new StaticData();
            databaseManager = new DatabaseManager(context, "svu303_v2", 1);
            basicData.context = context;
        }

        return basicData;
    }

    public int getMyTheme() {
        android.content.SharedPreferences pref = context
                .getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        return pref.getInt(theme, defaultTheme);
    }

    public void setMyTheme(int t) {
        android.content.SharedPreferences.Editor pref = context
                .getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE).edit();
        pref.putInt(theme, t);
        pref.apply();
    }

    public boolean getSound() {
        android.content.SharedPreferences pref = context
                .getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        return pref.getBoolean(sound, true);
    }

    public void setSound(boolean b) {
        android.content.SharedPreferences.Editor pref = context
                .getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE).edit();
        pref.putBoolean(sound, b);
        pref.apply();
    }

    public void playSound() {
        if (getSound()) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.sound);
            mediaPlayer.start();
        }
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public int getCurrentUserID() {
        android.content.SharedPreferences pref = context
                .getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        return pref.getInt(user, -1);
    }

    public void setCurrentUserID(int i) {
        android.content.SharedPreferences.Editor pref = context
                .getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE).edit();
        pref.putInt(user, i);
        pref.apply();
    }

    public boolean checkTests() {
        android.content.SharedPreferences pref = context
                .getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        return pref.getBoolean(test, false);
    }

    public void setCheckTests() {
        android.content.SharedPreferences.Editor pref = context
                .getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE).edit();
        pref.putBoolean(test, true);
        pref.apply();
    }

    public void setBackgroundColor(TextView textView, Context context) {
        textView.setBackgroundColor(ContextCompat.getColor(context, R.color.dark));
    }

    public void setBackgroundNoColor(TextView textView, Context context) {
        textView.setBackgroundColor(ContextCompat.getColor(context, R.color.light));
    }

    public void setText(Button btn, String text) {
        btn.setText(text);
    }

}
