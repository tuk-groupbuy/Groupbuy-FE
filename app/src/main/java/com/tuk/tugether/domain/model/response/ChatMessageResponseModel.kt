package com.tuk.tugether.domain.model.response

data class ChatMessageResponseModel(
    val success: Boolean,
    val message: String,
    val messages: List<ChatMessageModel>
) {
    data class ChatMessageModel(
        val sender: String,
        val content: String,
        val sendAt: String
    )
}