package com.itecknologi.iteckapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

import com.itecknologi.iteckapp.databinding.ActivityVoiceCallBinding;
import com.itecknologi.iteckapp.utils.DialogHelper;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcChannelEventHandler;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcChannel;
import io.agora.rtc.RtcEngine;

public class VoiceCallActivity extends AppCompatActivity {

    private ActivityVoiceCallBinding binding;
    private String APP_ID = "3e027fe400114c379b2e38d350ccc702";
    private String channelName = "testChannel";
    private String callToken = "";
    private RtcEngine rtcEngine;
    private String Tag = "VoiceCallTag";
    private IRtcEngineEventHandler eventHandler = new IRtcEngineEventHandler() {

        @Override
        public void onUserJoined(int uid, int elapsed) {
            super.onUserJoined(uid, elapsed);
            Log.d(Tag, "AGORA >> onUserJoined, " + uid);
        }


        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            super.onJoinChannelSuccess(channel, uid, elapsed);
            Log.d(Tag, "AGORA >> onJoinChannelSuccess, " + channel);
        }

        @Override
        public void onError(int err) {
            super.onError(err);
            Log.d(Tag, "AGORA >> onError, " + err);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVoiceCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateToken();


    }

    private void updateToken() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Set Token");
        EditText editText = new EditText(this);
        alertDialog.setView(editText);
        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Editable value = editText.getText();
                callToken = value.toString();
                initializing();
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void initializing() {
        if (isPermissionGranted()) {
            initializeAndJoinChannel();
        } else {
            askForPermission();
        }
    }

    private void initializeAndJoinChannel() {
        try {
            rtcEngine = RtcEngine.create(getBaseContext(), APP_ID, new IRtcEngineEventHandler() {

                @Override
                public void onUserJoined(int uid, int elapsed) {
                    super.onUserJoined(uid, elapsed);
                    Log.d(Tag, "AGORA >> onUserJoined, " + uid);
                }

                @Override
                public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
                    super.onJoinChannelSuccess(channel, uid, elapsed);
                    Log.d(Tag, "AGORA >> onJoinChannelSuccess, " + channel);
                }

                @Override
                public void onError(int err) {
                    super.onError(err);
                    Log.d(Tag, "AGORA >> onError, " + err);
                }
            });
        } catch (Exception e) {
            Log.e("Init err", e.getMessage());
        }

        rtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
        rtcEngine.joinChannel(callToken, channelName, "", 1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (rtcEngine != null) {
            rtcEngine.leaveChannel();
        }
    }

    private void askForPermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        initializeAndJoinChannel();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }

    private Boolean isPermissionGranted() {
        return ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}