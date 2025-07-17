package com.tuk.tugether.data.dto.request.chat

data class ChatMessage(
    val sender: String,
    val content: String,
    val sendAt: String
)