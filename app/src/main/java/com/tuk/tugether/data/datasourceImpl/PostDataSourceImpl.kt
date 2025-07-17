package com.tuk.tugether.data.datasourceImpl

import com.tuk.tugether.data.datasource.PostDataSource
import com.tuk.tugether.data.dto.request.post.DeleteJoinPostRequestDto
import com.tuk.tugether.data.dto.request.post.JoinPostRequestDto
import com.tuk.tugether.data.dto.request.post.UpdatePostRequestDto
import com.tuk.tugether.data.dto.response.post.GetAllPostResponseDto
import com.tuk.tugether.data.dto.response.post.GetPostDetailResponseDto
import com.tuk.tugether.data.service.PostService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(
private val postService: PostService
) : PostDataSource {
    override suspend fun createPost(dto: RequestBody, file: MultipartBody.Part): String =
        postService.createPost(dto, file)

    override suspend fun getAllPosts(): List<GetAllPostResponseDto> =
        postService.getAllPosts()

    override suspend fun getPostDetail(postId: Long, requesterId: Long): GetPostDetailResponseDto =
        postService.getPostDetail(postId, requesterId)

    override suspend fun updatePost(postId: Long, body: UpdatePostRequestDto): String =
        postService.updatePost(postId, body)

    override suspend fun deletePost(postId: Long): String =
        postService.deletePost(postId)

    override suspend fun joinPost(body: JoinPostRequestDto): String =
        postService.joinPost(body)

    override suspend fun deleteJoinPost(body: DeleteJoinPostRequestDto): String =
        postService.deleteJoinPost(body)
}

