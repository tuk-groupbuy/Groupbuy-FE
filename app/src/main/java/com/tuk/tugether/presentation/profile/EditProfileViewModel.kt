package com.tuk.tugether.presentation.profile

import android.util.Log
import androidx.lifecycle.*
import com.tuk.tugether.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class EditProfileViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            val fetchedUser = fetchUserFromServer(userId) ?: defaultUser(userId)
            _user.value = fetchedUser
        }
    }

    private suspend fun fetchUserFromServer(userId: String): User? = withContext(Dispatchers.IO) {
        try {
            val baseUrl = "http://13.125.230.122:8080/api/users/profile"
            val encodedUserId = URLEncoder.encode(userId, "UTF-8")
            val fullUrl = "$baseUrl?userId=$encodedUserId"
            Log.d("ServerRequest", "요청 URL: $fullUrl")  // ⭐ 요청 URL 출력

            val url = URL(fullUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val responseCode = connection.responseCode
            Log.d("ServerResponse", "응답 코드: $responseCode")  // ⭐ 응답 코드 출력

            if (responseCode == 200) {
                val stream = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(stream)

                val nickname = json.optString("nickname", "사용자 이름")
                val email = json.optString("email", "이메일 정보 없음")

                User(id = userId, nickname = nickname, email = email, joinDate = "2025-07-17")
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    private fun defaultUser(userId: String) = User(
        id = userId,
        nickname = "사용자 이름",
        email = "이메일 정보 없음",
        joinDate = "가입일 정보 없음"
    )
}
