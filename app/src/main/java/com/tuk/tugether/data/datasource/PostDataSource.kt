package com.tuk.tugether.data.datasource

import com.tuk.tugether.data.dto.request.post.DeleteJoinPostRequestDto
import com.tuk.tugether.data.dto.request.post.JoinPostRequestDto
import com.tuk.tugether.data.dto.request.post.UpdatePostRequestDto
import com.tuk.tugether.data.dto.response.post.GetAllPostResponseDto
import com.tuk.tugether.data.dto.response.post.GetPostDetailResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PostDataSource {
    suspend fun createPost(dto: RequestBody, file: MultipartBody.Part): String
    suspend fun getAllPosts(): List<GetAllPostResponseDto>
    suspend fun getPostDetail(postId: Long, requesterId: Long): GetPostDetailResponseDto
    suspend fun updatePost(postId: Long, body: UpdatePostRequestDto): String
    suspend fun deletePost(postId: Long): String
    suspend fun joinPost(body: JoinPostRequestDto): String
    suspend fun deleteJoinPost(body: DeleteJoinPostRequestDto): String
}
