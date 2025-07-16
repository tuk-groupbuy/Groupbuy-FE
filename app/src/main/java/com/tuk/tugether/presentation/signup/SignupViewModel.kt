package com.tuk.tugether.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val verificationCode = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirm = MutableLiveData<String>()

    private val _step1Valid = MutableLiveData<Boolean>()
    val step1Valid: LiveData<Boolean> get() = _step1Valid

    // 인증번호 요청용 예시
    fun sendVerificationCode() {
        // TODO: 이메일 유효성 검사 + API 연동
        if (email.value.isNullOrBlank()) {
            _step1Valid.value = false
        } else {
            // 실제 API 호출 등
            _step1Valid.value = true
        }
    }

    fun validateStep1(): Boolean {
        val pw = password.value
        val pwConfirm = passwordConfirm.value

        return !email.value.isNullOrBlank()
                && !verificationCode.value.isNullOrBlank()
                && !pw.isNullOrBlank()
                && pw == pwConfirm
    }
}
