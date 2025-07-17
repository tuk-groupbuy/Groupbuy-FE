package com.tuk.tugether.domain.model.request.notification

import com.tuk.tugether.data.dto.request.notification.NotificationDecisionRequestDto

data class NotificationDecisionRequestModel(
    val postId: Long,
    val userId: Long,
    val writerId: Long
){
    fun toDto(): NotificationDecisionRequestDto =
        NotificationDecisionRequestDto(postId, userId, writerId)
}
