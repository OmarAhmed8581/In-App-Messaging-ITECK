package com.itecknologi.iteckapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class get_vehicle_data_model {

    // string variables

    private String contact, Success, Name, apploginid;


    @SerializedName("Vehicle")
    private List<VehicleValue> Vehicle;

    public List<VehicleValue> getVehicle() {

        return Vehicle;
    }


    public get_vehicle_data_model(String contact) {
        //this.DeviceId=DeviceId;
        // this.Email = Email;
        // this.Contact = Contact;

        setcontact(contact);


    }


    public String getcontact() {
        return contact;
    }

    public void setcontact(String contact) {
        this.contact = contact;
    }

    public String getSuccess() {
        return Success;
    }

    public String getName() {
        return Name;
    }

    class VehicleValue {
        @SerializedName("VehReg")
        @Expose
        private String VehReg;
        @SerializedName("ObjectId")
        @Expose
        private String ObjectId;
        @SerializedName("VehicleId")
        @Expose
        private String VehicleId;

        public String getVehReg() {
            return VehReg;
        }

        public void setVehReg(String VehReg) {
            this.VehReg = VehReg;
        }

        public String getObjectId() {
            return ObjectId;
        }

        public void setObjectId(String ObjectId) {
            this.ObjectId = ObjectId;
        }

        public String getVehicleId() {
            return VehicleId;
        }

        public void setVehicleId(String VehicleId) {
            this.VehicleId = VehicleId;
        }


    }
}
