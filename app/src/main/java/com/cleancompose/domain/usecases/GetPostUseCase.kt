package com.cleancompose.domain.usecases

import com.cleancompose.domain.repositories.PostsRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(private val postsRepository: PostsRepository) {
    suspend fun invoke(id : Int) = postsRepository.getPosts(id)
}