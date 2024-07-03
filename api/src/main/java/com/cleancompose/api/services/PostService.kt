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
package com.cleancompose.api.services

import com.cleancompose.api.DummyApi
import com.cleancompose.api.models.CommentDTO
import com.cleancompose.api.models.Page
import com.cleancompose.api.models.PostDTO
import com.cleancompose.domain.models.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {
    @GET("post?limit=23")
    @Headers("app-id: ${DummyApi.APP_ID}")
    suspend fun getPosts(@Query("page") page: Int): NetworkResult<Page<PostDTO>>

    @GET("post/{id}/comment")
    @Headers("app-id: ${DummyApi.APP_ID}")
    suspend fun getComment(@Path("id") id: String): NetworkResult<Page<CommentDTO>>

    @GET("tag/{id}/post?limit=23")
    @Headers("app-id: ${DummyApi.APP_ID}")
    suspend fun getPostsByTag(
        @Path("id") id: String,
        @Query("page") page: Int,
    ): NetworkResult<Page<PostDTO>>
}
