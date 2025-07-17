package com.tuk.tugether.data.dto.response.post

import com.tuk.tugether.domain.model.response.post.GetAllPostResponseModel

data class SearchPostResponseDto(
    val posts: List<GetAllPostResponseDto>
)

fun SearchPostResponseDto.toModel(): List<GetAllPostResponseModel> {
    return posts.map { it.toModel() }
}

