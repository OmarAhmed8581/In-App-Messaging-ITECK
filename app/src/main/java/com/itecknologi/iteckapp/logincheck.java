package com.itecknologi.iteckapp;

public class logincheck {

    private String device_id, login_id, success;


    public logincheck(String login_id, String device_id) {

        setlogin_id(login_id);
        setdevice_id(device_id);


    }

    public String getlogin_id() {
        return login_id;
    }

    public void setlogin_id(String login_id) {
        this.login_id = login_id;
    }


    public String getdevice_id() {
        return device_id;
    }

    public void setdevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getsuccess() {
        return success;
    }

    public void setsuccess(String success) {
        this.success = success;
    }

}
