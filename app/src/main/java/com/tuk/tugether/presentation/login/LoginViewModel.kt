package com.tuk.tugether.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


class LoginViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    fun login() {
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()

        if (emailValue.isBlank() || passwordValue.isBlank()) {
            _loginResult.value = "입력값을 모두 입력해주세요."
        } else if (!emailValue.endsWith("@tukorea.ac.kr")) {
            _loginResult.value = "학교 이메일만 사용 가능합니다."
        } else if (emailValue == "test@tukorea.ac.kr" && passwordValue == "1234") {
            _loginResult.value = "로그인 성공"
        } else {
            _loginResult.value = "이메일 또는 비밀번호가 올바르지 않습니다."
        }
    }

}
