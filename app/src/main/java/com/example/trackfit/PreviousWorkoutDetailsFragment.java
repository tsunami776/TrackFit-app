package com.example.trackfit;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PreviousWorkoutDetailsFragment extends Fragment implements View.OnClickListener{
    private int pos = -1;

    public PreviousWorkoutDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_previous_workout_details, container, false);
        Button backButton = (Button)view.findViewById(R.id.previousWorkoutBackButton);
        backButton.setOnClickListener(this);

        // get data from another fragment
        Bundle bundle = this.getArguments();
        pos = bundle.getInt("position");

        // initialize db
        Context context = getActivity().getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("WorkoutsDB", Context.MODE_PRIVATE,null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        ArrayList<Workout> userWorkouts = new ArrayList<>();
        userWorkouts = dbHelper.readWorkout();

        // set each textView
        TextView detailDateTextView = view.findViewById(R.id.detailDateText);
        detailDateTextView.setText(userWorkouts.get(pos).getDate());
        TextView detailDurationTextView = view.findViewById(R.id.detailDurationText);
        detailDurationTextView.setText(userWorkouts.get(pos).getDuration());
        TextView detailDistanceTextView = view.findViewById(R.id.detailDistanceText);
        detailDistanceTextView.setText(userWorkouts.get(pos).getDistance());
        TextView detailCaloriesTextView = view.findViewById(R.id.detailCaloriesText);
        detailCaloriesTextView.setText(userWorkouts.get(pos).getCalories());
        TextView detailPaceTextView = view.findViewById(R.id.detailPaceText);
        detailPaceTextView.setText(userWorkouts.get(pos).getPace());
        TextView detailStepsTextView = view.findViewById(R.id.detailStepsText);
        detailStepsTextView.setText(userWorkouts.get(pos).getSteps());

        return view;
    }

    // Back button
    @Override
    public void onClick(View v) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new PreviousWorkoutFragment()).commit();
    }
}