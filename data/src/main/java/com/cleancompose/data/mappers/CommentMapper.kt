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
        publishDate = period(calculatePeriod(comment.publishDate, now)),
    )

    private fun calculatePeriod(publishDate: String, now: Instant): Duration {
        val ins = Instant.parse(publishDate)
        return Duration.between(ins, now)
    }

    private fun period(duration: Duration): String {
        return if (duration.toDays() / 365 > 1) {
            return " ${duration.toDays().div(365)} An"
        } else if (duration.toDays() / 30 > 1) {
            return "${duration.toDays().div(30)} mois"
        } else {
            "${duration.toDays()} Jours"
        }
    }
}
