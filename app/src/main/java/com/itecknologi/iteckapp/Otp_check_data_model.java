package com.itecknologi.iteckapp;

public class Otp_check_data_model {


    // string variables

    private String OTP, device_id, login_id, Success, Message;

    public Otp_check_data_model(String login_id, String OTP, String device_id) {
        //this.DeviceId=DeviceId;
        // this.Email = Email;
        // this.Contact = Contact;

        setlogin_id(login_id);
        setOTP(OTP);
        setdevice_id(device_id);


    }


    public String getlogin_id() {
        return login_id;
    }

    public String getSuccess() {
        return Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setlogin_id(String login_id) {
        this.login_id = login_id;
    }

    public void setOTP(String OTP) {

        this.OTP = OTP;
    }

    public void setdevice_id(String device_id) {
        this.device_id = device_id;
    }

}
