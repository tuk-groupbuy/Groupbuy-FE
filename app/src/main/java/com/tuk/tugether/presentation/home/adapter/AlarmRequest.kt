package com.tuk.tugether.presentation.home.adapter

data class AlarmRequest(
    val senderNickname: String,
    val createdAt: String,
    val postId: Long,
    val userId: Long
)
