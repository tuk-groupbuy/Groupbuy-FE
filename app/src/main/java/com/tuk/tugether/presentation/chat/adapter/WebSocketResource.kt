package com.tuk.tugether.presentation.chat.adapter

import okhttp3.Response

sealed class WebSocketResource {
    data object Open :WebSocketResource()
    data object Enter : WebSocketResource()
    data class Closing(val reason: String) : WebSocketResource()
    data class Failure(val response: Response?) : WebSocketResource()
}