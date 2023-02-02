package com.itecknologi.iteckapp.models

data class GetMessage(
//    val _id: String,
        val conversationId: String,
        val createdAt: Long,
        val isTemplate: Boolean,
//    val name: String,
//    val role: String,
        val senderId: String,
//    val socketId: String,
        val text: String,
        val type: String,
//    val userId: String
)