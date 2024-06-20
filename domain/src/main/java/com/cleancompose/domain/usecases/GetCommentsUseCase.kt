package com.cleancompose.domain.usecases

import com.cleancompose.domain.repositories.CommentsRepository
import javax.inject.Inject


class GetCommentsUseCase @Inject constructor(private val commentsRepository: CommentsRepository) {
    suspend fun invoke(postId: String) = commentsRepository.getComments(postId = postId)
}