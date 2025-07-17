package com.tuk.tugether.presentation.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class SignupViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val verificationCode = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirm = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()

    private val _step1Valid = MutableLiveData<Boolean>()
    val step1Valid: LiveData<Boolean> get() = _step1Valid

    private val _signupSuccess = MutableLiveData<Boolean>()
    val signupSuccess: LiveData<Boolean> get() = _signupSuccess

    // 이메일 인증코드 요청
    fun sendVerificationCode() {
        val currentEmail = email.value.orEmpty()

        if (currentEmail.isBlank()) {
            _step1Valid.value = false
            return
        }

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val url = URL("http://13.125.230.122:8080/api/auth/email/issue-code")
                    val connection = url.openConnection() as HttpURLConnection

                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json")
                    connection.doOutput = true

                    val jsonBody = JSONObject().apply {
                        put("email", currentEmail)
                    }

                    val outputStreamWriter = OutputStreamWriter(connection.outputStream)
                    outputStreamWriter.write(jsonBody.toString())
                    outputStreamWriter.flush()
                    outputStreamWriter.close()

                    val responseCode = connection.responseCode
                    connection.disconnect()

                    responseCode in 200..299
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
            }

            _step1Valid.value = result
        }
    }

    // 유효성 검사
    fun validateStep1(): Boolean {
        val pw = password.value
        val pwConfirm = passwordConfirm.value

        return !email.value.isNullOrBlank()
                && !verificationCode.value.isNullOrBlank()
                && !pw.isNullOrBlank()
                && pw == pwConfirm
    }

    // 회원가입 요청 (닉네임 포함)
    fun submitSignupWithNickname() {
        val currentEmail = email.value.orEmpty()
        val currentCode = verificationCode.value.orEmpty()
        val currentPassword = password.value.orEmpty()
        val currentNickname = nickname.value.orEmpty()

        if (currentEmail.isBlank() || currentCode.isBlank() || currentPassword.isBlank() || currentNickname.isBlank()) {
            _signupSuccess.value = false
            return
        }

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val url = URL("http://13.125.230.122:8080/api/auth/signup")
                    val connection = url.openConnection() as HttpURLConnection

                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json")
                    connection.doOutput = true

                    val jsonBody = JSONObject().apply {
                        put("email", currentEmail)
                        put("code", currentCode)
                        put("password", currentPassword)
                        put("nickname", currentNickname)
                    }

                    val outputStreamWriter = OutputStreamWriter(connection.outputStream)
                    outputStreamWriter.write(jsonBody.toString())
                    outputStreamWriter.flush()
                    outputStreamWriter.close()

                    val responseCode = connection.responseCode
                    connection.disconnect()

                    responseCode in 200..299
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
            }

            _signupSuccess.value = result
        }
    }
}
