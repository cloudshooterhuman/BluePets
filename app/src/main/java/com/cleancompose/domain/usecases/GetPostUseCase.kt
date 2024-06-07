package com.cleancompose.domain.usecases

import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostUseCase @Inject constructor(private val postsRepository: PostsRepository) {
    fun invoke(id: Int): Flow<List<PostModel>> {
        return postsRepository.getPosts(id)
    }
}