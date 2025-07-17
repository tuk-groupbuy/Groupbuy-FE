package com.tuk.tugether.data.datasourceImpl

import com.tuk.tugether.data.datasource.NotificationDataSource
import com.tuk.tugether.data.dto.response.notification.NotificationResponseDto
import com.tuk.tugether.data.service.NotificationService
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService
) : NotificationDataSource {
    override suspend fun getNotifications(userId: Long): List<NotificationResponseDto> {
        return notificationService.getNotifications(userId)
    }
}
