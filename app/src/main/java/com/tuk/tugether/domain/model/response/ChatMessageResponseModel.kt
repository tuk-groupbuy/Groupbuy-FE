package com.tuk.tugether.domain.model.response

data class ChatMessageResponseModel(
    val success: Boolean,
    val message: String,
    val messages: List<ChatMessageModel>,
    val isLast: Boolean
) {
    data class ChatMessageModel(
        val sender: String,
        val content: String,
        val viewType: Int,
        val sendAt: String,
        var isLastIndex: Boolean = false
    )
}