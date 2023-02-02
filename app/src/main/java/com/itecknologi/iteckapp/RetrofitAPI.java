package com.itecknologi.iteckapp;

import com.itecknologi.iteckapp.models.TokenResponse;
import com.itecknologi.iteckapp.models.UserMessages;
import com.itecknologi.iteckapp.utils.Constants;

import org.w3c.dom.Comment;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("DataModal")

    //on below line we are creating a method to post our data.
    Call<DataModal> createPost(@Body DataModal dataModal);

    @FormUrlEncoded
    @POST("DataModel")
    Call<Comment> createComment(@Field("device_id") String device_id, @Field("Email") String Email, @Field("Contact") String Contact, @Field("FcmToken") String FcmToken);

    @FormUrlEncoded
    @POST("GetStatsModel")
    Call<GetStatsModel> getResponse(@Field("veh_id") String veh_id);

    @FormUrlEncoded
    @POST("DataModel")
    Call<DataModal> createComment(@FieldMap Map<String, String> fields);

    @GET("mobile/get_stats_lite.php/")
    Call<Statss> getStats(@Query("veh_id") String selectedVehicle,
                          @Query("object_id") String selectedVehicleObjId);

    //generate token for call

    @Headers({
            "Content-Type:application/json",
            "Authorization:Bearer " + Constants.token,
    })
    @GET("agora/create")
    Call<TokenResponse> createToken(@Query("channelName") String channelName, @Query("userId") String userId);

    @Headers({
            "Content-Type:application/json",
            "Authorization:Bearer " + Constants.token,
    })
    @GET("message/get")
    Call<UserMessages> getMessagesFromServer(@Query("search") String search, @Query("page") int page);

}


