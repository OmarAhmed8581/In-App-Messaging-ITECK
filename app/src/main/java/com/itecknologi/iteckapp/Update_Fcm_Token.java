package com.itecknologi.iteckapp;

import org.w3c.dom.Comment;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Update_Fcm_Token {

    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("Update_Fcm_Token_data_model")

    //on below line we are creating a method to post our data.
    Call<Update_Fcm_Token_data_model> createPost(@Body Update_Fcm_Token_data_model update_Fcm_Token_data_model);

    @FormUrlEncoded
    @POST("Update_Fcm_Token_data_model")
    Call<Comment> createComment(@Field("contact") String contact);

    @FormUrlEncoded
    @POST("Update_Fcm_Token_data_model")
    Call<Update_Fcm_Token_data_model> createComment(@FieldMap Map<String, String> fields);


}
