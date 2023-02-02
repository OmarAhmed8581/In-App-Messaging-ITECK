package com.itecknologi.iteckapp;

public class TripModel {

    private String time1;
    private String time2;

    private String location1;
    private String location2;

    private String distance;
    private String date;

    public TripModel(String time1, String time2, String location1, String location2, String distance, String date) {
        this.time1 = time1;
        this.time2 = time2;
        this.location1 = location1;
        this.location2 = location2;
        this.distance = distance;
        this.date = date;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getLocation1() {
        return location1;
    }

    public void setLocation1(String location1) {
        this.location1 = location1;
    }

    public String getLocation2() {
        return location2;
    }

    public void setLocation2(String location2) {
        this.location2 = location2;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
