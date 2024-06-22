package com.cleancompose.domain.models

data class CommentModel(
    val id: String,
    val message: String,
    val post: String,
    val owner: OwnerPreviewModel,
    val publishDate: String,
)