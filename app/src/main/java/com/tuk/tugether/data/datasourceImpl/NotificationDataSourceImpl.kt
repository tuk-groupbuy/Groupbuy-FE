package com.tuk.tugether.data.datasourceImpl

import com.tuk.tugether.data.datasource.NotificationDataSource
import com.tuk.tugether.data.dto.request.notification.NotificationApproveRequestDto
import com.tuk.tugether.data.dto.request.notification.NotificationDecisionRequestDto
import com.tuk.tugether.data.dto.response.notification.NotificationResponseDto
import com.tuk.tugether.data.service.NotificationService
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService
) : NotificationDataSource {

    override suspend fun getNotifications(userId: Long): List<NotificationResponseDto> =
        notificationService.getNotifications(userId)

    override suspend fun approveNotification(request: NotificationApproveRequestDto): String =
        notificationService.approveNotification(request).body() ?: "알 수 없는 응답"

    override suspend fun rejectNotification(request: NotificationDecisionRequestDto): String =
        notificationService.rejectNotification(request).body() ?: "알 수 없는 응답"
}
