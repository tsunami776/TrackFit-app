package com.example.trackfit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class EndWorkoutDialogFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = this.getArguments();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Workout End")
                .setMessage("Are you sure you want to end this workout?")
                .setPositiveButton("End Workout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        EndWorkoutFragment endWorkoutFragment = new EndWorkoutFragment();
                        endWorkoutFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, endWorkoutFragment).commit();
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
