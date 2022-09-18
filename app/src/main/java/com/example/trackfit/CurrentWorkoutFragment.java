package com.example.trackfit;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;


public class CurrentWorkoutFragment extends Fragment implements View.OnClickListener, SensorEventListener, OnMapReadyCallback {
    private static final float METERS_TO_MILES = 0.000621371f;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private int seconds = 0;
    private float stepsTaken = 0f;
    private float prevSteps = -1f;
    private float distanceTraveled = 0f;
    private Location prevLocation = null;

    private SensorManager sensorManager;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private Button stopWorkoutButton;
    private TextView durationTextView;
    private TextView currentDistanceTextView;

    private GoogleMap mGoogleMap;
    private MapView mapView;
    private Marker marker;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;

    private final LatLng initialLocation = new LatLng(43, -89);

    public CurrentWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_workout, container, false);
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        stopWorkoutButton = (Button) view.findViewById(R.id.saveWorkoutButton);
        durationTextView = (TextView) view.findViewById(R.id.totalElapsedTime);
        currentDistanceTextView = (TextView) view.findViewById(R.id.totalDistance);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        stopWorkoutButton.setOnClickListener(this);

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 1);
        }

        // Getting the step sensor
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);

        runTimer();
        runDistance();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setMaxZoomPreference(17);
        mGoogleMap.setMinZoomPreference(17);

        int permission = ActivityCompat.checkSelfPermission(getContext().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }else{
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {}
            };
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.FUSED_PROVIDER);

        if (location != null) {
            Double Lat = location.getLatitude();
            Double Lon = location.getLongitude();
            LatLng position = new LatLng(Lat, Lon);
            System.out.println(Lat);
            System.out.println(Lon);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("time", seconds);
        bundle.putFloat("distance", distanceTraveled);
        bundle.putFloat("steps", stepsTaken);

        EndWorkoutDialogFragment endDialog = new EndWorkoutDialogFragment();
        endDialog.setArguments(bundle);

        endDialog.show(getActivity().getSupportFragmentManager(), "end workout dialog");
    }

    // Don't need to implement for steps in this project
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    // When the sensor detects a step, update the steps taken value
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (prevSteps < 0) {
            prevSteps = event.values[0];
        } else {
            stepsTaken = event.values[0] - prevSteps;
        }
    }

    // Starts the timer and updated UI every second
    private void runTimer() {
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                durationTextView.setText(time);
                seconds++;

                handler.postDelayed(this, 1000);
            }
        });
    }

    private void runDistance() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (prevLocation == null) {
                    prevLocation = location;
                } else {
                    distanceTraveled +=  location.distanceTo(prevLocation) * METERS_TO_MILES;
                    prevLocation = location;
                    currentDistanceTextView.setText(df.format(distanceTraveled));

                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override
            public void onProviderEnabled(String s) {}
            @Override
            public void onProviderDisabled(String s) {}
        };

        if (Build.VERSION.SDK_INT < 23) {
        } else {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.FUSED_PROVIDER, 1000, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.FUSED_PROVIDER);
                if (location != null) {
                    prevLocation = location;
                }
            }
        }
    }


}