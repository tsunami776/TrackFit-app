package com.example.trackfit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class DeleteWorkoutDialogFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this workout?")
                .setPositiveButton("Delete Workout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new WorkoutFragment()).commit();
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
