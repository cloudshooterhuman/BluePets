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
    private val postService: PostService, private val postMapper: PostMapper,
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
}
