package com.tuk.tugether.data.dto.response.chat

import com.tuk.tugether.domain.model.response.CommonChatResponseModel
import com.tuk.tugether.domain.model.response.CreateChatResponseModel

data class CreateChatResponseDto(
    val success: Boolean,
    val message: String,
    val chatRoomId: Long
) {
    fun toCreateChatResponseModel() =
        CreateChatResponseModel(success, message, chatRoomId)
}

