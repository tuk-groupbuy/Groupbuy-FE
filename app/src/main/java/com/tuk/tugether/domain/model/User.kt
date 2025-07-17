package com.tuk.tugether.domain.model

data class User(
    val id: String = "",
    val email: String = "기본",
    val nickname: String = "기본",
    val joinDate: String = "2023-01-01"
)