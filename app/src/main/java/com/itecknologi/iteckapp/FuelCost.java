package com.itecknologi.iteckapp;

import com.google.gson.annotations.SerializedName;

public class FuelCost {
    @SerializedName("LastMonth")
    int LastMonth;

    @SerializedName("PastSeven")
    PastSeven PastSeven;

    @SerializedName("ThisMonth")
    double ThisMonth;

    @SerializedName("Yesterday")
    int Yesterday;

    public FuelCost(int lastMonth, com.itecknologi.iteckapp.PastSeven pastSeven, double thisMonth, int yesterday) {
        LastMonth = lastMonth;
        PastSeven = pastSeven;
        ThisMonth = thisMonth;
        Yesterday = yesterday;
    }

    public int getLastMonth() {
        return LastMonth;
    }

    public void setLastMonth(int lastMonth) {
        LastMonth = lastMonth;
    }

    public com.itecknologi.iteckapp.PastSeven getPastSeven() {
        return PastSeven;
    }

    public void setPastSeven(com.itecknologi.iteckapp.PastSeven pastSeven) {
        PastSeven = pastSeven;
    }

    public double getThisMonth() {
        return ThisMonth;
    }

    public void setThisMonth(double thisMonth) {
        ThisMonth = thisMonth;
    }

    public int getYesterday() {
        return Yesterday;
    }

    public void setYesterday(int yesterday) {
        Yesterday = yesterday;
    }
}
