package com.itecknologi.iteckapp.chat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.protobuf.ByteString;
import com.google.rpc.Help;
import com.itecknologi.iteckapp.CallDialogFragment;
import com.itecknologi.iteckapp.MainActivity;
import com.itecknologi.iteckapp.R;
import com.itecknologi.iteckapp.RetrofitAPI;
import com.itecknologi.iteckapp.databinding.ActivityMessageBinding;
import com.itecknologi.iteckapp.models.AgentCallObject;
import com.itecknologi.iteckapp.models.CallObject;
import com.itecknologi.iteckapp.models.ChatMessage;
import com.itecknologi.iteckapp.models.GetMessage;
import com.itecknologi.iteckapp.models.TokenResponse;
import com.itecknologi.iteckapp.models.User;
import com.itecknologi.iteckapp.models.UserMessages;
import com.itecknologi.iteckapp.remote.api.APIClient;
import com.itecknologi.iteckapp.utils.AndroidLoggingHandler;
import com.itecknologi.iteckapp.utils.CallingInterface;
import com.itecknologi.iteckapp.utils.Constants;
import com.itecknologi.iteckapp.utils.DialogHelper;
import com.itecknologi.iteckapp.utils.EndlessRecyclerViewScrollListener;
import com.itecknologi.iteckapp.utils.Helper;
import com.itecknologi.iteckapp.utils.SocketManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import io.socket.client.Ack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity implements CallingInterface {
//user id eec1efdf-d0a8-4628-b6f7-b99411a659fb ----------- 7965a6f7-db0a-4802-922b-5cfd15942fc3 ------- 08e5dd20-0ff7-4126-a92b-a0bf50a5fad6

    FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
    CallDialogFragment fragment;
    private ActivityMessageBinding binding;
    private final ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private ChatAdapter chatAdapter;
    final int pdfCode = 123;
    private int currentPage = 1;
    String channelName = UUID.randomUUID().toString();
    File selectedImage = null;
    File selectedPdf = null;
    String agoraToken = "";

    RetrofitAPI apiInterface;
    SharedPreferences sharedPreferences;

    static String userId = "", conversationId = userId;

    String type, mimeType = "";
    Boolean isAgentAvailable = false;
    User agent;
    User user;
    String senderId = "";
    String receiverId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        DialogHelper.getInstance().callDialog(this).show();
        AndroidLoggingHandler.reset(new AndroidLoggingHandler());
        java.util.logging.Logger.getLogger("my.category").setLevel(Level.FINEST);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        removeUser();
    }

    CallObject callObject = new CallObject(channelName, receiverId, "user", userId, userId, "audio");
//    CallObject callObject = new CallObject(Constants.callChannel, userId, "agent", agentId, agentId);

    private void initViews() {
        try {
            sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            userId = sharedPreferences.getString("Email", "dummy123");
            userId = "testingUserId";
            conversationId = userId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        user = new User(userId, "Agora User", "user", userId);

        Constants.my_user_id = userId;
        apiInterface = APIClient.getClient().create(RetrofitAPI.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.recyclerView.setLayoutManager(layoutManager);

        clickListeners();

        chatAdapter = new ChatAdapter(chatMessages, this);
        binding.recyclerView.setAdapter(chatAdapter);

        listenToSocketEvents();

        sendButtonObserver();
        getMessagesFromServer();

        binding.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager, EndlessRecyclerViewScrollListener.LoadOnScrollDirection.TOP) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                currentPage = page;
                Helper.getInstance().logMsg("Current page " + currentPage);
//                if (currentPage > 1) {
                getMessagesFromServer();
//                }

            }
        });

    }

    private void sendButtonObserver() {
        binding.etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.btnSend.setEnabled(!editable.toString().isEmpty());
            }
        });
    }

    private void generateToken() {

        try {
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            Helper.getInstance().loadingDialog(MessageActivity.this).show();

                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            Call<TokenResponse> token = apiInterface.createToken(channelName, userId);
            token.enqueue(new Callback<TokenResponse>() {
                @Override
                public void onResponse(@NonNull Call<TokenResponse> call, @NonNull Response<TokenResponse> response) {
                    Helper.getInstance().loadingDialog(MessageActivity.this).dismiss();
                    if (response.body() == null) return;
                    agoraToken = response.body().getToken();
                    System.out.println("agoraToken: " + agoraToken.toString());
                    Helper.getInstance().displayToast(MessageActivity.this, agoraToken);
                }

                @Override
                public void onFailure(@NonNull Call<TokenResponse> call, @NonNull Throwable t) {
                    Helper.getInstance().loadingDialog(MessageActivity.this).dismiss();
                    Helper.getInstance().displayToast(MessageActivity.this, t.getMessage());
                }
            });
        } catch (Exception e) {

            Helper.getInstance().loadingDialog(this).dismiss();
            e.printStackTrace();
        }
    }


    private void getMessagesFromServer() {
//        Helper.getInstance().loadingDialog(this).show();
        try {
            Call<UserMessages> token = apiInterface.getMessagesFromServer(conversationId, currentPage);
            token.enqueue(new Callback<UserMessages>() {
                @Override
                public void onResponse(@NonNull Call<UserMessages> call, @NonNull Response<UserMessages> response) {
                    Helper.getInstance().loadingDialog(MessageActivity.this).dismiss();
                    if (response.body() == null || response.body().component1().isEmpty()) return;

                    List<ChatMessage> tempList = response.body().component1();
//                    Collections.reverse(tempList);

                    chatMessages.addAll(tempList);
                    chatAdapter.notifyItemChanged(0);
                    binding.recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(@NonNull Call<UserMessages> call, @NonNull Throwable t) {
                    Helper.getInstance().loadingDialog(MessageActivity.this).dismiss();
                    Helper.getInstance().displayToast(MessageActivity.this, t.getMessage());
                }
            });
        } catch (Exception e) {
            Helper.getInstance().loadingDialog(this).dismiss();
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;


        if (resultCode == Activity.RESULT_OK) {

            Uri uri = data.getData();

//                    // Use Uri object instead of File to avoid storage permissions
//                    imgProfile.setImageURI(fileUri)

//                ChatMessage message = new ChatMessage(UUID.randomUUID().toString(), "localUser", uri.getPath(),
//                        "type", System.currentTimeMillis(), "image", "J J", "");


            try {


                InputStream iStream = getContentResolver().openInputStream(uri);
                byte[] inputData = Helper.getInstance().getBytes(iStream);


                ChatMessage message = new ChatMessage(userId, userId, receiverId, ByteString.copyFrom(inputData).toString(), "image", "", "", "user", "", System.currentTimeMillis());

                String jsonInString = new Gson().toJson(message);


                JSONObject mJSONObject = new JSONObject(jsonInString);
                Helper.getInstance().logMsg("SEND MESSAGE JSON >>> " + new Gson().newBuilder().setPrettyPrinting().create().toJson(message));
                SocketManager.INSTANCE.emit(Helper.SocketChannels.SendMessage.getChannel(), mJSONObject, args -> {
                    Helper.getInstance().logMsg(Arrays.toString(args));


                });
                message = new ChatMessage(userId, userId, receiverId, uri.getPath(), "image", "", "", "user", "", System.currentTimeMillis());
                chatMessages.add(0, message);

                chatAdapter.notifyItemChanged(0);
                binding.recyclerView.smoothScrollToPosition(0);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }


    private void getPdf() {
        Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
        intentPDF.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intentPDF, "Select Pdf"), pdfCode);
    }

    private void captureImageFromCamera() {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File file = new File(Environment.getExternalStorageDirectory()+File.separator + System.currentTimeMillis()+"_image.jpg");
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//        startActivityForResult(cameraIntent, cameraCode);

        ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    private void getImageFromGallery() {


        ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_pref_dialog);

        LinearLayout captureFromCamera = bottomSheetDialog.findViewById(R.id.select_camera);
        LinearLayout fromGallery = bottomSheetDialog.findViewById(R.id.select_gallery);
        LinearLayout selectPdf = bottomSheetDialog.findViewById(R.id.select_pdf);

        assert captureFromCamera != null;
        assert fromGallery != null;
        assert selectPdf != null;
        captureFromCamera.setOnClickListener(view -> {
            captureImageFromCamera();
            bottomSheetDialog.dismiss();
        });

        fromGallery.setOnClickListener(view -> {
            getImageFromGallery();
            bottomSheetDialog.dismiss();
        });

        selectPdf.setOnClickListener(view -> {
            getPdf();
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }


    private void listenToSocketEvents() {
        MessageActivity activityContext = this;

        SocketManager.INSTANCE.listenTo(Helper.SocketChannels.GetMessage.getChannel(), args -> {
            try {
                if (args.length > 0) {
//                    generateToken();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.btnCall.setVisibility(View.VISIBLE);
                            binding.waitingForAgent.setVisibility(View.GONE);
                            binding.ll111.setVisibility(View.VISIBLE);
                        }
                    });


                    isAgentAvailable = true;
                    String strObj = args[0].toString();
                    GetMessage msg = new Gson().fromJson(strObj, GetMessage.class);
//                    conversationId = msg.getConversationId();
                    senderId = msg.getSenderId();
                    receiverId = msg.getSenderId();
                    agent = new User(msg.getSenderId(), "", "agent", msg.getSenderId());
                    ChatMessage newMsg = new ChatMessage(msg.getConversationId(), senderId, receiverId, msg.getText(), "text", "", "", "agent", "", msg.getCreatedAt());
                    chatMessages.add(0, newMsg);


                    runOnUiThread(() -> {

//                        binding.tvAgentJoined.setVisibility(View.VISIBLE);
                        chatAdapter.notifyItemChanged(0);
                        binding.recyclerView.smoothScrollToPosition(0);
                    });


                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }

        });

        SocketManager.INSTANCE.listenTo(Helper.SocketChannels.CallStarted.getChannel(), args -> {
            try {
                if (args.length > 0) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {

//                                String jsonInString = new Gson().toJson(user);
//                                JSONObject mJSONObject = new JSONObject(jsonInString);

                                AgentCallObject object = new Gson().fromJson(args[0].toString(), AgentCallObject.class);
                                DialogHelper.getInstance().callDialog(activityContext, object).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }

        });

        SocketManager.INSTANCE.listenTo(Helper.SocketChannels.UserLeft.getChannel(), args -> {

            if (args[0] != null) {
                Helper.getInstance().logMsg("USER LEFT >>> " + args[0]);
                if (agent == null) return;
                if (args[0].toString().contains(agent.get_id())) {
                    agent = null;
                }

//                Helper.getInstance().logMsg("USER LEFT >>> " + new Gson().newBuilder().setPrettyPrinting().create().toJson(user));

            }


        });

        SocketManager.INSTANCE.connect(args -> {
            try {
                removeUser();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            addUser();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 2000);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> Helper.getInstance().displayToast(this, "Socket connected"));

        });

        SocketManager.INSTANCE.listenTo(Helper.SocketChannels.AgentLeft.getChannel(), args -> {

                    isAgentAvailable = false;

                }
        );

    }

    private void clickListeners() {

        binding.btnBack.setOnClickListener(view -> {
            showAlert();
        });

        binding.btnCall.setOnClickListener(view -> {
            fragment = new CallDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("callToken", agoraToken);
            bundle.putString("senderId", userId);
            bundle.putString("receiverId", agent.getUserId());
            bundle.putString("channelName", channelName);
            bundle.putBoolean("userInitiatedCall", true);


//            System.out.println("channelName: "+channelName.toString());

            fragment.setArguments(bundle);
//            fm.addToBackStack(null);
//            fm.add(R.id.fragment_container_view, fragment).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, fragment).commit();

        });

        binding.btnSend.setOnClickListener(view -> {

//            generateToken();


            if (SocketManager.INSTANCE.isConnected()) {

                try {

                    if (!isAgentAvailable) {
                        Helper.getInstance().displayToast(this, "Agent not available");
                        return;
                    }

                    type = "text";

                    ChatMessage message = new ChatMessage(conversationId, userId, receiverId, binding.etMessage.getText().toString(), type, mimeType, "", "user", "", System.currentTimeMillis());

                    chatMessages.add(0, message);
                    chatAdapter.notifyItemChanged(0);
                    binding.etMessage.setText("");
                    binding.recyclerView.smoothScrollToPosition(0);
                    String jsonInString = new Gson().toJson(message);
                    JSONObject mJSONObject = new JSONObject(jsonInString);
                    Helper.getInstance().logMsg("SEND MESSAGE JSON >>> " + new Gson().newBuilder().setPrettyPrinting().create().toJson(message));


                    SocketManager.INSTANCE.emit(Helper.SocketChannels.SendMessage.getChannel(), mJSONObject, args -> {
                        Helper.getInstance().logMsg(Arrays.toString(args));
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                Toast.makeText(this, "Not connected to server", Toast.LENGTH_SHORT).show();
            }
        });


        binding.bgCallLayout.setOnClickListener(view -> {
            hideBgCall();
        });

        binding.btnAttach.setOnClickListener(view -> {
            if (cameraAndStoragePermissionGranted()) {
//                showBottomSheetDialog();
                captureImageFromCamera();
            } else {
                getCameraPermission();
            }
        });
    }

    private boolean cameraAndStoragePermissionGranted() {
        return ActivityCompat.checkSelfPermission(this, Constants.camera_permission) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Constants.read_storage_permission) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Constants.write_storage_permission) == PackageManager.PERMISSION_GRANTED;
    }


    private void getCameraPermission() {
        Dexter.withContext(this)
                .withPermissions(Constants.camera_permission, Constants.read_storage_permission, Constants.write_storage_permission)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
//                            showBottomSheetDialog();
                            captureImageFromCamera();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public void removeFragment() {
//        getSupportFragmentManager().popBackStack();

        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//        Intent intent = new Intent(MessageActivity.this, MainActivity.class);
//        startActivity(intent);
//        Toast.makeText(this, "Testing work", Toast.LENGTH_SHORT).show();

        endCall();
    }

    public void showBgCall() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).hide(fragment).commit();
                binding.bgCallLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void hideBgCall() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.bgCallLayout.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).show(fragment).commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        showAlert();
    }

    private void endCallAndChats() {

        try {
            endCall();
            removeUser();
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirmation");
        dialog.setCancelable(false);
        dialog.setMessage("By going back all active chats and calls will be ended. Do you want to proceed?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                endCallAndChats();
                dialogInterface.dismiss();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    public void addUser() throws JSONException {

        runOnUiThread(() -> {
            Helper.getInstance().displayToast(this, "Adding user");
        });

        String jsonInString = new Gson().toJson(user);
        JSONObject mJSONObject = new JSONObject(jsonInString);
        Helper.getInstance().logMsg("ADD USER >>> " + new Gson().newBuilder().setPrettyPrinting().create().toJson(user));
        SocketManager.INSTANCE.emit("addUser", mJSONObject, null);
        generateToken();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager.INSTANCE.clearSession();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SocketManager.INSTANCE.clearSession();

    }

    public void removeUser() throws JSONException {
        String jsonInString = new Gson().toJson(user);
        JSONObject mJSONObject = new JSONObject(jsonInString);

        Helper.getInstance().logMsg("REMOVE USER >>> " + new Gson().newBuilder().setPrettyPrinting().create().toJson(user));

        if (agent == null) {
//            SocketManager.INSTANCE.emit("removeUser", mJSONObject, new Ack() {
//                @Override
//                public void call(Object... args) {
//                    Helper.getInstance().logMsg(Arrays.toString(args));
//                }
//            });


            SocketManager.INSTANCE.emitRemoveUser("removeUser", mJSONObject, null, new Ack() {
                @Override
                public void call(Object... args) {
                    Helper.getInstance().logMsg(Arrays.toString(args));
                }
            });


        } else {
            JSONObject mJsonAgentObj = new JSONObject(new Gson().toJson(agent));
            SocketManager.INSTANCE.emitRemoveUser("removeUser", mJSONObject, mJsonAgentObj, new Ack() {
                @Override
                public void call(Object... args) {
                    Helper.getInstance().logMsg(Arrays.toString(args));
                }
            });

        }


    }


    public void takeUser(View view) throws JSONException {
        Helper.getInstance().displayToast(this, "taking user and agent");

//        User user = new User(userId, "TestUser","user",userId);
//        User agent = new User(agentId, "TestAgent","agent",agentId);

        String jsonInString = new Gson().toJson(agent);
        JSONObject mJSONObject = new JSONObject(jsonInString);


        String jsonUserInString = new Gson().toJson(user);
        JSONObject mJSONUserObject = new JSONObject(jsonUserInString);


        SocketManager.INSTANCE.emitTakeUser("takeUser", mJSONUserObject, mJSONObject, null);

    }

    public void startCall() {
        callObject = new CallObject(channelName, agent.getUserId(), "user", userId, userId, "audio");
        String jsonInString = new Gson().toJson(callObject);
        System.out.println("jsonInString:" + jsonInString);
        try {


            JSONObject mJSONObject = new JSONObject(jsonInString);

            SocketManager.INSTANCE.emit("startCall", mJSONObject, new Ack() {
                @Override
                public void call(Object... args) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void endCall() {
        String jsonInString = new Gson().toJson(callObject);
        try {


            JSONObject mJSONObject = new JSONObject(jsonInString);

            SocketManager.INSTANCE.emit(Helper.SocketChannels.EndCall.getChannel(), mJSONObject, new Ack() {
                @Override
                public void call(Object... args) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void declineCall() {
        SocketManager.INSTANCE.emit(Helper.SocketChannels.DeclineCall.getChannel(), callObject, new Ack() {
            @Override
            public void call(Object... args) {

            }
        });
    }

    public void removeAgent(View view) {
        SocketManager.INSTANCE.emit(Helper.SocketChannels.RemoveAgent.getChannel(), agent, new Ack() {
            @Override
            public void call(Object... args) {

            }
        });
    }

    public void addAgent(View view) throws JSONException {

        Helper.getInstance().displayToast(this, "Adding agent");
        String jsonInString = new Gson().toJson(agent);
        JSONObject mJSONObject = new JSONObject(jsonInString);
        SocketManager.INSTANCE.emit("addAgent", mJSONObject, null);
        generateToken();
    }

    @Override
    public void onCallAccepted(AgentCallObject agentCallObject) {
        fragment = new CallDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("callToken", agoraToken);
        bundle.putString("senderId", agentCallObject.getSenderId());
        bundle.putString("receiverId", agentCallObject.getUserId());
        bundle.putString("channelName", agentCallObject.getChannelName());
        bundle.putBoolean("userInitiatedCall", false);
        fragment.setArguments(bundle);
//        fm.addToBackStack(null);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, fragment).commit();
//        fm.add(R.id.fragment_container_view, fragment).commit();

    }

    @Override
    public void onCallDeclined(AgentCallObject agentCallObject) {
        Helper.getInstance().displayToast(this, "Call declined");
        declineCall();
    }
}