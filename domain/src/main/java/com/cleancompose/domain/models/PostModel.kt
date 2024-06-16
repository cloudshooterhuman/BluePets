package com.cleancompose.domain.models

data class PostModel(
    val id: String,
    val text: String,
    val imageUrl: String,
    val publishDate: String,
    val owner: OwnerPreviewModel,
)