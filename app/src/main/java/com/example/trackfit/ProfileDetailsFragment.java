package com.example.trackfit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileDetailsFragment extends Fragment {
    private int goalDays;
    private float goalDistance;
    private int goalCalories;
    private EditText days;
    private EditText distance;
    private EditText calories;
    public ProfileDetailsFragment() {
    }
    public int getGoalDays(){//going to be called when you decide whether to save or delete workout
        return this.goalDays;
    }
    public float getGoalDistance(){//going to be called when you decide whether to save or delete workout
        return this.goalDistance;
    }
    public int getGoalCalories(){//going to be called when you decide whether to save or delete workout
        return this.goalCalories;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_details, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.trackfit", Context.MODE_PRIVATE);
        EditText fName = (EditText) view.findViewById(R.id.editFirstNameProfileDetails) ;
        fName.setText(sharedPreferences.getString("FirstName", ""));
        EditText lName = (EditText) view.findViewById(R.id.editLastNameProfileDetails) ;
        lName.setText(sharedPreferences.getString("LastName", ""));
        EditText dob = (EditText) view.findViewById(R.id.editDOBProfileDetails) ;
        dob.setText(sharedPreferences.getString("dateOfBirth", ""));
        EditText hF = (EditText) view.findViewById(R.id.editHeightFeetProfileDetails) ;
        hF.setText(sharedPreferences.getString("heightFeet", ""));
        EditText hI = (EditText) view.findViewById(R.id.editHeightInchesProfileDetails) ;
        hI.setText(sharedPreferences.getString("heightInches", ""));
        EditText w = (EditText) view.findViewById(R.id.editWeightProfileDetails) ;
        w.setText(sharedPreferences.getString("Weight", ""));

        days = (EditText) view.findViewById(R.id.editDaysGoalProfileDetails) ;
        days.setText(sharedPreferences.getString("goalDays", ""));
        this.goalDays = Integer.parseInt(days.getText().toString());
        distance = (EditText) view.findViewById(R.id.editDistanceGoalProfileDetails) ;
        distance.setText(sharedPreferences.getString("goalDistance", ""));
        this.goalDistance = Float.parseFloat(distance.getText().toString());
        calories = (EditText) view.findViewById(R.id.editCaloriesGoalProfileDetails) ;
        calories.setText(sharedPreferences.getString("goalCalories", ""));
        this.goalCalories = Integer.parseInt(calories.getText().toString());

        return view;
    }
}