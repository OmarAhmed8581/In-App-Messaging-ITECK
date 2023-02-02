package com.itecknologi.iteckapp;

public class DataModal {

    // string variables
    private String DeviceId;
    private String Email;
    private String Contact, Success, Message, FcmToken, apploginid, OTP;

   /* @SerializedName("DeviceId")
    @Expose
    private String DeviceId;
    @SerializedName("email")
    @Expose
    private String Email;
    @SerializedName("Contact")
    @Expose
    private String Contact;
    @SerializedName("Success")
    @Expose
    private String Success;

    @SerializedName("Message")
    @Expose
    private String Message;*/

    public DataModal(String DeviceId, String Email, String Contact, String FcmToken) {
        //this.DeviceId=DeviceId;
        // this.Email = Email;
        // this.Contact = Contact;
        setDeviceId(DeviceId);
        setEmail(Email);
        setContact(Contact);
        setFcmToken(FcmToken);

    }


    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String DeviceId) {
        this.DeviceId = DeviceId;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getFcmToken() {
        return FcmToken;
    }

    public void setFcmToken(String FcmToken) {
        this.FcmToken = FcmToken;
    }


    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String Success) {
        this.Success = Success;
    }


    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }


    /**
     * login id
     **/

    public String getapploginid() {
        return apploginid;
    }

    public void setapploginid(String apploginid) {
        this.apploginid = apploginid;
    }


}
