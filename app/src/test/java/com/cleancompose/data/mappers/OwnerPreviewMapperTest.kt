package com.cleancompose.data.mappers

import com.cleancompose.data.mappers.ModelDataFactory.getUserPreviewDTO
import com.cleancompose.domain.models.DomainModelFactory.getDefaultOwnerPreviewModel
import org.junit.Assert.*
import org.junit.Test

class OwnerPreviewMapperTest {
    private val ownerPreviewMapper = OwnerPreviewMapper()

    @Test
    fun `Given user preview DTO, When mapper is called, Then returns owner preview model`() {
        // Given
        val userPreviewDTO = getUserPreviewDTO()

        // When
        val actualUserPreviewModel = ownerPreviewMapper.fromDto(userPreviewDTO)
        val expectedUserPreviewModel = getDefaultOwnerPreviewModel()

        // Then
        assertEquals(expectedUserPreviewModel, actualUserPreviewModel)
    }
}