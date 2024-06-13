package com.cleancompose.domain.usecases

import com.cleancompose.domain.repositories.PostsRepository
import javax.inject.Inject


class GetPostUseCase @Inject constructor(private val postRepository: PostsRepository) {
    suspend fun invoke(nextPage: Int) = postRepository.getPosts(nextPage)
}