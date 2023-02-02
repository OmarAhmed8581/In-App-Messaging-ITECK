package com.itecknologi.iteckapp;

public class SpeedLimitModel {
    String day;
    String location;
    String speed;

    public SpeedLimitModel(String day, String location, String speed) {
        this.day = day;
        this.location = location;
        this.speed = speed;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
