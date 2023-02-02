package com.itecknologi.iteckapp.models

data class Result(
        val __v: Int,
        val _id: String,
        val conversationId: String,
        val createdAt: Long,
        val deactivated: Boolean,
        val deleted: Boolean,
        val mimeType: String,
        val name: String,
        val senderId: String,
        val size: Any,
        val text: String,
        val type: String
)