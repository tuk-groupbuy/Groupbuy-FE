package com.tuk.tugether.domain.model

data class User(
    val name: String = "이름 없음",
    val email: String = "이메일 없음",
    val joinDate: String = "가입일 없음",
    val profileImageUrl: String = ""
)
