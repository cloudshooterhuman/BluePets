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
package com.cleancompose.data.mappers

import com.cleancompose.ModelDataFactory.getPostDTO
import com.cleancompose.domain.models.DomainModelFactory.getDefaultOwnerPreviewModel
import com.cleancompose.domain.models.DomainModelFactory.getDefaultPostModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class PostMapperTest {
    private val ownerPreviewMapper: OwnerPreviewMapper = mockk()

    private val postMapper = PostMapper(ownerPreviewMapper)

    @Test
    fun `Given a post DTO, When mapper is called, Then returns a post model`() {
        // Given
        val postId = "23"
        val postDTO = getPostDTO(postId)
        every { ownerPreviewMapper.fromDto(any()) } returns getDefaultOwnerPreviewModel()

        // When
        val actualPostPreviewModels = postMapper.fromDto(postDTO)
        val expectedPostPreviewModels = getDefaultPostModel(postId)

        // Then
        assertEquals(expectedPostPreviewModels, actualPostPreviewModels)
    }
}
