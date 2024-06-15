package com.cleancompose.api.services

import com.cleancompose.api.DummyApi
import com.cleancompose.api.models.Page
import com.cleancompose.api.models.PostDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PostService {
    @GET("post?limit=23")
    @Headers("app-id: ${DummyApi.APP_ID}")
    suspend fun getPosts(@Query("page") page: Int): Response<Page<PostDTO>>
}