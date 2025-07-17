package com.tuk.tugether.domain.model.response.notification

import com.tuk.tugether.data.dto.response.notification.NotificationResponseDto

data class NotificationResponseModel (
    val notificationId: Long,
    val type: String,
    val content: String,
    val isRead: Boolean,
    val createdAt: String,
    val userId: Long?,
    val currentQuantity: Int,
    val goalQuantity: Int
)