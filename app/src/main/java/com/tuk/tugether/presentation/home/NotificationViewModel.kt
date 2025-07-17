package com.tuk.tugether.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.tugether.domain.model.request.notification.NotificationApproveRequestModel
import com.tuk.tugether.domain.model.request.notification.NotificationDecisionRequestModel
import com.tuk.tugether.domain.model.response.notification.NotificationResponseModel
import com.tuk.tugether.domain.model.response.post.GetAllPostResponseModel
import com.tuk.tugether.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _notificationList = MutableLiveData<List<NotificationResponseModel>>()
    val notificationList: LiveData<List<NotificationResponseModel>> get() = _notificationList

    private val _actionResult = MutableLiveData<String>()
    val actionResult: LiveData<String> get() = _actionResult

    fun getNotifications(userId: Long) {
        viewModelScope.launch {
            notificationRepository.getNotifications(userId)
                .onSuccess { result ->
                    Log.d("NotificationViewModel", "알림 불러오기 성공: $result")
                    _notificationList.value = result
                }
                .onFailure { exception ->
                    Log.e("NotificationViewModel", "알림 불러오기 실패", exception)
                    _notificationList.value = emptyList()
                }
        }
    }

    fun approveNotification(request: NotificationApproveRequestModel) {
        viewModelScope.launch {
            notificationRepository.approveNotification(request)
                .onSuccess {
                    Log.d("NotificationViewModel", "승인 성공: $it")
                    _actionResult.value = it
                }
                .onFailure { exception ->
                    Log.e("NotificationViewModel", "승인 실패", exception)
                    _actionResult.value = "승인 실패"
                }
        }
    }

    fun rejectNotification(request: NotificationDecisionRequestModel) {
        viewModelScope.launch {
            notificationRepository.rejectNotification(request)
                .onSuccess {
                    Log.d("NotificationViewModel", "거절 성공: $it")
                    _actionResult.value = it
                }
                .onFailure { exception ->
                    Log.e("NotificationViewModel", "거절 실패", exception)
                    _actionResult.value = "거절 실패"
                }
        }
    }
}
