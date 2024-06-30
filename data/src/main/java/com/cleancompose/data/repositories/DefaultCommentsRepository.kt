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
import com.cleancompose.domain.ResultOf
import com.cleancompose.domain.models.CommentModel
import com.cleancompose.domain.repositories.CommentsRepository
import org.threeten.bp.Instant
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCommentsRepository @Inject constructor(
    private val postService: PostService,
    private val commentMapper: CommentMapper,
) : CommentsRepository {

    override suspend fun getComments(postId: String): ResultOf<List<CommentModel>> {
        return try {
            postService.getComment(postId).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    return ResultOf.Success(
                        commentMapper.fromListDto(
                            response.body()!!.data,
                            Instant.now(),
                        ),
                    )
                } else {
                    return ResultOf.Failure(
                        response.errorBody().toString(),
                        Throwable(response.message()),
                    )
                }
            }
        } catch (e: IOException) {
            ResultOf.Failure("[IO] error please retry", e)
        } catch (e: HttpException) {
            ResultOf.Failure("[HTTP] error please retry", e)
        }
    }
}
