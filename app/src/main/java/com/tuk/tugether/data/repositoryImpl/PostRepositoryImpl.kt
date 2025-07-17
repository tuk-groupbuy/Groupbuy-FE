package com.tuk.tugether.data.repositoryImpl

import com.tuk.tugether.data.datasource.PostDataSource
import com.tuk.tugether.data.dto.response.post.toModel
import com.tuk.tugether.domain.model.request.post.DeleteJoinPostRequestModel
import com.tuk.tugether.domain.model.request.post.JoinPostRequestModel
import com.tuk.tugether.domain.model.request.post.UpdatePostRequestModel
import com.tuk.tugether.domain.model.response.post.GetAllPostResponseModel
import com.tuk.tugether.domain.model.response.post.GetPostDetailResponseModel
import com.tuk.tugether.domain.repository.PostRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postDataSource: PostDataSource
) : PostRepository {

    override suspend fun createPost(dto: RequestBody, file: MultipartBody.Part): Result<String> =
        runCatching { postDataSource.createPost(dto, file) }

    override suspend fun getAllPosts(): Result<List<GetAllPostResponseModel>> =
        runCatching { postDataSource.getAllPosts().map { it.toModel() } }

    override suspend fun getPostDetail(postId: Long, requesterId: Long): Result<GetPostDetailResponseModel> =
        runCatching { postDataSource.getPostDetail(postId, requesterId).toModel() }

    override suspend fun updatePost(postId: Long, body: UpdatePostRequestModel): Result<String> =
        runCatching { postDataSource.updatePost(postId, body.toDto()) }

    override suspend fun deletePost(postId: Long): Result<String> =
        runCatching { postDataSource.deletePost(postId) }

    override suspend fun joinPost(request: JoinPostRequestModel): Result<String> =
        runCatching { postDataSource.joinPost(request.toDto()) }

    override suspend fun deleteJoinPost(request: DeleteJoinPostRequestModel): Result<String> =
        runCatching { postDataSource.deleteJoinPost(request.toDto()) }
}


