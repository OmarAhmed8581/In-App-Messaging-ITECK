package com.itecknologi.iteckapp;

import org.w3c.dom.Comment;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Otp_check_API {

    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("Otp_check_data_model")

    //on below line we are creating a method to post our data.
    Call<Otp_check_data_model> createPost(@Body Otp_check_data_model otp_check_data_model);

    @FormUrlEncoded
    @POST("Otp_check_data_model")
    Call<Comment> createComment(@Field("login_id") String login_id, @Field("OTP") String OTP, @Field("device_id") String device_id);

    @FormUrlEncoded
    @POST("Otp_check_data_model")
    Call<Otp_check_data_model> createComment(@FieldMap Map<String, String> fields);


}
