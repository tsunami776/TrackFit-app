package com.example.trackfit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class PreviousWorkoutFragment extends Fragment {
    //private ArrayList<Workout> workoutsList;
    private RecyclerView recyclerView;
    private PreviousWorkoutRecyclerAdapter.RecyclerViewClickListener listener;
    public static ArrayList<Workout> userWorkouts;

    public PreviousWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        userWorkouts = new ArrayList<>();

        // Get SQLiteDatabase instance
        Context context = getActivity().getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("WorkoutsDB", Context.MODE_PRIVATE,null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        userWorkouts = dbHelper.readWorkout();

        View view = inflater.inflate(R.layout.fragment_previous_workout, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        setAdapter();

        // Clear All Workouts Dialog
        Button clearButton = view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClearAllWorkoutDialogFragment clearAllDialog = new ClearAllWorkoutDialogFragment();
                clearAllDialog.show(getActivity().getSupportFragmentManager(), "Clear all workout dialog");
            }
        });

        return view;
    }

    private void setAdapter() {
        setOnClickListener();
        PreviousWorkoutRecyclerAdapter adapter = new PreviousWorkoutRecyclerAdapter(userWorkouts, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new PreviousWorkoutRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                // send data from fragment to fragment

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                PreviousWorkoutDetailsFragment previousWorkoutDetailsFragment = new PreviousWorkoutDetailsFragment();
                previousWorkoutDetailsFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, previousWorkoutDetailsFragment).commit();
            }

        };
    }

//    public void clearAll(View v) {
//        Bundle bundle = new Bundle();
//        bundle.putString("sourceLocation", "prevWorkoutFragment");
//        DeleteWorkoutDialogFragment deleteDialog = new DeleteWorkoutDialogFragment();
//        deleteDialog.setArguments(bundle);
//        deleteDialog.show(getActivity().getSupportFragmentManager(), "delete workout dialog");
//        Log.i("tag", "pressed");
//    }
}