package com.itecknologi.iteckapp;

import org.w3c.dom.Comment;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface get_vehicle_reg {


    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("get_vehicle_data_model")

    //on below line we are creating a method to post our data.
    Call<get_vehicle_data_model> createPost(@Body get_vehicle_data_model Get_vehicle_data_model);

    @FormUrlEncoded
    @POST("get_vehicle_data_model")
    Call<Comment> createComment(@Field("contact") String contact);

    @FormUrlEncoded
    @POST("get_vehicle_data_model")
    Call<get_vehicle_data_model> createComment(@FieldMap Map<String, String> fields);

}


