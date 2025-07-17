package com.tuk.tugether.domain.model.request.post

import com.tuk.tugether.data.dto.request.post.JoinPostRequestDto

data class JoinPostRequestModel(
    val postId: Long,
    val userId: Long
) {
    fun toDto(): JoinPostRequestDto = JoinPostRequestDto(postId, userId)
}
