package com.cleancompose.domain.usecases

import com.cleancompose.domain.repositories.PostsRepository
import javax.inject.Inject


class GetPostByTagUseCase @Inject constructor(private val postRepository: PostsRepository) {
    suspend fun invoke(pagId: String, tagId: Int) = postRepository.getPostsByTag(pagId, tagId)
}