package com.tuk.tugether.domain.repository

import com.tuk.tugether.domain.model.request.post.DeleteJoinPostRequestModel
import com.tuk.tugether.domain.model.request.post.JoinPostRequestModel
import com.tuk.tugether.domain.model.request.post.UpdatePostRequestModel
import com.tuk.tugether.domain.model.response.post.GetAllPostResponseModel
import com.tuk.tugether.domain.model.response.post.GetPostDetailResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PostRepository {
    suspend fun createPost(dto: RequestBody, file: MultipartBody.Part): Result<String>
    suspend fun getAllPosts(): Result<List<GetAllPostResponseModel>>
    suspend fun getPostDetail(postId: Long, requesterId: Long): Result<GetPostDetailResponseModel>
    suspend fun updatePost(postId: Long, body: UpdatePostRequestModel): Result<String>
    suspend fun deletePost(postId: Long): Result<String>
    suspend fun joinPost(request: JoinPostRequestModel): Result<String>
    suspend fun deleteJoinPost(request: DeleteJoinPostRequestModel): Result<String>
}
