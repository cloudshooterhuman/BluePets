package com.cleancompose.domain.repositories

import com.cleancompose.domain.ResultOf
import com.cleancompose.domain.models.PostModel

interface PostsRepository {
    suspend fun getPosts(page: Int): ResultOf<List<PostModel>>

    suspend fun getPostsByTag(tagId: String, page: Int): ResultOf<List<PostModel>>
}