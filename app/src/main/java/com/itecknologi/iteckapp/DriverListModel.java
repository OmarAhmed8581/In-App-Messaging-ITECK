package com.itecknologi.iteckapp;

public class DriverListModel {

    private String title;
    private String desc;
    private String hours;

    public DriverListModel(String title, String desc, String hours) {
        this.title = title;
        this.desc = desc;
        this.hours = hours;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
