package com.cleancompose.data.mappers


import com.cleancompose.api.models.UserPreviewDTO
import com.cleancompose.domain.models.OwnerPreviewModel
import javax.inject.Inject

class OwnerPreviewMapper @Inject constructor() {
    fun fromDto(userPreview: UserPreviewDTO) = OwnerPreviewModel(
        id = userPreview.id,
        name = "${userPreview.firstName} ${userPreview.lastName}",
        pictureUrl = userPreview.picture,
    )
}