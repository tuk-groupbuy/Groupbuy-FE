package com.tuk.tugether.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.tugether.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class ProfileViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val url = URL("http://13.125.230.122:8080/api/users/profile?userId=$userId")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.connectTimeout = 5000
                    connection.readTimeout = 5000

                    if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                        val stream = connection.inputStream.bufferedReader().readText()
                        val json = JSONObject(stream)

                        val nickname = json.getString("nickname")
                        val email = json.getString("email")

                        User(userId, nickname, email)
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }

            result?.let {
                _user.value = it
            }
        }
    }
}
