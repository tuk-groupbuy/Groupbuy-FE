package com.tuk.tugether.data.repositoryImpl

import com.tuk.tugether.data.datasource.NotificationDataSource
import com.tuk.tugether.data.dto.response.notification.toModel
import com.tuk.tugether.domain.model.request.notification.NotificationApproveRequestModel
import com.tuk.tugether.domain.model.request.notification.NotificationDecisionRequestModel
import com.tuk.tugether.domain.model.response.notification.NotificationResponseModel
import com.tuk.tugether.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val dataSource: NotificationDataSource
) : NotificationRepository {

    override suspend fun getNotifications(userId: Long): Result<List<NotificationResponseModel>> =
        runCatching { dataSource.getNotifications(userId).map { it.toModel() } }

    override suspend fun approveNotification(request: NotificationApproveRequestModel): Result<String> =
        runCatching {
            val response = dataSource.approveNotification(request.toDto())
            response.body() ?: "응답 없음"
        }

    override suspend fun rejectNotification(request: NotificationDecisionRequestModel): Result<String> =
        runCatching {
            val response = dataSource.rejectNotification(request.toDto())
            response.body() ?: "응답 없음"
        }

}