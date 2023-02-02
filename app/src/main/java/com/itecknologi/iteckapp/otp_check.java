package com.itecknologi.iteckapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class otp_check extends AppCompatActivity {
    private Button verify;
    private TextView timer, resendotp, otpnotify;
    private EditText otp;
    String code, contact = "";
    String fetchapploginid;
    SharedPreferences sh;
    private VideoView clip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_check);

        clip = (VideoView) findViewById(R.id.videoView7);
        timer = (TextView) findViewById(R.id.timer);
        otpnotify = (TextView) findViewById(R.id.textViewotpnotify);
        otp = (EditText) findViewById(R.id.textInputEditTextotp);
        resendotp = (TextView) findViewById(R.id.textView4);

        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);

        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(clip);


        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.two);

        clip.setVideoURI(uri);
        clip.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                int videoWidth = mp.getVideoWidth();
                int videoHeight = mp.getVideoHeight();
                //Get VideoView's current width and height
                int videoViewWidth = clip.getWidth();
                int videoViewHeight = clip.getHeight();

                float xScale = 30;
                float yScale = 2;

                float scale = Math.min(xScale, yScale);

                float scaledWidth = scale * videoWidth;
                float scaledHeight = scale * videoHeight;

                ViewGroup.LayoutParams layoutParams = clip.getLayoutParams();
                layoutParams.width = (int) scaledWidth;
                layoutParams.height = (int) scaledHeight;
                clip.setLayoutParams(layoutParams);
                clip.start();

            }

        });


        verify = (Button) findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String a = androidId.trim();
                String b = otp.getText().toString();

                sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

                fetchapploginid = sh.getString("Apploginid", "");
                String q = fetchapploginid;

                if (checkConnection()) {
                    postData(q.trim(), b.trim(), a.trim());
                } else {
                    showAlertDialogue2("No Internet",
                            "Make sure your internet is connected and try again", R.drawable.ic_wifi_off_fill);
                }


            }
        });

        resendotp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);


                String fetchemail = sh.getString("Email", "");
                String fetchphone = sh.getString("Phone", "");

                String eemail = fetchemail;
                String pphone = fetchphone;
                String a = androidId.trim();
                String b = fetchemail;
                String c = fetchphone;
                String d = "ffcm";

                resendotp(a.trim(), b.trim(), c.trim(), d.trim());


            }
        });

        reverseTimer(150, timer);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String fetchphone = sh.getString("Phone", "");

        String pphone = (fetchphone);
        otpnotify.setText("OTP sent on: " + pphone);


        SmsVerifyCatcher smsVerifyCatcher = new SmsVerifyCatcher(otp_check.this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                code = parseCode(message);

                Log.d(" OTP", code);
                if (code != null && code != "") {
                    otp.setText(code);
                    String a = code;

                    otp.setText(a.trim());
                } else {


                }
            }

        });
        smsVerifyCatcher.onStart();


    }


    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{6}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;

    }

    public void reverseTimer(int Seconds, final TextView tv) {

        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                tv.setText("Expires in: " + String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                tv.setText("OTP Expired.");
                resendotp.setVisibility(View.VISIBLE);
            }

        }.start();
    }


    private void resendotp(String device_id, String email, String contact, String FcmToken) {
        if (checkConnection()) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.itecknologi.com/mobile/login.php/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

            DataModal modal = new DataModal(device_id, email, contact, FcmToken);


            if (modal.equals(null) && modal.equals("")) {


            } else {
                TelephonyManager telephonyManager;
                telephonyManager = (TelephonyManager) getSystemService(Context.
                        TELEPHONY_SERVICE);
                @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);


                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

                String fetchemail = sh.getString("Email", "");
                String fetchphone = sh.getString("Phone", "");

                String eemail = (fetchemail);
                String pphone = (fetchphone);

                HashMap<String, String> fields = new HashMap<>();
                String a = androidId;
                String b = eemail;
                String c = pphone;
                String d = "ffcm";
                fields.put("login_id", a);
                fields.put("Email", b);
                fields.put("Contact", c);
                fields.put("FcmToken", d);


                Call<DataModal> call = retrofitAPI.createComment(fields);

                call.enqueue(new Callback<DataModal>() {
                    @Override
                    public void onResponse(Call<DataModal> call, retrofit2.Response<DataModal> response) {

                        DataModal responseFromAPI = response.body();
                        String responseString = "Response Code:" + response.code() + "\n" + "Response:" + responseFromAPI.getSuccess() + "\n" + "Msg:" + responseFromAPI.getMessage();


                        if (responseFromAPI.getSuccess().equals("true") && responseFromAPI.getMessage().equals("OTP Sent")) {
                            Intent intent = new Intent(getApplicationContext(), otp_check.class);
                            startActivity(intent);
                        }
                        return;
                    }


                    @Override
                    public void onFailure(Call<DataModal> call, Throwable t) {
                        Toast.makeText(otp_check.this, "Error Found:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        } else {
            showAlertDialogue2("No Internet",
                    "Make sure your internet is connected and try again", R.drawable.ic_wifi_off_fill);
        }


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


    private void postData(String login_id, String otp, String deviceId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.itecknologi.com/mobile/login.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Otp_check_API retrofitAPI = retrofit.create(Otp_check_API.class);
        Otp_check_data_model modal = new Otp_check_data_model(login_id, otp, deviceId);

        if (modal.equals(null) && modal.equals("")) {


        } else {

            TelephonyManager telephonyManager;
            telephonyManager = (TelephonyManager) getSystemService(Context.
                    TELEPHONY_SERVICE);
            @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);


            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

            String fetchapploginid = sh.getString("apploginid", "");

            HashMap<String, String> fields = new HashMap<>();
            String a = androidId;
            String b = otp.toString();

            fields.put("login_id", login_id);
            fields.put("OTP", b);
            fields.put("device_id", a);

            Log.d(TAG, "otp Request: " + a);
            Log.d(TAG, "otp Request: " + b);
            Log.d(TAG, "otp Request: " + login_id);
            Call<Otp_check_data_model> call = retrofitAPI.createComment(fields);

            call.enqueue(new Callback<Otp_check_data_model>() {
                @Override
                public void onResponse(Call<Otp_check_data_model> call, retrofit2.Response<Otp_check_data_model> response) {

                    Log.d(TAG, "otp response: " + response.body().getMessage());
                    Log.d(TAG, "otp response: " + response.body().getSuccess());
                    Log.d(TAG, "otp response: " + response.body().getlogin_id());

                    Otp_check_data_model responseFromAPI = response.body();

                    String responseString = "Response Code:" + response.code() + "\n" + "Response:"
                            + responseFromAPI.getSuccess() + "\n" + "Msg:" + responseFromAPI.getMessage();

                    if (responseFromAPI.getSuccess().equals("true") &&
                            responseFromAPI.getMessage().equals("OTP VERIFIED")) {

                        SharedPreferences.Editor myEdit = sh.edit();
                        myEdit.putString("status", "true");
                        myEdit.apply();
                        String st = sh.getString("status", "");

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "OTP: " + response.body().getMessage());
                        Toast.makeText(otp_check.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(otp_check.this, "Invalid OTP.", Toast.LENGTH_LONG).show();
                    return;
                }


                @Override
                public void onFailure(Call<Otp_check_data_model> call, Throwable t) {
                    Toast.makeText(otp_check.this, "Error Found:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}