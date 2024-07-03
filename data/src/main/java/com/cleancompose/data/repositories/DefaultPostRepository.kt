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

import android.os.Build
import androidx.annotation.RequiresExtension
import com.cleancompose.api.services.PostService
import com.cleancompose.data.mappers.PostMapper
import com.cleancompose.domain.models.NetworkError
import com.cleancompose.domain.models.NetworkException
import com.cleancompose.domain.models.NetworkResult
import com.cleancompose.domain.models.NetworkSuccess
import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.repositories.PostsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPostRepository @Inject constructor(
    private val postService: PostService,
    private val postMapper: PostMapper,
) : PostsRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getPosts(page: Int): NetworkResult<List<PostModel>> {
        return when (val postsResponse = postService.getPosts(page)) {
            is NetworkError -> {
                NetworkError(postsResponse.code, postsResponse.message)
            }

            is NetworkException -> {
                NetworkException(postsResponse.e)
            }

            is NetworkSuccess -> {
                // Log.e("myapp", "DefaultPostRepository ${Thread.currentThread().name}")
                NetworkSuccess(postMapper.fromListDto(postsResponse.data.data))
            }
        }
    }

    override suspend fun getPostsByTag(tagId: String, page: Int): NetworkResult<List<PostModel>> {
        return when (val postsByTagResponse = postService.getPostsByTag(tagId, page)) {
            is NetworkError -> {
                NetworkError(postsByTagResponse.code, postsByTagResponse.message)
            }

            is NetworkException -> {
                NetworkException(postsByTagResponse.e)
            }

            is NetworkSuccess -> {
                NetworkSuccess(postMapper.fromListDto(postsByTagResponse.data.data))
            }
        }
    }
}
