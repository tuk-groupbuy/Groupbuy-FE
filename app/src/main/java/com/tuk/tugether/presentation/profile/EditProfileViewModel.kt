package com.tuk.tugether.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.tugether.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditProfileViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadUserProfile() {
        _isLoading.value = true

        viewModelScope.launch {
            // TODO: 실제 유저 데이터 불러오는 로직으로 교체
            delay(1000) // 예: 네트워크 호출 시뮬레이션

            val dummyUser = User(
                name = "김안드로이드",
                email = "android@example.com",
                joinDate = "2024-05-01",
                profileImageUrl = "https://example.com/profile.jpg"
            )

            _user.value = dummyUser
            _isLoading.value = false
        }
    }
}
