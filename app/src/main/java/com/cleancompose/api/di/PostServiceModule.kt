package com.cleancompose.api.di

import android.content.Context
import com.cleancompose.api.DummyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PostServiceModule {
    @Provides
    fun providePostService(@ApplicationContext appContext: Context) = DummyApi(appContext).postService
}