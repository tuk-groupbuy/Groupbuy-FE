package com.tuk.tugether.data.dto.request.notification

data class NotificationApproveRequestDto(
    val postId: Long,
    val userId: Long,
    val writerId: Long
)
