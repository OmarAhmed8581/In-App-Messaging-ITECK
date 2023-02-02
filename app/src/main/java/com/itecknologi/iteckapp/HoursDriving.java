package com.itecknologi.iteckapp;

public class HoursDriving {

    int Business;
    int Personal;


    public HoursDriving(int business, int personal) {
        Business = business;
        Personal = personal;
    }

    public int getBusiness() {
        return Business;
    }

    public void setBusiness(int business) {
        Business = business;
    }

    public int getPersonal() {
        return Personal;
    }

    public void setPersonal(int personal) {
        Personal = personal;
    }
}
