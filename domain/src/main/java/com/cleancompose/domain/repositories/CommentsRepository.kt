package com.cleancompose.domain.repositories

import com.cleancompose.domain.ResultOf
import com.cleancompose.domain.models.CommentModel

interface CommentsRepository {
    suspend fun getComments(postId: String): ResultOf<List<CommentModel>>
}