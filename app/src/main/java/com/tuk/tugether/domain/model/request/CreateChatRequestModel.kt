package com.tuk.tugether.domain.model.request

import com.tuk.tugether.data.dto.request.CreateChatRequestDto

data class CreateChatRequestModel(
    val userId: Long
){
    fun toCreateChatRequestDto() =
        CreateChatRequestDto(userId)
}
