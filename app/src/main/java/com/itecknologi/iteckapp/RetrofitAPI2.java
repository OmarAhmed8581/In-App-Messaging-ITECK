package com.itecknologi.iteckapp;

import org.w3c.dom.Comment;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI2 {


    /**
     * mine check login
     **/


    @POST("logincheck")

    //on below line we are creating a method to post our data.
    Call<logincheck> createPost(@Body logincheck Logincheck);

    @FormUrlEncoded
    @POST("logincheck")
    Call<Comment> createComment(@Field("login_id") String login_id, @Field("device_id") String device_id);


    @FormUrlEncoded
    @POST("logincheck")
    Call<logincheck> createComment(@FieldMap Map<String, String> fields);

}


