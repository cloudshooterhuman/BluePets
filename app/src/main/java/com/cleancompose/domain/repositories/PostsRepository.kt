package com.cleancompose.domain.repositories

import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.ResultOf

interface PostsRepository {
    suspend fun getPosts(postId: Int): ResultOf<List<PostModel>>
}