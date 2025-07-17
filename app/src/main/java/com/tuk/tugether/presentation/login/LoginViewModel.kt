package com.tuk.tugether.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject


data class UserInfo(val userId: String, val nickname: String)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    private val client = OkHttpClient()

    private val _userInfo = MutableLiveData<UserInfo?>()
    val userInfo: LiveData<UserInfo?> = _userInfo

    fun login() {
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()

        if (emailValue.isBlank() || passwordValue.isBlank()) {
            _loginResult.value = "입력값을 모두 입력해주세요."
            return
        }

        // JSON 문자열 생성
        val json = JSONObject().apply {
            put("email", emailValue)
            put("password", passwordValue)
        }.toString()

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("http://13.125.230.122:8080/api/auth/login")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _loginResult.postValue("로그인 실패: 네트워크 오류")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonStr = response.body?.string()
                    val jsonObj = JSONObject(jsonStr)
                    val userId = jsonObj.getString("userId")
                    val nickname = jsonObj.getString("nickname")
                    onLoginSuccess(userId, nickname)
                    _loginResult.postValue("로그인 성공")
                } else {
                    _loginResult.postValue("이메일 또는 비밀번호가 올바르지 않습니다.")
                }
            }
        })
    }
    fun onLoginSuccess(userId: String, nickname: String) {
        _userInfo.postValue(UserInfo(userId, nickname))
    }
}
