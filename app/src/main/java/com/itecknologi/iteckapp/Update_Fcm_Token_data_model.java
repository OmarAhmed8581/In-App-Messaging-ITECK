package com.itecknologi.iteckapp;

public class Update_Fcm_Token_data_model {

    // string variables

    private String deviceid, loginid, Success, message, fcmtoken;

    public Update_Fcm_Token_data_model(String loginid, String deviceid, String fcmtoken) {
        //this.DeviceId=DeviceId;
        // this.Email = Email;
        // this.Contact = Contact;

        setloginid(loginid);
        setdeviceid(deviceid);
        setfcmtoken(fcmtoken);


    }


    public String getmessage() {
        return message;
    }

    public void setloginid(String loginid) {
        this.loginid = loginid;
    }

    public void setdeviceid(String deviceid) {

        this.deviceid = deviceid;
    }

    public void setfcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }


}
