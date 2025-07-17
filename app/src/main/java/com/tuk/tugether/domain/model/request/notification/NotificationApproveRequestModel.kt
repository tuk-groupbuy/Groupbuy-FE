package com.tuk.tugether.domain.model.request.notification

import com.tuk.tugether.data.dto.request.notification.NotificationApproveRequestDto

data class NotificationApproveRequestModel(
    val postId: Long,
    val userId: Long,
    val writerId: Long
){
    fun toNotificationApproveRequestDto() =
        NotificationApproveRequestDto(postId, userId, writerId)
}
