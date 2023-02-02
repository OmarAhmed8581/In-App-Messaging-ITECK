package com.itecknologi.iteckapp;

public class ModelFreqPlaces {

    private String txtLocation;
    private String txtNoTimes;

    public ModelFreqPlaces(String txtLocation, String txtNoTimes) {
        this.txtLocation = txtLocation;
        this.txtNoTimes = txtNoTimes;
    }

    public String getTxtLocation() {
        return txtLocation;
    }

    public void setTxtLocation(String txtLocation) {
        this.txtLocation = txtLocation;
    }

    public String getTxtNoTimes() {
        return txtNoTimes;
    }

    public void setTxtNoTimes(String txtNoTimes) {
        this.txtNoTimes = txtNoTimes;
    }
}
