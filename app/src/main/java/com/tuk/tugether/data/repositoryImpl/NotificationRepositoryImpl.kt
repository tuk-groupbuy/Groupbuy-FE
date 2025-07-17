package com.tuk.tugether.data.repositoryImpl

import com.tuk.tugether.data.datasource.NotificationDataSource
import com.tuk.tugether.data.dto.request.notification.NotificationApproveRequestDto
import com.tuk.tugether.data.dto.request.notification.NotificationDecisionRequestDto
import com.tuk.tugether.domain.model.response.notification.NotificationResponseModel
import com.tuk.tugether.domain.model.response.notification.toNotificationResponseModel
import com.tuk.tugether.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val dataSource: NotificationDataSource
) : NotificationRepository {
    override suspend fun getNotifications(userId: Long): List<NotificationResponseModel> {
        return dataSource.getNotifications(userId).map { it.toNotificationResponseModel() }
    }

    override suspend fun approveNotification(request: NotificationApproveRequestDto): String {
        return dataSource.approveNotification(request)
    }

    override suspend fun rejectNotification(request: NotificationDecisionRequestDto): String {
        return dataSource.rejectNotification(request)
    }
}
