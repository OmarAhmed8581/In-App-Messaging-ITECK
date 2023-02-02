package com.itecknologi.iteckapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.type.DayOfWeek;


public class FreqDayModel implements Comparable<FreqDayModel> {
    private String day;
    private String noTimes;

    public FreqDayModel(String day, String noTimes) {
        this.day = day;
        this.noTimes = noTimes;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNoTimes() {
        return noTimes;
    }

    public void setNoTimes(String noTimes) {
        this.noTimes = noTimes;
    }

//    @Override
//    public int compare(FreqDayModel freqDayModel, FreqDayModel t1) {
//        DayOfWeek a = DayOfWeek.valueOf(freqDayModel.getDay());
//        DayOfWeek a1 = DayOfWeek.valueOf(t1.getDay());
//        return Integer.compare(a.getNumber(),a1.getNumber());
//    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int compareTo(FreqDayModel freqDayModel) {


        DayOfWeek a = DayOfWeek.valueOf(this.getDay());
        DayOfWeek a1 = DayOfWeek.valueOf(freqDayModel.getDay());
//
        return Integer.compare(a.getNumber(), a1.getNumber());


    }


}
