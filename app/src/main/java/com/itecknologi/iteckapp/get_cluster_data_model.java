package com.itecknologi.iteckapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class get_cluster_data_model {
    private String object_id, veh_id, Location, X, Y, VehicleNo, Ignition, Speed, BatteryHealth, BatteryVolt, Ang, CustName;
    /* @SerializedName("GpsTime")
     private ArrayList<TimeValue> GpsTime;


     public ArrayList<TimeValue> getGpsTime() {

         return GpsTime;
     }*/
    HashMap<String, String> GpsTime;

    public get_cluster_data_model(String veh_id, String object_id) {
        //this.DeviceId=DeviceId;
        // this.Email = Email;
        // this.Contact = Contact;

        setveh_id(veh_id);
        setobject_id(object_id);


    }

    @SerializedName("GpsTime")
    public HashMap<String, String> getGpsTime() {

        return GpsTime;
    }


    public String getVeh_id() {
        return veh_id;
    }

    public void setveh_id(String veh_id) {
        this.veh_id = veh_id;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setobject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getLocation() {
        return Location;
    }

    public String getX() {
        return X;
    }

    public String getY() {
        return Y;
    }

    public String getSpeed() {
        return Speed;
    }

    public String getIgnition() {
        return Ignition;

    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public String getCustName() {
        return CustName;
    }

    public String getAng() {
        return Ang;
    }

    public String getBatteryHealth() {
        return BatteryHealth;
    }

    public String getBatteryVolt() {

        return BatteryVolt;

    }

   /* class TimeValue {
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("timezone_type")
        @Expose
        private String timezone_type;
        @SerializedName("timezone")
        @Expose
        private String timezone;

        public String getdate() {
            return date;
        }

        public void setdate(String date) {
            this.date = date;
        }

        public String gettimezone_type() {
            return timezone_type;
        }

        public void settimezone_type(String timezone_type) {
            this.timezone_type = timezone_type;
        }

        public String getimezone() {
            return timezone;
        }

        public void settimezone(String timezone) {
            this.timezone = timezone;
        }


    }*/


}
