package com.tuk.tugether.data.datasourceImpl

import com.tuk.tugether.data.datasource.NotificationDataSource
import com.tuk.tugether.data.dto.request.notification.NotificationApproveRequestDto
import com.tuk.tugether.data.dto.request.notification.NotificationDecisionRequestDto
import com.tuk.tugether.data.dto.response.notification.NotificationResponseDto
import com.tuk.tugether.data.service.NotificationService
import retrofit2.Response
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService
) : NotificationDataSource {

    override suspend fun getNotifications(userId: Long): List<NotificationResponseDto> =
        notificationService.getNotifications(userId)

    override suspend fun approveNotification(request: NotificationApproveRequestDto): Response<String> =
        notificationService.approveNotification(request)

    override suspend fun rejectNotification(request: NotificationDecisionRequestDto): Response<String> =
        notificationService.rejectNotification(request)
}

