package com.cleancompose.data.mappers

import com.cleancompose.api.models.PostDTO
import com.cleancompose.domain.models.PostModel
import javax.inject.Inject

class PostMapper @Inject constructor(
    private val ownerPreviewMapper: OwnerPreviewMapper,
) {
    fun fromListDto(postPreviews: List<PostDTO>): List<PostModel> =
        postPreviews.map { fromDto(it) }

    private fun fromDto(postPreview: PostDTO) = PostModel(
        id = postPreview.id,
        text = postPreview.text,
        imageUrl = postPreview.image,
        publishDate = postPreview.publishDate,
        owner = ownerPreviewMapper.fromDto(postPreview.owner)
    )
}