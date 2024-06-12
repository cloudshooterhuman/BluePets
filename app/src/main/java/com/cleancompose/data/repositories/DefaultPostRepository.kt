package com.cleancompose.data.repositories

import com.cleancompose.api.services.PostService
import com.cleancompose.data.mappers.PostMapper
import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultPostRepository @Inject constructor(
    private val postService: PostService, private val postMapper: PostMapper,
) : PostsRepository {
    override fun getPosts(postId: Int): Flow<List<PostModel>> = flow {
        postService.getPosts(postId).let {
            if (it.isSuccessful) {
                val fromListDto = postMapper.fromListDto(postService.getPosts(postId).body()!!.data)
                emit(fromListDto)
            } else {
                emit(emptyList())
            }
        }
    }


}
