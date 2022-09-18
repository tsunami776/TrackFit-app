package com.example.trackfit;

public class Workout {
    private String date;
    private String duration;
    private String distance;
    private String pace;
    private String calories;
    private String steps;

    public Workout(String date, String duration) {
        this.date = date;
        this.duration = duration;
    }

    public Workout(String date, String duration, String distance, String pace, String calories, String steps) {
        this.date = date;
        this.duration = duration;
        this.distance = distance;
        this.pace = pace;
        this.calories = calories;
        this.steps = steps;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPace() {
        return pace;
    }

    public void setPace(String pace) {
        this.pace = pace;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
