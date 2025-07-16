package com.tuk.tugether.domain.model.response

data class ParticipantListResponseModel(
    val userList: List<ChatRoomUserModel>
) {
    data class ChatRoomUserModel(
        val userId: Long,
        val owner: Boolean
    )
}