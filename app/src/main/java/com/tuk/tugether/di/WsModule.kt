package com.tuk.tugether.di

import android.content.SharedPreferences
import com.tuk.tugether.R
import com.tuk.tugether.TUgetherApplication
import com.tuk.tugether.presentation.chat.ChatViewModel
import com.tuk.tugether.presentation.chat.WsClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WsModule {

//    @Provides
//    @Singleton
//    @Named("WsClient")
//    fun provideWsOkHttpClient(): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//        return OkHttpClient.Builder()
//            .connectTimeout(10, TimeUnit.SECONDS)
//            .writeTimeout(10, TimeUnit.SECONDS)
//            .readTimeout(0, TimeUnit.SECONDS) // 읽기 타임아웃을 0으로 설정해 WebSocket이 끊기지 않도록 설정
//            .retryOnConnectionFailure(true) // 연결 실패 시 재시도 설정
//            .addInterceptor(interceptor)
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideWebSocketService(@Named("WsClient") okHttpClient: OkHttpClient, viewModel: ChatViewModel, request: Request, spf: SharedPreferences): WsClient {
//        return WsClient(viewModel, okHttpClient, request, spf)
//    }
//
//    @Provides
//    @Singleton
//    fun provideWebSocketRequest(spf: SharedPreferences): Request {
//        return Request.Builder()
//            .url(TUgetherApplication.getString(R.string.ws_url))
//            .build()
//    }

    // CoroutineScope 제공
    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    // Krossbow용 WebSocketClient
    @Provides
    @Singleton
    fun provideKrossbowWebSocketClient(): OkHttpWebSocketClient {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(Duration.ofMinutes(1))
            .pingInterval(Duration.ofSeconds(10))
            .build()
        return OkHttpWebSocketClient(okHttpClient)
    }

    // Krossbow용 StompClient
    @Provides
    @Singleton
    fun provideStompClient(webSocketClient: OkHttpWebSocketClient): StompClient {
        return StompClient(webSocketClient)
    }

    // 실제 사용하는 WsClient
    @Provides
    @Singleton
    fun provideWsClient(
        stompClient: StompClient,
        spf: SharedPreferences,
        coroutineScope: CoroutineScope
    ): WsClient {
        val url = TUgetherApplication.getString(R.string.ws_url)
        return WsClient(
            stompClient = stompClient,
            spf = spf,

            scope = coroutineScope,
            url = url,
            onMessageReceived = {}

        )
    }
}