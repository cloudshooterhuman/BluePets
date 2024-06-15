package com.cleancompose.api

import com.cleancompose.api.services.PostService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class DummyApi @Inject constructor() {
    companion object {
        const val APP_ID = "665f91372bdeb7ac1f668bc7"
        const val API_URL = "https://dummyapi.io/data/v1/"
    }

    val postService: PostService

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        postService = retrofit.create(PostService::class.java)
    }

}