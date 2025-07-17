package com.tuk.tugether.domain.model.request

import com.tuk.tugether.data.dto.request.chat.CommonChatRequestDto

data class CommonChatRequestModel(
    val userId: Long,
    val chatRoomId :Long
) {
    fun toCommonChatRequestDto() =
        CommonChatRequestDto(userId, chatRoomId)
}