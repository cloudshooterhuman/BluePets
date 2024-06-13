package com.cleancompose.data.mappers

import com.cleancompose.data.mappers.ModelDataFactory.getPostDTO
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
