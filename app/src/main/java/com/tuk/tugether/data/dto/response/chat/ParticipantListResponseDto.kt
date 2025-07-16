package com.tuk.tugether.data.dto.response.chat

import com.tuk.tugether.domain.model.response.ParticipantListResponseModel

data class ParticipantListResponseDto(
    val success: Boolean,
    val message: String,
    val responseChatRoomUserGetDTOS: List<ChatRoomUserDto>
) {
    data class ChatRoomUserDto(
        val userId: Long,
        val owner: Boolean
    ) {
        fun toChatRoomUserModel() =
            ParticipantListResponseModel.ChatRoomUserModel(userId, owner)
    }

    fun toParticipantListResponseModel() =
        ParticipantListResponseModel(success, message, responseChatRoomUserGetDTOS.map { it.toChatRoomUserModel() })
}