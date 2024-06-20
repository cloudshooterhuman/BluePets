package com.cleancompose.data.di

import com.cleancompose.data.repositories.DefaultCommentsRepository
import com.cleancompose.domain.repositories.CommentsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CommentsRepositoryModule {

    @Binds
    internal abstract fun bindCommentsPreviewRepository(impl: DefaultCommentsRepository): CommentsRepository
}