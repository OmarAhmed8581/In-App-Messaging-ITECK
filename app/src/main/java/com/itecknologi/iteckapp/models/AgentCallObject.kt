package com.itecknologi.iteckapp.models

data class AgentCallObject(
        val _id: String,
        val channelName: String,
        val name: String,
        val role: String,
        val senderId: String,
        val socketId: String,
        val type: String,
        val uid: String,
        val userId: String
)