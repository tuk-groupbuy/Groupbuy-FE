package com.tuk.tugether.data.dto.response.chat

import com.tuk.tugether.domain.model.response.CommonChatResponseModel

data class CommonChatResponseDto(
    val success: Boolean,
    val message: String,
    val chatRoomUserId: Long
) {
    fun toJoinChatResponseModel() =
        CommonChatResponseModel(success, message, chatRoomUserId)
}
