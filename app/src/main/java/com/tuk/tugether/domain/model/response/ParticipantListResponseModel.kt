package com.tuk.tugether.domain.model.response

data class ParticipantListResponseModel(
    val success: Boolean,
    val message: String,
    val userList: List<ChatRoomUserModel>
) {
    data class ChatRoomUserModel(
        val userId: Long,
        val nickname: String,
        val owner: Boolean
    )
}