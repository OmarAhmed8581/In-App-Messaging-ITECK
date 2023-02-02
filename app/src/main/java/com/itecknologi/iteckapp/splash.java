package com.itecknologi.iteckapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.telephony.TelephonyManager;
import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class splash extends AppCompatActivity {
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 4000;
    private VideoView clip;
    String androidId, k, tokencheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

/** For Fcm-Token **/

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        tokencheck = token;

                    }
                });


        /** For device id**/
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
        //String deviceId = telephonyManager.getDeviceId();// this is device ID.
        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);// this is Android ID.
        //String subscriberId = telephonyManager.getSubscriberId();

        //Toast.makeText(this, "THE ID IS:"+androidId,,
        //      Toast.LENGTH_LONG).show();

/**Referencing Video. **/
        clip = (VideoView) findViewById(R.id.videoView);
        //Creating MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(clip);

        //specify the location of media file
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash);

        clip.setVideoURI(uri);
        clip.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(Animation.INFINITE);
                anim.setDuration(700);
                //imageview ko call krwaya hai by id.

                final ImageView splash = (ImageView) findViewById(R.id.logo);
                splash.startAnimation(anim);
                splash.setAnimation(null);

                //idhr sy switch krwadunga next activity py.
                splash.this.finish();

                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String fetchloginid = sh.getString("Apploginid", "");
                k = fetchloginid.toString();
                String st = sh.getString("status", "");


                if (!k.equals("") && st.toString().equals("true")) {

                    Intent mainIntent = new Intent(splash.this, MainActivity.class);
                    splash.this.startActivity(mainIntent);

                    if (checkConnection()) {
                        postfcm(k.trim(), androidId.trim(), tokencheck.trim());
                    } else {
                        showAlertDialogue2("No Internet",
                                "Make sure your internet is connected and try again", R.drawable.ic_wifi_off_fill);
                    }


                } else {
                    Intent mainIntent = new Intent(splash.this, login_one.class);
                    splash.this.startActivity(mainIntent);
                }


            }
        }, SPLASH_DISPLAY_LENGTH);


        // postfcm(k.trim(),androidId.trim(),tokencheck.trim());


    }

    private void showAlertDialogue2(String title, String message, int icon) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setIcon(icon);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    private boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (null != networkInfo)
            return true;
        else
            return false;

    }

    private void postfcm(String loginid, String deviceid, String fcmtoken) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.itecknologi.com/mobile/updateFCMtoken.php/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        Update_Fcm_Token retrofitAPI = retrofit.create(Update_Fcm_Token.class);

        // passing data from our text fields to our modal class.


        Update_Fcm_Token_data_model modal = new Update_Fcm_Token_data_model(loginid, deviceid, fcmtoken);
        if (modal.equals(null) && modal.equals("")) {
            Toast.makeText(splash.this, "Modal Is Empty", Toast.LENGTH_LONG).show();

        } else {
            // For device id
            TelephonyManager telephonyManager;
            telephonyManager = (TelephonyManager) getSystemService(Context.
                    TELEPHONY_SERVICE);
            //String deviceId = telephonyManager.getDeviceId();// this is device ID.
            String androidId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);// this is Android ID.
            //String subscriberId = telephonyManager.getSubscriberId();

            //Toast.makeText(login_one.this, "Modal Is Fill", Toast.LENGTH_LONG).show();
            HashMap<String, String> fields = new HashMap<>();
            String a = k;
            String b = androidId;
            String d = tokencheck;
            fields.put("loginid", a);
            fields.put("deviceid", b);
            fields.put("fcmtoken", d);
            // Toast.makeText(login_one.this, "Active", Toast.LENGTH_LONG).show();
            Call<Update_Fcm_Token_data_model> call = retrofitAPI.createComment(fields);

            call.enqueue(new Callback<Update_Fcm_Token_data_model>() {
                @Override
                public void onResponse(Call<Update_Fcm_Token_data_model> call, retrofit2.Response<Update_Fcm_Token_data_model> response) {


                    // this method is called when we get response from our api.
                    // Toast.makeText(login_one.this, "Data added to API", Toast.LENGTH_SHORT).show();

                    // below line is for hiding our progress bar.
                    //loadingPB.setVisibility(View.GONE);

                    // on below line we are setting empty text
                    // to our both edit text.
                    //jobEdt.setText("");
                    // nameEdt.setText("");

                    // we are getting response from our body
                    // and passing it to our modal class.
                    Update_Fcm_Token_data_model responseFromAPI = response.body();

                    // on below line we are getting our data from modal class and adding it to our string.
                    // String responseString = "Response Code : " + response.code() +"\n Device ID:"+responseFromAPI.getDeviceId()+ "\nEmail: " + responseFromAPI.getEmail() + "\n" + "Phone: " + responseFromAPI.getContact()+"\n"+"response:"+responseFromAPI.getSuccess()+"\n"+"Msg:"+responseFromAPI.getMessage();
                    String responseString = "Response Code:" + response.code() + "\n" + "Msg:" + responseFromAPI.getmessage();

                    //responsDeviceid=responseFromAPI.getapploginid();
                    // Storing data into SharedPreferences
                    // SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)

                   /* SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("Apploginid", responsDeviceid.trim());
                    myEdit.commit();
                    Toast.makeText(login_one.this, "App_login_id Saved", Toast.LENGTH_LONG).show();*/

                    // below line we are setting our
                    // string to our text view.
                    //responseTV.setText(responseString);
//                    Toast.makeText(splash.this, responseString, Toast.LENGTH_SHORT).show();
                    if (responseFromAPI.getmessage().equals("success")) {
                        /*Intent intent= new Intent(getApplicationContext(),otp_check.class);
                        startActivity(intent);*/
//                        Toast.makeText(splash.this, "Fcm Updated.", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(splash.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    return;
                }


                @Override
                public void onFailure(Call<Update_Fcm_Token_data_model> call, Throwable t) {
                    // setting text to our text view when
                    // we get error response from API.
                    //responseTV.setText("Error found is : " + t.getMessage());
                    Toast.makeText(splash.this, "Error Found:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }


    }


}