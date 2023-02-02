package com.itecknologi.iteckapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.itecknologi.iteckapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Helper {

    Dialog loadingDialogue;
    Toast toast;
    private static Helper helper;

    public static Helper getInstance() {
        if (helper == null) {

            helper = new Helper();
        }
        return helper;
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public Bitmap byteStringToBitmap(String bytes) {
        try {
            byte[] imgInByte = bytes.getBytes(StandardCharsets.UTF_8);
            return BitmapFactory.decodeByteArray(imgInByte, 0, imgInByte.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Dialog loadingDialog(Context context) {
        if (loadingDialogue != null) return loadingDialogue;
        try {
            loadingDialogue = new Dialog(context);
            loadingDialogue.setContentView(R.layout.loading);
            loadingDialogue.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rounded_corner_main_activity));
            loadingDialogue.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            loadingDialogue.setCancelable(false);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return loadingDialogue;
    }

    public void displayToast(Context context, String msg) {


        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

//        if(toast!=null){
//            toast.cancel();
//            toast.setText(msg);
//        }else{
//            toast = Toast.makeText(context, msg,Toast.LENGTH_SHORT);
//        }
//        toast.show();

    }

    public void logMsg(String msg) {
        Log.d("Msg Logs", msg);
    }

    public void logError(String msg) {
        Log.e("Error Logs", msg);
    }

    public String formatTime(long time) {
        try {
            Date date = new Date(time * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

            return sdf.format(date);
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }

    }

    public enum SocketChannels {
        AddUser,
        GetUsers,
        SendMessage,
        GetMessage,
        StartCall,
        EndCall,
        DeclineCall,
        RemoveAgent,
        UserLeft,
        AgentLeft,
        AddAgent,
        TakeUser,
        DeclineUser,
        GetActiveChats,
        GetAgentActiveChats,
        CallStarted;

        public String getChannel() {
            switch (this) {

                case CallStarted:
                    return "callStarted";

                case RemoveAgent:
                    return "removeAgent";

                case AddUser:
                    return "addUser";

                case GetUsers:
                    return "getUsers";
                case SendMessage:
                    return "sendMessage";
                case GetMessage:
                    return "getMessage";

                case StartCall:
                    return "startCall";

                case EndCall:
                    return "endCall";

                case DeclineCall:
                    return "declineCall";

                case UserLeft:
                    return "userLeft";

                case AgentLeft:
                    return "agentLeft";

                case AddAgent:
                    return "addAgent";

                case DeclineUser:
                    return "declineUser";

                case GetActiveChats:
                    return "getActiveChats";

                case GetAgentActiveChats:
                    return "getAgentActiveChats";

                default:
                    return "";
            }
        }

    }

    public String timestampToDate(Long time) {
        String strDate = "";


        try {
            Timestamp timestamp = new Timestamp(time);
            Date date = new Date(timestamp.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
            simpleDateFormat.format(date);
            System.out.println(date);
            strDate = simpleDateFormat.format(date);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return strDate;
    }

}
