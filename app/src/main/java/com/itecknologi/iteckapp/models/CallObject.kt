package com.itecknologi.iteckapp.models

data class CallObject(
        val channelName: String,
        val receiverId: String,
        val role: String,
        val senderId: String,
        val uid: String,
        val type: String = "audio"
)