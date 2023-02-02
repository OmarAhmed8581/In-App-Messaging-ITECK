package com.itecknologi.iteckapp.models

data class GetUserItem(
        val _id: String,
        val name: String,
        val role: String,
        val socketId: String,
        val userId: String
)