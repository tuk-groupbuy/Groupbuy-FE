package com.tuk.tugether.data.dto.response.chat

data class ChatResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)
