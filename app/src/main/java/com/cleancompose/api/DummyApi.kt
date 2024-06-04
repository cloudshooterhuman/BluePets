package com.cleancompose.api

import android.content.Context
import com.cleancompose.api.services.PostService
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class DummyApi @Inject constructor(
    @ApplicationContext context: Context
) {
    companion object {
        const val APP_ID = "665f91372bdeb7ac1f668bc7"
    }

    val postService: PostService

    init {
        val oktHttpClient = OkHttpClient.Builder()
        //.addInterceptor(NetworkConnectionInterceptor(context))

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyapi.io/data/v1/")
            .client(oktHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        postService = retrofit.create(PostService::class.java)
    }

}