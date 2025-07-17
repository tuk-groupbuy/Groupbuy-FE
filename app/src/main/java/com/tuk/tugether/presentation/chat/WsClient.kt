package com.tuk.tugether.presentation.chat

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.tuk.tugether.data.dto.request.chat.ChatMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.*
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.frame.FrameBody
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import javax.inject.Inject

class WsClient @Inject constructor(
//    private val viewModel: ChatViewModel,
//    private val stompClient: StompClient,
//    private val okHttpClient: OkHttpClient,
//    private val request: Request,
//    private val spf: SharedPreferences

    private val stompClient: StompClient,
    private val spf: SharedPreferences,
    private val scope: CoroutineScope,
    private val url: String,
    private val onMessageReceived: () -> Unit

) {
//    private var webSocket: WebSocket? = null
//    private var nickname = spf.getString("nickName", "김태건") ?: "김태건"
//    private val roomId = viewModel.chatRoomId.value
    private var session: StompSession? = null
    private val nickname: String get() = spf.getString("user_nickname", "") ?: ""
    private var roomId: Long = -1L // 기본값

    private var subscriptionJob: Job? = null
//    fun connectWebSocket() {
//        webSocket = okHttpClient.newWebSocket(request, this)
//    }
    fun setRoomId(id: Long) {
        roomId = id
    }
    fun connectWebSocket() {
        scope.launch {
            try {
                session = stompClient.connect(url)
                Log.d("WebSocket", "STOMP connected")
                subscribeToMessages()
//                enterChatRoom("") // 자동 입장 메시지
            } catch (e: Exception) {
                Log.e("WebSocket", "Connection failed: ${e.message}")
            }
        }
    }

    private fun subscribeToMessages() {
        subscriptionJob = scope.launch {
            session?.subscribe(
                StompSubscribeHeaders(destination = "/topic/chat/$roomId")
            )?.collect { frame ->
                val message = frame.bodyAsText
                Log.d("WebSocket", "Received: $message")
                if (!message.contains("님이 입장했습니다.")) {
                    onMessageReceived()
                }
            }
        }
    }

    fun enterChatRoom(text: String) {
        sendChatMessage(ENTER, text)
    }

    fun sendMessage(text: String) {
        sendChatMessage(TALK, text)
    }

    private fun sendChatMessage(type: String, content: String) {
        val chatMessage = ChatMessage(
            type = type,
            sender = nickname,
            chatRoomId = roomId,
            content = content
        )
        val json = Gson().toJson(chatMessage)

        scope.launch {
            try {
                session?.send(
                    StompSendHeaders(destination = "/app/chat/send"),
                    FrameBody.Text(json)
                )
            } catch (e: Exception) {
                Log.e("WebSocket", "Send failed: ${e.message}")
            }
        }
    }

    fun closeSocket() {
        scope.launch {
            try {
                subscriptionJob?.cancel()
                session?.disconnect()
                Log.d("WebSocket", "Connection closed")
            } catch (e: Exception) {
                Log.e("WebSocket", "Close failed: ${e.message}")
            }
        }
    }

//    fun enterChatRoom(text: String){
//        val chatMessage = ChatMessage(
//            type = ENTER,
//            sender = nickname,
//            chatRoomId = roomId,
//            content = text)
//        val gson = Gson()
//        val jsonMessage = gson.toJson(chatMessage)
//        webSocket?.send(jsonMessage)
//    }

//    fun sendMessage(text: String){
//        val chatMessage = ChatMessage(
//            type = TALK,
//            sender = nickname,
//            chatRoomId = roomId,
//            content = text)
//
//        val gson = Gson()
//        val jsonMessage = gson.toJson(chatMessage)
//        webSocket?.send(jsonMessage)
//    }

//    fun closeSocket(){
//        webSocket?.close(1000, "")
//    }

//    override fun onOpen(webSocket: WebSocket, response: Response) {
//        Log.d("WebSocket", "Connection opened: $response")
//    }
//
//    override fun onMessage(webSocket: WebSocket, text: String) {
//        Log.d("WebSocket","Message received: $text")
//        if (!text.contains("님이 입장했습니다.")) {
//            viewModel.refreshChatLog()
//        }
//    }
//
//    override fun onMessage(webSocket: WebSocket, bytes: okio.ByteString) {
//        Log.d("WebSocket","Message received (binary): ${bytes.hex()}")
//    }
//
//    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//        Log.d("WebSocket", "Connection closing: $code / $reason")
//        webSocket.close(code, reason)
//    }
//
//    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
//        Log.d("WebSocket", "Connection closed: $code / $reason")
//    }
//
//    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//        Log.e("WebSocket", "Error: " + t.message)
//            connectWebSocket()
//            enterChatRoom("") // 필요시 다시 입장 시도
//    }

    companion object {
        private const val ENTER = "ENTER"
        private const val TALK = "TALK"
    }

}