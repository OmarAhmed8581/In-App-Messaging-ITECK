package com.itecknologi.iteckapp.utils;

import android.app.Dialog;
import android.util.DisplayMetrics;
import android.widget.ImageButton;

import androidx.appcompat.content.res.AppCompatResources;

import com.itecknologi.iteckapp.R;
import com.itecknologi.iteckapp.chat.MessageActivity;
import com.itecknologi.iteckapp.models.AgentCallObject;

public class DialogHelper {

    private static DialogHelper helper;

    public static DialogHelper getInstance() {
        if (helper == null)
            helper = new DialogHelper();
        return helper;
    }

    public DisplayMetrics displayMetrics(Dialog dialog) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        dialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }


    public Dialog callDialog(MessageActivity messageActivity, AgentCallObject object) {
        Dialog dialog = new Dialog(messageActivity);
        dialog.setContentView(R.layout.incoming_call_dialog);
        dialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(messageActivity, R.color.transparent));
        dialog.setCancelable(false);

        int height = displayMetrics(dialog).heightPixels;
        int width = displayMetrics(dialog).widthPixels;


        dialog.getWindow().setLayout((int) (width * 0.85), (int) (height * 0.90));
        ImageButton btnEndCall = dialog.findViewById(R.id.btn_reject);
        ImageButton btnAcceptCall = dialog.findViewById(R.id.btn_accept);


        btnAcceptCall.setOnClickListener(view -> {
            ((CallingInterface) messageActivity).onCallAccepted(object);
            dialog.dismiss();


        });

        btnEndCall.setOnClickListener(view -> {
            dialog.dismiss();
            ((CallingInterface) messageActivity).onCallDeclined(object);
        });

        return dialog;
    }

}
