package com.tuk.tugether.presentation.home.adapter

data class Alarm(
    val title: String,
    val current: Int,
    val max: Int,
    val requests: List<AlarmRequest>
)