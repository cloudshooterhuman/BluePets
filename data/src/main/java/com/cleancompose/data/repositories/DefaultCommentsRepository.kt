/*
 * Copyright 2024 Abdellah Selassi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cleancompose.data.repositories

import com.cleancompose.api.services.PostService
import com.cleancompose.data.mappers.CommentMapper
import com.cleancompose.domain.models.CommentModel
import com.cleancompose.domain.models.NetworkError
import com.cleancompose.domain.models.NetworkException
import com.cleancompose.domain.models.NetworkResult
import com.cleancompose.domain.models.NetworkSuccess
import com.cleancompose.domain.repositories.CommentsRepository
import org.threeten.bp.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCommentsRepository @Inject constructor(
    private val postService: PostService,
    private val commentMapper: CommentMapper,
) : CommentsRepository {

    override suspend fun getComments(postId: String): NetworkResult<List<CommentModel>> {
        return when (val commentsResponse = postService.getComment(postId)) {
            is NetworkError -> {
                NetworkError(commentsResponse.code, commentsResponse.message)
            }

            is NetworkException -> {
                NetworkException(commentsResponse.e)
            }

            is NetworkSuccess -> {
                NetworkSuccess(commentMapper.fromListDto(commentsResponse.data.data, Instant.now()))
            }
        }
    }
}
