package com.tuk.tugether.domain.model.request.post

import com.tuk.tugether.data.dto.request.post.DeleteJoinPostRequestDto

data class DeleteJoinPostRequestModel(
    val postId: Long,
    val userId: Long
) {
    fun toDto(): DeleteJoinPostRequestDto = DeleteJoinPostRequestDto(postId, userId)
}
