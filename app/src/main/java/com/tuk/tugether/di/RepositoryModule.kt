package com.tuk.tugether.di

import android.app.Application
import android.content.Context
import com.tuk.tugether.data.repositoryImpl.NotificationRepositoryImpl
import com.tuk.tugether.data.repositoryImpl.PostRepositoryImpl
import com.tuk.tugether.data.repositoryImpl.TestRepositoryImpl
import com.tuk.tugether.data.repositoryImpl.chat.ChatRepositoryImpl
import com.tuk.tugether.data.service.TestService
import com.tuk.tugether.domain.repository.NotificationRepository
import com.tuk.tugether.domain.repository.PostRepository
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

    @Provides
    @ViewModelScoped
    fun providesTestRepository(
        testService: TestService
    ): TestRepository = TestRepositoryImpl(testService)

    @ViewModelScoped
    @Provides
    fun providesNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository = notificationRepositoryImpl

    @ViewModelScoped
    @Provides
    fun providesPostRepository(
        postRepositoryImpl: PostRepositoryImpl
    ): PostRepository = postRepositoryImpl

    @Provides
    @ViewModelScoped
    fun providesChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository = chatRepositoryImpl
  
}