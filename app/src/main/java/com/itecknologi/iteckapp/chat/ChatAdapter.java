package com.itecknologi.iteckapp.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itecknologi.iteckapp.R;
import com.itecknologi.iteckapp.models.ChatMessage;
import com.itecknologi.iteckapp.utils.Constants;
import com.itecknologi.iteckapp.utils.Helper;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChatMessage> messages;
    private Context context;

    private int type_local_user = 1;
    private int type_remote_user = 2;
    private int type_local_user_img = 3;

    public ChatAdapter(ArrayList<ChatMessage> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {

        if (Objects.equals(messages.get(position).getSenderId(), Constants.my_user_id)) {
            return type_local_user;
        } else if (!messages.get(position).getSenderId().equals(Constants.my_user_id)) {
            return type_remote_user;
        } else {
            return type_remote_user;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        if (viewType == type_local_user) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
            return new RemoteUserViewHolder(view);
        }
        if (viewType == type_remote_user) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_remote, parent, false);
            return new RemoteUserViewHolder(view);
        }

        if (viewType == type_local_user_img) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_local_image, parent, false);
            return new LocalUserImageViewHolder(view);
        }

        // inflating our layout file on below line.

        return null;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = messages.get(position);


        try {
            if (getItemViewType(position) == type_local_user_img) {

                LocalUserImageViewHolder myHolder = (LocalUserImageViewHolder) holder;


                File file = new File(chatMessage.getText());

                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    myHolder.ivUserImage.setImageBitmap(bitmap);
                }

            } else {
                RemoteUserViewHolder myHolder = (RemoteUserViewHolder) holder;

                if (chatMessage.getType().equals("image") && chatMessage.getSenderId().equals(Constants.my_user_id)) {
                    myHolder.ivImage.setVisibility(View.VISIBLE);
                    myHolder.tvMessage.setVisibility(View.GONE);
                    File file = new File(chatMessage.getText());

                    if (file.exists()) {
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        myHolder.ivImage.setImageBitmap(bitmap);
                    }
                } else if (chatMessage.getType().equals("image") && !chatMessage.getSenderId().equals(Constants.my_user_id)) {
                    myHolder.ivImage.setVisibility(View.VISIBLE);
                    myHolder.tvMessage.setVisibility(View.GONE);

                    Bitmap bitmap = Helper.getInstance().byteStringToBitmap(chatMessage.getText());
                    if (bitmap != null) {
                        myHolder.ivImage.setImageBitmap(bitmap);
                    }


                } else {
                    myHolder.tvMessage.setText(chatMessage.getText());

                }


                myHolder.tvCreatedDate.setText(Helper.getInstance().timestampToDate(chatMessage.getCreatedDate()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class LocalUserImageViewHolder extends RecyclerView.ViewHolder {

        TextView tvCreatedDate, tvUserInitials;
        ImageView ivUserImage;

        public LocalUserImageViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCreatedDate = itemView.findViewById(R.id.tv_created_date);
            tvUserInitials = itemView.findViewById(R.id.tv_user_initials);
            ivUserImage = itemView.findViewById(R.id.iv_image);


        }
    }


    public static class RemoteUserViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMessage, tvUserInitials, tvCreatedDate;
        public ImageView ivImage;

        public RemoteUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvUserInitials = itemView.findViewById(R.id.tv_user_initials);
            tvCreatedDate = itemView.findViewById(R.id.tv_created_date);
            ivImage = itemView.findViewById(R.id.iv_image);


        }
    }
}
