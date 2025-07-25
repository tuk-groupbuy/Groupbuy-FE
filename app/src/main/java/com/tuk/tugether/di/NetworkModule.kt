package com.tuk.tugether.di

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.tuk.tugether.R
import com.tuk.tugether.TUgetherApplication
import com.tuk.tugether.util.network.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// @Module: 모듈은 Hilt에게 특정 객체를 만드는 방법을 알려주는 클래스이다.
@Module
// @InstallIn : 해당 컴포넌트의 모듈이 설치 되게 한다.
//별도로 Scope를 지정해주면 해당하는 HiltComponent의 수명동안 같은 인스턴스를 공유해 바인딩이 요청될 때마다 같은 인스턴스를 제공할 수 있다.
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val NETWORK_EXCEPTION_OFFLINE_CASE = "network status is offline"
    const val NETWORK_EXCEPTION_BODY_IS_NULL = "result.json body is null"

    // @Provides : 모듈 클래스 내에 해당 객체 생성
    @Provides
    @Singleton
    fun providesConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .create()
        )
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        authInterceptor: AuthInterceptor // 인터셉터 주입
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {
            addInterceptor(authInterceptor) // 인증 인터셉터 추가
            addInterceptor(interceptor)
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TUgetherApplication.getString(R.string.base_url))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(sharedPreferences: SharedPreferences): AuthInterceptor =
        AuthInterceptor(sharedPreferences)

}