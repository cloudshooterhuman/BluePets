package com.cleancompose.data.mappers


import com.cleancompose.api.models.PostDTO
import com.cleancompose.domain.models.PostModel
import javax.inject.Inject

private val  DATE_TIME_FORMATTER : String = "d MMMM yyyy HH:mm:ss"

class PostMapper @Inject constructor(
    private val ownerPreviewMapper: OwnerPreviewMapper,
) {

    fun fromListDto(postPreviews: List<PostDTO>): List<PostModel> =
        postPreviews.map { fromDto(it) }

    fun fromDto(postPreview: PostDTO) = PostModel(
        id = postPreview.id,
        text = postPreview.text,
        imageUrl = postPreview.image,
        publishDate = postPreview.publishDate,
        owner = ownerPreviewMapper.fromDto(postPreview.owner)
    )

    /* fixme need to moved to UI ViewObject model
    fun formatForHuman(publishDate: String): String {
        val instant = Instant.parse(publishDate)
        val datetimeInUtc: LocalDateTime = instant.toLocalDateTime(TimeZone.UTC)
        val formattedDateTime = dateTimeFormat.format(datetimeInUtc)

        return formattedDateTime
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    val dateTimeFormat = LocalDateTime.Format {
        byUnicodePattern(DATE_TIME_FORMATTER)
    }*/

}