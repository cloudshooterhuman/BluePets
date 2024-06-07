package com.cleancompose.domain.repositories

import com.cleancompose.domain.models.PostModel
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun getPosts(postId: Int): Flow<List<PostModel>>
}