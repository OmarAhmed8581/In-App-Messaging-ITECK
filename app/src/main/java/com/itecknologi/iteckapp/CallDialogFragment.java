package com.itecknologi.iteckapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.itecknologi.iteckapp.chat.MessageActivity;
import com.itecknologi.iteckapp.databinding.ActivityVoiceCallBinding;
import com.itecknologi.iteckapp.utils.CountUpTimer;
import com.itecknologi.iteckapp.utils.Helper;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;

public class CallDialogFragment extends Fragment implements View.OnClickListener {

    private ActivityVoiceCallBinding binding;
    private RtcEngine rtcEngine;
    private String Tag = "VoiceCallTag";
    private CountUpTimer callTimer;
    private Boolean isMicEnabled = true;
    private Boolean isSpeakerOpened = false;
    private Boolean isMiniCallLayoutEnabled = false;
    private String callToken, senderId, receiverId, channelName;
    private Boolean userInitiatedCall;

    private final IRtcEngineEventHandler eventHandler = new IRtcEngineEventHandler() {

        @Override
        public void onUserJoined(int uid, int elapsed) {
            super.onUserJoined(uid, elapsed);
            callTimer.start();
            Helper.getInstance().displayToast(requireContext(), "Call connected");
            Log.d(Tag, "AGORA >> onUserJoined, " + uid);
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            super.onUserOffline(uid, reason);
            Helper.getInstance().displayToast(getContext(), "User left");
            rtcEngine.leaveChannel();
            callTimer.cancel();
            requireActivity().finish();
        }

        @Override
        public void onConnectionLost() {
            super.onConnectionLost();
            Helper.getInstance().displayToast(getContext(), "Connection lost");
            rtcEngine.leaveChannel();
            callTimer.cancel();
            requireActivity().finish();
        }


        @Override
        public void onConnectionStateChanged(int state, int reason) {
            super.onConnectionStateChanged(state, reason);

            switch (state) {
                case 0: {
                    binding.ivWifiStrength.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.wifid));
                    break;
                }

                case 3: {
                    binding.ivWifiStrength.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.connecting));

                    break;
                }

                case 2: {
                    binding.ivWifiStrength.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.wific));

                    break;
                }

//               case 3:{
//                   binding.ivWifiStrength.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.wifid));
//
//                   break;
//               }
            }

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityVoiceCallBinding.inflate(inflater, container, false);
        return binding.getRoot();
//        return inflater.inflate(R.layout.activity_voice_call,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();

    }

    public void startCall() {

    }

    private void initViews() {

        binding.btnMessage.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.btnToggleMic.setOnClickListener(this);
        binding.btnToggleSpeaker.setOnClickListener(this);
        binding.btnCallEnd.setOnClickListener(this);
        binding.btnCallEnd2.setOnClickListener(this);
        binding.btnToggleMic2.setOnClickListener(this);
        binding.btnToggleSpeaker2.setOnClickListener(this);
//        binding.btnOpenCallScreen.setOnClickListener(this);


        callTimer = new CountUpTimer(Long.MAX_VALUE) {
            @Override
            public void onTick(int second) {

                if (isMiniCallLayoutEnabled) {
                    binding.tvCallTimer.setText(Helper.getInstance().formatTime(Long.parseLong(String.valueOf(second))));

                } else {
                    binding.tvStatus.setText(Helper.getInstance().formatTime(Long.parseLong(String.valueOf(second))));

                }

            }
        };
        getArgumentsFromBundle();

    }


    private void getArgumentsFromBundle() {
        try {
            if (getArguments() != null) {
                callToken = getArguments().getString("callToken");
                userInitiatedCall = getArguments().getBoolean("userInitiatedCall");
                senderId = getArguments().getString("senderId");
                receiverId = getArguments().getString("receiverId");
                channelName = getArguments().getString("channelName");
            }

            initializing();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void customAlert(String msg, String title) {
        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        alertDialog.setCancelable(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);

        EditText editText = new EditText(requireContext());
        alertDialog.setView(editText);
        alertDialog.setPositiveButton("End Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                endCall();
                dialogInterface.dismiss();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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

    public void showCallLayout() {
        binding.fullCallLayout.setVisibility(View.VISIBLE);
    }

    private void initializeAndJoinChannel() {
        try {
            rtcEngine = RtcEngine.create(requireActivity().getBaseContext(), com.itecknologi.iteckapp.utils.Constants.agoraAppId, eventHandler);
        } catch (Exception e) {
            Log.e("Init err", e.getMessage());
        }

        rtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
//        String channelName = com.itecknologi.iteckapp.utils.Constants.callChannel;
//        rtcEngine.joinChannel(callToken, channelName, "", 0);
        rtcEngine.joinChannelWithUserAccount(callToken, channelName, senderId);
        if (userInitiatedCall) {
            ((MessageActivity) requireActivity()).startCall();
        }
//
    }

    @Override
    public void onStop() {
        super.onStop();
        if (rtcEngine != null) {
            rtcEngine.leaveChannel();
            callTimer.cancel();
        }
    }


    private void askForPermission() {
        Dexter.withContext(requireContext())
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                        initializeAndJoinChannel();
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
                requireContext(),
                Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
            case R.id.btn_message: {
                openMiniCallLayout();
                break;
            }

            case R.id.btn_toggle_mic: {
                toggleMic();
                break;
            }

            case R.id.btn_toggle_speaker: {
                toggleSpeaker();
                break;
            }
            case R.id.btn_call_end: {
                endCall();
                break;
            }


            default: {
                break;
            }
        }
    }

    private void openMiniCallLayout() {

//        binding.miniCallLayout.setVisibility(View.VISIBLE);
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

//                binding.fullCallLayout.setVisibility(View.GONE);
                ((MessageActivity) requireActivity()).showBgCall();
            }
        });

        isMiniCallLayoutEnabled = true;
    }

    private void endCall() {
        if (rtcEngine != null) {
            rtcEngine.leaveChannel();
            callTimer.cancel();
            ((MessageActivity) requireActivity()).removeFragment();
//            requireActivity().finish();
        }
    }

    private void toggleSpeaker() {
        if (rtcEngine == null) return;
        if (!rtcEngine.getCallId().isEmpty()) {
            if (isSpeakerOpened) {
                ViewCompat.setBackgroundTintList(binding.btnToggleSpeaker, ColorStateList.valueOf(getResources().getColor(R.color.color_call_bg)));
                binding.btnToggleSpeaker.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.speaker_white));
                rtcEngine.setEnableSpeakerphone(false);

            } else {
                ViewCompat.setBackgroundTintList(binding.btnToggleSpeaker, ColorStateList.valueOf(getResources().getColor(R.color.white)));
                binding.btnToggleSpeaker.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.speaker));
                rtcEngine.setEnableSpeakerphone(true);
            }

            isSpeakerOpened = !isSpeakerOpened;
        }
    }

    private void toggleMic() {
        if (rtcEngine == null) return;

        if (!rtcEngine.getCallId().isEmpty()) {
            if (isMicEnabled) {
                ViewCompat.setBackgroundTintList(binding.btnToggleMic, ColorStateList.valueOf(getResources().getColor(R.color.white)));
                binding.btnToggleMic.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_mic_off_24));

                rtcEngine.disableAudio();
//                ViewCompat.setBackgroundTintList(binding.btnToggleMic, ColorStateList.valueOf(Color.RED));
            } else {
                ViewCompat.setBackgroundTintList(binding.btnToggleMic, ColorStateList.valueOf(getResources().getColor(R.color.color_call_bg)));

                binding.btnToggleMic.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_mic_24));

                rtcEngine.enableAudio();
//                ViewCompat.setBackgroundTintList(binding.btnToggleMic, ColorStateList.valueOf(Color.WHITE));

            }
            isMicEnabled = !isMicEnabled;
        }
    }
}
