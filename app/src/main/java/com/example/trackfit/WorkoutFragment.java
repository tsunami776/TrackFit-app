package com.example.trackfit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.Math;

import java.util.ArrayList;
import java.util.Objects;

// Jiaxin added packages about volley to get the weather
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

// Jiaxin added packages about JSON
import java.util.Map;
import java.util.concurrent.Executor;

import org.json.*;
import org.w3c.dom.Text;
// import org.json.simple.JSONObject;

public class WorkoutFragment extends Fragment implements View.OnClickListener {
    private ArrayList<String> quotes = new ArrayList<String>();
    private TextView quotesTextView;
    private TextView weatherTextView;
    private TextView cityTextView;
    //private RequestQueue queue;
    private LocationManager locationManager;
    private LocationListener locationListener;
    //private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;

    public WorkoutFragment() {
        this.quotes.add("The Pain You Feel Today, Will Be The Strength You Feel Tomorrow");
        this.quotes.add("You Don't Have To Be Extreme, Just Consistent");
        quotes.add("All Progress Takes Place Outside Your Comfort Zone");
        quotes.add("Later = Never. Do It Now");
        quotes.add("A Little Progress Each Day Adds Up To Big Results");
        quotes.add("Be Somebody Nobody Thought You Could Be");
        quotes.add("When You Feel Like Quitting, Think About Why You Started");
        quotes.add("Find Your Fire");
        quotes.add("Push Through The Pain Every Single Day");
        quotes.add("Turn The Pain Into Power");
        quotes.add("If It's Easy You Are Doing It Wrong");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout, container, false);
        Button startWorkout = (Button) view.findViewById(R.id.startWorkoutButton);
        quotesTextView = (TextView) view.findViewById(R.id.quoteTextView);
        String toDisplayQuote = "";
        int randQuote = (int) (Math.random() * 9);
        toDisplayQuote = quotes.get(randQuote);
        quotesTextView.setText(toDisplayQuote);
        startWorkout.setOnClickListener(this);



        // Get the location first, default location is Madison
        String Lat = "43";
        String Lon = "-89";
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
                public void onLocationChanged(@NonNull Location location) {

                }
            };
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Lat = String.valueOf(location.getLatitude());
        Lon = String.valueOf(location.getLongitude());

        weatherTextView = (TextView) view.findViewById(R.id.weatherTextView) ;
        cityTextView = (TextView) view.findViewById(R.id.weatherLocationTextView);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://api.weatherbit.io/v2.0/current?lat=" + Lat + "&lon=" + Lon + "&key=secret";
        //api.openweathermap.org/data/2.5/weather?id=524901&appid=YOUR_API_KEY
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonString = response ;
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(jsonString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            JSONArray arr = obj.getJSONArray("data");
                            String temp = arr.getJSONObject(0).getString("temp");
                            Double C = Double.valueOf(temp);
                            Double F = C * 1.8 + 32;
                            temp = String.valueOf(F);

                            String city = arr.getJSONObject(0).getString("city_name");
                            JSONObject weather = arr.getJSONObject(0).getJSONObject("weather");
                            String textWeather = weather.getString("description");
                            //String pageName = obj.getJSONObject("temp").getString("pageName");
                            weatherTextView.setText("The temperature is: "+ temp + "°F\n The weather is: " + textWeather);
                            cityTextView.setText("City: " + city);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                weatherTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startWorkoutButton:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new CurrentWorkoutFragment()).commit();
                break;
        }
    }

    public void getTemp(){
//        weatherTextView = (TextView) view.findViewById(R.id.quoteTextView) ;
    }



}