package com.tuk.tugether.domain.model.request

import com.tuk.tugether.data.dto.request.chat.CreateChatRequestDto

data class CreateChatRequestModel(
    val userId: Long,
    val postId: Long
){
    fun toCreateChatRequestDto() =
        CreateChatRequestDto(userId, postId)
}
