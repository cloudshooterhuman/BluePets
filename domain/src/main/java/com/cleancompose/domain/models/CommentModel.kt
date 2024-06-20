package com.cleancompose.domain.models

import org.threeten.bp.Duration

data class CommentModel(
    val id: String,
    val message: String,
    val post: String,
    val owner: OwnerPreviewModel,
    val publishDate: String,
)