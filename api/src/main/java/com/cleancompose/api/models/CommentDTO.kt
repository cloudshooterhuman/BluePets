package com.cleancompose.api.models


data class CommentDTO(
    val id: String,
    val message: String,
    val post: String,
    val publishDate: String,
    val owner: UserPreviewDTO,
)