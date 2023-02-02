package com.itecknologi.iteckapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login_one extends AppCompatActivity {
    private Button register;
    private VideoView regclip;
    String tokenn = "1", responsDeviceid, apploginid;
    private EditText ContactNo, Email;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_one);

        register = (Button) findViewById(R.id.reg);
        regclip = (VideoView) findViewById(R.id.videoView8);
        Email = (EditText) findViewById(R.id.textInputEditText);
        ContactNo = (EditText) findViewById(R.id.textInputEditText2);
        progressDialog = new ProgressDialog(this);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(regclip);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.two);

        regclip.setVideoURI(uri);
        regclip.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                int videoWidth = mp.getVideoWidth();
                int videoHeight = mp.getVideoHeight();

                int videoViewWidth = regclip.getWidth();
                int videoViewHeight = regclip.getHeight();

                float xScale = 30;
                float yScale = 2;

                float scale = Math.min(xScale, yScale);

                float scaledWidth = scale * videoWidth;
                float scaledHeight = scale * videoHeight;


                ViewGroup.LayoutParams layoutParams = regclip.getLayoutParams();
                layoutParams.width = (int) scaledWidth;
                layoutParams.height = (int) scaledHeight;
                regclip.setLayoutParams(layoutParams);
                regclip.start();

            }

        });


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        String token = task.getResult();

                        tokenn = token;

                    }
                });


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);

        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO
                regclip.stopPlayback();

                String a = androidId.toString();
                String b = Email.getText().toString();
                String c = ContactNo.getText().toString();
                String d = tokenn;
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String fetchloginid = sh.getString("Apploginid", "");

                postData(a.trim(), b.trim(), c.trim(), d.trim());

            }
        });


    }


    private void postData(String device_id, String email, String contact, String FcmToken) {
        if (checkConnection()) {
            progressDialog.setMessage("Requesting OTP...");
            progressDialog.show();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.itecknologi.com/mobile/login.php/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

            DataModal modal = new DataModal(device_id, email, contact, FcmToken);

            if (modal.equals(null) && modal.equals("")) {
                Toast.makeText(login_one.this, "Modal Is Empty", Toast.LENGTH_LONG).show();

            } else {

                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.
                        TELEPHONY_SERVICE);
                @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);


                HashMap<String, String> fields = new HashMap<>();
                String a = androidId;
                String b = Email.getText().toString();
                String c = ContactNo.getText().toString();
                String d = tokenn;
                fields.put("device_id", a);
                fields.put("Email", b);
                fields.put("Contact", c);
                fields.put("FcmToken", d);
                // Toast.makeText(login_one.this, "Active", Toast.LENGTH_LONG).show();
                Call<DataModal> call = retrofitAPI.createComment(fields);

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                myEdit.putString("AndroidId", a);
                myEdit.putString("Email", b);
                myEdit.putString("Phone", c);
                myEdit.putString("fcmtoken", tokenn);

                myEdit.commit();


                call.enqueue(new Callback<DataModal>() {
                    @Override
                    public void onResponse(Call<DataModal> call, retrofit2.Response<DataModal> response) {

                        DataModal responseFromAPI = response.body();
                        String responseString = "Response Code:" + response.code() + "\n" + "Response:" + responseFromAPI.getSuccess() + "\n" + "Msg:" + responseFromAPI.getMessage();

                        responsDeviceid = responseFromAPI.getapploginid();

                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        sharedPreferences.edit().remove("Apploginid").apply();

                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("Apploginid", responsDeviceid.trim());
                        myEdit.putString("status", "false");
                        myEdit.apply();
                        Toast.makeText(login_one.this, "App_login_id Saved", Toast.LENGTH_LONG).show();


                        Toast.makeText(login_one.this, responseString, Toast.LENGTH_SHORT).show();
                        if (responseFromAPI.getSuccess().equals("true") && responseFromAPI.getMessage().equals("OTP Sent")) {
                            Intent intent = new Intent(getApplicationContext(), otp_check.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(login_one.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    @Override
                    public void onFailure(Call<DataModal> call, Throwable t) {


                        Toast.makeText(login_one.this, "Error Found:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        } else {
            showAlertDialogue2("No Internet",
                    "Make sure your internet is connected and try again", R.drawable.ic_wifi_off_fill);
        }


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

                .addConverterFactory(GsonConverterFactory.create())

                .build();
        Update_Fcm_Token retrofitAPI = retrofit.create(Update_Fcm_Token.class);

        Update_Fcm_Token_data_model modal = new Update_Fcm_Token_data_model(loginid, deviceid, fcmtoken);
        if (modal.equals(null) && modal.equals("")) {
            Toast.makeText(login_one.this, "Modal Is Empty", Toast.LENGTH_LONG).show();

        } else {
            // For device id
            TelephonyManager telephonyManager;
            telephonyManager = (TelephonyManager) getSystemService(Context.
                    TELEPHONY_SERVICE);
            @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            HashMap<String, String> fields = new HashMap<>();
            String a = responsDeviceid;
            String b = androidId;
            String d = tokenn;
            fields.put("loginid", a);
            fields.put("deviceid", b);
            fields.put("fcmtoken", d);
            Call<Update_Fcm_Token_data_model> call = retrofitAPI.createComment(fields);


            call.enqueue(new Callback<Update_Fcm_Token_data_model>() {
                @Override
                public void onResponse(Call<Update_Fcm_Token_data_model> call, retrofit2.Response<Update_Fcm_Token_data_model> response) {

                    Update_Fcm_Token_data_model responseFromAPI = response.body();

                    String responseString = "Response Code:" + response.code() + "\n" + "Msg:" + responseFromAPI.getmessage();


                    Toast.makeText(login_one.this, responseString, Toast.LENGTH_SHORT).show();
                    if (responseFromAPI.getmessage().equals("success")) {

                        Toast.makeText(login_one.this, "Fcm Updated.", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(login_one.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    return;
                }


                @Override
                public void onFailure(Call<Update_Fcm_Token_data_model> call, Throwable t) {
                    Toast.makeText(login_one.this, "Error Found:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });


        }


    }

}