package com.example.trackfit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBHelper {
    SQLiteDatabase sqLiteDatabase;

    public DBHelper (SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createTable() {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS workouts" +
                "(id INTEGER PRIMARY KEY, date TEXT, duration TEXT, distance TEXT, pace TEXT, calories TEXT, steps TEXT)");
    }

    public ArrayList<Workout> readWorkout() {
        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * from workouts"), null);

        int dateIndex = c.getColumnIndex("date");
        int durationIndex = c.getColumnIndex("duration");
        int distanceIndex = c.getColumnIndex("distance");
        int paceIndex = c.getColumnIndex("pace");
        int caloriesIndex = c.getColumnIndex("calories");
        int stepsIndex = c.getColumnIndex("steps");

        c.moveToFirst();

        ArrayList<Workout> workoutList = new ArrayList<>();

        while (!c.isAfterLast()) {
            String date = c.getString(dateIndex);
            String duration = c.getString(durationIndex);
            String distance = c.getString(distanceIndex);
            String pace = c.getString(paceIndex);
            String calories = c.getString(caloriesIndex);
            String steps = c.getString(stepsIndex);

            Workout workout = new Workout (date, duration, distance, pace, calories, steps);
            workoutList.add(workout);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();

        return workoutList;
    }

    public void saveWorkout(String date, String duration, String distance, String pace, String calories, String steps) {
        createTable();
        //sqLiteDatabase.execSQL(String.format("INSERT INTO workouts (date, duration, distance, pace, calories, steps) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                //date, duration, distance, pace, calories, steps));
        sqLiteDatabase.execSQL("INSERT INTO workouts (date, duration, distance, pace, calories, steps) VALUES " + String.format("('%s', '%s', '%s', '%s', '%s', '%s')", date, duration, distance, pace, calories, steps));
    }

//    public void updateWorkout(String date, String duration, String distance, String pace, String calories, String steps) {
//        createTable();
//        sqLiteDatabase.execSQL(String.format("UPDATE workouts set date = '%s', distance = '%s', calories = '%s', steps = '%s' where duration = '%s'",
//                date, distance, pace, calories, steps, duration));
//    }

    public void deleteWorkout() {
        createTable();
        //sqLiteDatabase.execSQL("DELETE FROM workouts WHERE id=" + String.format(("%s"), id));
        sqLiteDatabase.execSQL("DELETE FROM workouts");
    }
}
