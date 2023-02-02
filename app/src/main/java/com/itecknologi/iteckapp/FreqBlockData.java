package com.itecknologi.iteckapp;

import java.util.List;

public class FreqBlockData {

    private String day;
    private String noTimes;
    private List<FreqListModel> freqListModels;

    public FreqBlockData(String day, String noTimes, List<FreqListModel> freqListModels) {
        this.day = day;
        this.noTimes = noTimes;
        this.freqListModels = freqListModels;
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

    public List<FreqListModel> getFreqListModels() {
        return freqListModels;
    }

    public void setFreqListModels(List<FreqListModel> freqListModels) {
        this.freqListModels = freqListModels;
    }
}
