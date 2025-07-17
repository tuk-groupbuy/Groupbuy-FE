package com.tuk.tugether.di

import com.tuk.tugether.data.repositoryImpl.TestRepositoryImpl
import com.tuk.tugether.data.repositoryImpl.post.PostRepositoryImpl
import com.tuk.tugether.data.service.TestService
import com.tuk.tugether.domain.repository.PostRepository
import com.tuk.tugether.domain.repository.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    // 스코프 애노테이션이 있음
    // 해당하는 Hilt 컴포넌트의 수명동안 매 요청에 동일 인스턴스를 반환
    // 다음의 경우 viewModel의 수명동안 동일 인스턴스를 반환
    @ViewModelScoped
    @Provides
    fun providesTestRepository(
        testService: TestService
    ): TestRepository = TestRepositoryImpl(testService)

    @Singleton
    @Provides
    fun providesPostRepository(
        postRepositoryImpl: PostRepositoryImpl
    ): PostRepository = postRepositoryImpl
}