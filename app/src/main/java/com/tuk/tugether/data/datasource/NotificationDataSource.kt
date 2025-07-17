package com.tuk.tugether.data.datasource

import com.tuk.tugether.data.dto.response.notification.NotificationResponseDto

interface NotificationDataSource {
    suspend fun getNotifications(userId: Long): List<NotificationResponseDto>
}
