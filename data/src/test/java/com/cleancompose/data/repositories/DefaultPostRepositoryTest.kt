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
package com.cleancompose.data.repositories

import com.cleancompose.ModelDataFactory.getPostDTO
import com.cleancompose.api.models.Page
import com.cleancompose.api.services.PostService
import com.cleancompose.data.mappers.PostMapper
import com.cleancompose.domain.models.DomainModelFactory.getDefaultPostModel
import com.cleancompose.domain.models.NetworkError
import com.cleancompose.domain.models.NetworkSuccess
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

private const val HTTP_404_MESSAGE = "HTTP NOT FOUND"

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
            coEvery { postService.getPosts(page) } returns NetworkSuccess(
                Page(
                    data = postDTOs,
                    total = 0u,
                ),
            )
            coEvery { postMapper.fromListDto(postDTOs) } returns expectedPost

            // When
            val actualPost =
                postRepository.getPosts(page) as NetworkSuccess

            // Then
            assertEquals(expectedPost, actualPost.data)
        }

    @Test
    fun `Given a successful response with no post on page 0, When getting posts through repository, Then returns an empty list`() =
        runTest {
            // Given
            val page = 0
            coEvery { postService.getPosts(page) } returns NetworkSuccess(
                Page(
                    data = emptyList(),
                    total = 0u,
                ),
            )
            coEvery { postMapper.fromListDto(emptyList()) } returns emptyList()

            // When
            val post = postRepository.getPosts(page) as NetworkSuccess

            // Then
            assertTrue(post.data.isEmpty())
        }

    @Test
    fun `Given an error response, When getting posts through repository, Then returns an empty list`() =
        runTest {
            // Given
            val page = 0
            coEvery { postService.getPosts(page) } returns NetworkError(
                404,
                HTTP_404_MESSAGE,
            )

            // When
            val failure = postRepository.getPosts(page) as NetworkError

            // Then
            assertEquals(failure.message, HTTP_404_MESSAGE)
            assertEquals(failure.code, 404)
        }
}
