package com.itecknologi.iteckapp.models;

/*
* {
                            conversationId,
                            senderId,
                            text,
                            type,
                            createdAt: Date.now(),
                            mimeType,
                            name,
                            size
}*/


//{
//
//    "conversationId":"user123", "senderId": "user123", "receiverId": "agent123","text":"Hello World","type":"text","mimeType":"","size":"","name":"","role":"user"
//
//        }


import com.google.gson.annotations.SerializedName;

public class ChatMessage {

    @SerializedName("conversationId")
    private String conversationId;
    @SerializedName("senderId")
    private String senderId;
    @SerializedName("receiverId")
    private String receiverId;
    @SerializedName("text")
    private String text;
    @SerializedName("type")
    private String type;
    @SerializedName("createdAt")
    private Long createdAt;

    @SerializedName("mimeType")
    private String mimeType;
    @SerializedName("name")
    private String name;
    @SerializedName("role")
    private String role;
    @SerializedName("size")
    private String size;


    public ChatMessage(String conversationId, String senderId, String receiverId, String text, String type, String mimeType, String name, String role, String size) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.type = type;
        this.mimeType = mimeType;
        this.name = name;
        this.role = role;
        this.size = size;
    }

    public ChatMessage(String conversationId, String senderId, String receiverId, String text, String type, String mimeType, String name, String role, String size, Long createdAt) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.type = type;
        this.mimeType = mimeType;
        this.name = name;
        this.role = role;
        this.size = size;
        this.createdAt = createdAt;
    }


    public String getConversationId() {
        return conversationId;
    }

    public Long getCreatedDate() {
        return createdAt;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getRole() {
        return role;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }
}
