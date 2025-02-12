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
package com.cleancompose.domain.usecases

import com.cleancompose.domain.models.DomainModelFactory.getDefaultPostModel
import com.cleancompose.domain.models.NetworkError
import com.cleancompose.domain.models.NetworkException
import com.cleancompose.domain.models.NetworkSuccess
import com.cleancompose.domain.repositories.PostsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

class GetPostUseCaseTest {
    private val postRepository: PostsRepository = mockk()
    private val getPostUseCase = GetPostUseCase(postRepository)

    @Test
    fun `Given repository returns full list, When GetPostUseCase is invoked, Then returns full list`() =
        runTest {
            // Given
            val expectedPostsList = List(23) {
                getDefaultPostModel(UUID.randomUUID().toString())
            }

            coEvery { postRepository.getPosts(0) } returns NetworkSuccess(expectedPostsList)

            // When
            val actualPostsList = getPostUseCase.invoke(0) as NetworkSuccess

            // Then
            assertEquals(expectedPostsList, actualPostsList.data)
        }

    @Test
    fun `Given repository returns empty list, When GetPostUseCase is invoked, Then returns empty list`() =
        runTest {
            // Given
            coEvery { postRepository.getPosts(0) } returns NetworkSuccess(emptyList())

            // When
            val postsList = getPostUseCase.invoke(0) as NetworkSuccess

            // Then
            assertTrue(postsList.data.isEmpty())
        }

    // Unit test exception case

    @Test
    fun `Given repository returns exception, When GetPostUseCase is invoked, Then returns exception`() =
        runTest {
            val e: Throwable = Throwable("exception")
            // Given
            coEvery { postRepository.getPosts(0) } returns NetworkException(e)

            // When
            val exception = getPostUseCase.invoke(0) as NetworkException

            // Then
            assertEquals(exception.e.message, "exception")
        }

    // Unit test error case
    @Test
    fun `Given repository returns error, When GetPostUseCase is invoked, Then returns error`() =
        runTest {
            // Given
            coEvery { postRepository.getPosts(0) } returns NetworkError(500, "error")

            // When
            val error = getPostUseCase.invoke(0) as NetworkError

            // Then
            assertEquals(error.code, 500)
        }

}
