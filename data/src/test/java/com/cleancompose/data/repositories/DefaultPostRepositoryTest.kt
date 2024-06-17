package com.cleancompose.data.repositories

import com.cleancompose.ModelDataFactory.getPostDTO
import com.cleancompose.api.models.Page
import com.cleancompose.api.services.PostService
import com.cleancompose.data.mappers.PostMapper
import com.cleancompose.domain.models.DomainModelFactory.getDefaultPostModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response
import java.util.UUID

class DefaultPostRepositoryTest {
    private val postService: PostService = mockk()
    private val postMapper: PostMapper = mockk()
    private val postRepository =
        DefaultPostRepository(postService, postMapper)

    @Test
    fun `Given a successful response with posts on page 23, When getting posts through repository, Then returns list of Post`() =
        runTest {
            // Given
            val page = 23
            val randomUuids = List(50) { UUID.randomUUID().toString() }
            val postDTOs = randomUuids.map { getPostDTO(it) }
            val expectedPost = randomUuids.map { getDefaultPostModel(it) }
            coEvery { postService.getPosts(page) } returns Response.success(
                Page(
                    data = postDTOs,
                    total = 0u
                )
            )
            coEvery { postMapper.fromListDto(postDTOs) } returns expectedPost

            // When
            val actualPost = postRepository.getPosts(page) as com.cleancompose.domain.ResultOf.Success

            // Then
            assertEquals(expectedPost, actualPost.value)
        }

    @Test
    fun `Given a successful response with no post on page 0, When getting posts through repository, Then returns an empty list`() =
        runTest {
            // Given
            val page = 0
            coEvery { postService.getPosts(page) } returns Response.success(
                Page(
                    data = emptyList(),
                    total = 0u
                )
            )
            coEvery { postMapper.fromListDto(emptyList()) } returns emptyList()

            // When
            val post = postRepository.getPosts(page) as com.cleancompose.domain.ResultOf.Success

            // Then
            assertTrue(post.value.isEmpty())
        }

    @Test
    fun `Given an error response, When getting posts through repository, Then returns an empty list`() =
        runTest {
            // Given
            val page = 0
            coEvery { postService.getPosts(page) } returns Response.error(
                404,
                ResponseBody.create(MediaType.get("application/json"),"HTTP NOT FOUND")
            )

            // When
            val failure = postRepository.getPosts(page) as com.cleancompose.domain.ResultOf.Failure

            // Then
            assertEquals(failure.throwable.message, "Response.error()")
        }
}
