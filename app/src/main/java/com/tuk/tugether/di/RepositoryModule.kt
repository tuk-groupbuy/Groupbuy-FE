package com.tuk.tugether.di

import android.app.Application
import android.content.Context
import com.tuk.tugether.data.repositoryImpl.TestRepositoryImpl
import com.tuk.tugether.data.repositoryImpl.chat.ChatRepositoryImpl
import com.tuk.tugether.data.service.TestService
import com.tuk.tugether.domain.repository.TestRepository
import com.tuk.tugether.domain.repository.chat.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application

    // 스코프 애노테이션이 있음
    // 해당하는 Hilt 컴포넌트의 수명동안 매 요청에 동일 인스턴스를 반환
    // 다음의 경우 viewModel의 수명동안 동일 인스턴스를 반환
    @Provides
    @ViewModelScoped
    fun providesTestRepository(
        testService: TestService
    ): TestRepository = TestRepositoryImpl(testService)

    @Provides
    @ViewModelScoped
    fun providesChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository = chatRepositoryImpl
}