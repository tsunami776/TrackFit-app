package com.example.trackfit;

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

public class NewProfileActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;

    Button createProfileButton;
    EditText etFirstName;
    EditText etLastName;
    EditText etDOB;
    EditText etHeightFeet;
    EditText etHeightInches;
    EditText etWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.trackfit", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString("username", "").equals("")) {
           displayApp();
        } else {
            createProfileButton = findViewById(R.id.newProfileButton);
            createProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    etFirstName = findViewById(R.id.editFirstNameNewProfile);
                    etLastName = findViewById(R.id.editLastNameNewProfile);
                    etDOB = findViewById(R.id.editDOBNewProfile);
                    etHeightFeet = findViewById(R.id.editHeightFeetProfileDetails);
                    etHeightInches = findViewById(R.id.editHeightInchesProfileDetails);
                    etWeight = findViewById(R.id.editWeightProfileDetails);

                    String fName = etFirstName.getText().toString();
                    String lName = etLastName.getText().toString();
                    String dBirth = etDOB.getText().toString();
                    String hFeet = etHeightFeet.getText().toString();
                    String hInches = etHeightInches.getText().toString();
                    String weight = etWeight.getText().toString();

                    sharedPreferences.edit().putString("username", fName + lName).apply();
                    sharedPreferences.edit().putString("FirstName", fName).apply();
                    sharedPreferences.edit().putString("LastName", lName).apply();
                    sharedPreferences.edit().putString("dateOfBirth", dBirth).apply();
                    sharedPreferences.edit().putString("heightFeet", hFeet).apply();
                    sharedPreferences.edit().putString("heightInches", hInches ).apply();
                    sharedPreferences.edit().putString("Weight", weight).apply();
                    sharedPreferences.edit().putString("goalDistance", "30.0").apply();
                    sharedPreferences.edit().putString("goalDays", "5").apply();
                    sharedPreferences.edit().putString("goalCalories", "500").apply();
                    sharedPreferences.edit().putString("prevDistance", "0.0").apply();
                    sharedPreferences.edit().putString("prevDays", "0").apply();
                    sharedPreferences.edit().putString("prevCalories", "0").apply();
                    displayApp();
                }
            });
        }

    }

    public void displayApp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}