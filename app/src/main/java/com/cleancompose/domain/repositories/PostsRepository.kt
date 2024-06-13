package com.cleancompose.domain.repositories

import com.cleancompose.domain.ResultOf
import com.cleancompose.domain.models.PostModel

interface PostsRepository {
    suspend fun getPosts(postId: Int): ResultOf<List<PostModel>>
}