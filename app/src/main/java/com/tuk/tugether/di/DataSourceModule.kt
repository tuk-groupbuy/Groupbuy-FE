package com.tuk.tugether.di

import com.tuk.tugether.data.datasource.NotificationDataSource
import com.tuk.tugether.data.datasourceImpl.NotificationDataSourceImpl
import com.tuk.tugether.data.datasource.PostDataSource
import com.tuk.tugether.data.datasourceImpl.PostDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providePostDataSource(postDataSourceImpl: PostDataSourceImpl): PostDataSource =
        postDataSourceImpl

    @Provides
    @Singleton
    fun provideNotificationDataSource(notificationDataSourceImpl: NotificationDataSourceImpl): NotificationDataSource =
        notificationDataSourceImpl
}