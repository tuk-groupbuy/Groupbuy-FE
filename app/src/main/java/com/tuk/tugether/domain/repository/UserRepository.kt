package com.tuk.tugether.domain.repository

import com.tuk.tugether.domain.model.User

interface UserRepository {
    suspend fun getUserProfile(): User
}
