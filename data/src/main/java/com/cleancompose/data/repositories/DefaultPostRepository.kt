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

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.cleancompose.api.services.PostService
import com.cleancompose.data.mappers.PostMapper
import com.cleancompose.domain.ResultOf
import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.repositories.PostsRepository
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPostRepository @Inject constructor(
    private val postService: PostService,
    private val postMapper: PostMapper,
) : PostsRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getPosts(page: Int): ResultOf<List<PostModel>> {
        return try {
            postService.getPosts(page).let {
                if (it.isSuccessful && it.body() != null) {
                    val fromListDto =
                        postMapper.fromListDto(postService.getPosts(page).body()!!.data)
                    ResultOf.Success(fromListDto)
                } else {
                    ResultOf.Failure(it.errorBody().toString(), Throwable(it.message()))
                }
            }
        } catch (e: IOException) {
            ResultOf.Failure("[IO] error please retry", e)
        } catch (e: HttpException) {
            ResultOf.Failure("[HTTP] error please retry", e)
        }
    }

    override suspend fun getPostsByTag(tagId: String, page: Int): ResultOf<List<PostModel>> {
        return try {
            postService.getPostsByTag(tagId, page).let {
                if (it.isSuccessful && it.body() != null) {
                    val fromListDto =
                        postMapper.fromListDto(postService.getPostsByTag(tagId, page).body()!!.data)
                    ResultOf.Success(fromListDto)
                } else {
                    ResultOf.Failure(it.errorBody().toString(), Throwable(it.message()))
                }
            }
        } catch (e: IOException) {
            ResultOf.Failure("[IO] error please retry", e)
        } catch (e: HttpException) {
            ResultOf.Failure("[HTTP] error please retry", e)
        }
    }
}
