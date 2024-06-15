package com.cleancompose.data.di

import com.cleancompose.data.repositories.DefaultPostRepository
import com.cleancompose.domain.repositories.PostsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PostPreviewRepositoryModule {

    @Binds
    internal abstract fun bindPostPreviewRepository(impl: DefaultPostRepository): PostsRepository
}