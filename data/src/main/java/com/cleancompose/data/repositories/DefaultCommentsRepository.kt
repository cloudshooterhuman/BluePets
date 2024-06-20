package com.cleancompose.data.repositories

import com.cleancompose.api.services.PostService;
import com.cleancompose.data.mappers.CommentMapper
import com.cleancompose.domain.ResultOf
import com.cleancompose.domain.models.CommentModel
import com.cleancompose.domain.repositories.CommentsRepository
import org.threeten.bp.Instant
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class DefaultCommentsRepository @Inject constructor(
    private val postService: PostService, private val commentMapper: CommentMapper,
) : CommentsRepository {


    override suspend fun getComments(postId: String): ResultOf<List<CommentModel>> {
        return try {
            postService.getComment(postId).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    return ResultOf.Success(
                        commentMapper.fromListDto(
                            response.body()!!.data,
                            Instant.now()
                        )
                    )
                } else {
                    return ResultOf.Failure(
                        response.errorBody().toString(),
                        Throwable(response.message())
                    )
                }
            }
        } catch (e: IOException) {
            ResultOf.Failure("[IO] error please retry", e)
        } catch (e: HttpException) {
            ResultOf.Failure("[HTTP] error please retry", e)
        }
    }
}