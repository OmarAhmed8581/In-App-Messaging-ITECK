package com.itecknologi.iteckapp;

import com.google.gson.annotations.SerializedName;

public class GetStatsModel {


    int DrivingScrore;
    Fuel Fuel;
    FuelCost FuelCost;
    HoursDriving HoursDriving;
    Mileage Mileage;
    boolean Success;

    public GetStatsModel(int drivingScrore, com.itecknologi.iteckapp.Fuel fuel, com.itecknologi.iteckapp.FuelCost fuelCost, com.itecknologi.iteckapp.HoursDriving hoursDriving, com.itecknologi.iteckapp.Mileage mileage, boolean success) {
        DrivingScrore = drivingScrore;
        Fuel = fuel;
        FuelCost = fuelCost;
        HoursDriving = hoursDriving;
        Mileage = mileage;
        Success = success;
    }

    public int getDrivingScrore() {
        return DrivingScrore;
    }

    public void setDrivingScrore(int drivingScrore) {
        DrivingScrore = drivingScrore;
    }

    public com.itecknologi.iteckapp.Fuel getFuel() {
        return Fuel;
    }

    public void setFuel(com.itecknologi.iteckapp.Fuel fuel) {
        Fuel = fuel;
    }

    public com.itecknologi.iteckapp.FuelCost getFuelCost() {
        return FuelCost;
    }

    public void setFuelCost(com.itecknologi.iteckapp.FuelCost fuelCost) {
        FuelCost = fuelCost;
    }

    public com.itecknologi.iteckapp.HoursDriving getHoursDriving() {
        return HoursDriving;
    }

    public void setHoursDriving(com.itecknologi.iteckapp.HoursDriving hoursDriving) {
        HoursDriving = hoursDriving;
    }

    public com.itecknologi.iteckapp.Mileage getMileage() {
        return Mileage;
    }

    public void setMileage(com.itecknologi.iteckapp.Mileage mileage) {
        Mileage = mileage;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}
