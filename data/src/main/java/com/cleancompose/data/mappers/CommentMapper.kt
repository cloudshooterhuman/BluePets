package com.cleancompose.data.mappers


import com.cleancompose.api.models.CommentDTO
import com.cleancompose.domain.models.CommentModel
import org.threeten.bp.Duration
import org.threeten.bp.Instant

import javax.inject.Inject


class CommentMapper @Inject constructor(
    private val ownerPreviewMapper: OwnerPreviewMapper,
) {
    fun fromListDto(comments: List<CommentDTO>, now: Instant): List<CommentModel> =
        comments.map { fromDto(it, now) }

    private fun fromDto(comment: CommentDTO, now: Instant) = CommentModel(
        id = comment.id,
        message = comment.message,
        post = comment.post,
        owner = ownerPreviewMapper.fromDto(comment.owner),
        publishDate = calculatePeriod(comment.publishDate, now)
    )

    fun calculatePeriod(publishDate: String, now: Instant): Duration {
        val ins = Instant.parse(publishDate)
        return Duration.between(ins, now)
    }
}