package com.example.trackfit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class ClearAllWorkoutDialogFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Clear")
                .setMessage("Are you sure you want to clear all previous workouts?")
                .setPositiveButton("Clear Workouts", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Context context = getActivity().getApplicationContext();
                        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("WorkoutsDB", Context.MODE_PRIVATE,null);
                        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
                        dbHelper.deleteWorkout();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new PreviousWorkoutFragment()).commit();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }
}
