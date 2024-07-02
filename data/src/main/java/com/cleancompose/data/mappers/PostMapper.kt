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

import com.cleancompose.api.models.PostDTO
import com.cleancompose.domain.models.PostModel
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

private val DATE_TIME_FORMATTER: String = "d MMMM yyyy HH:mm:ss"

class PostMapper @Inject constructor(
    private val ownerPreviewMapper: OwnerPreviewMapper,
) {

    fun fromListDto(postPreviews: List<PostDTO>): List<PostModel> =
        postPreviews.map { fromDto(it) }

    fun fromDto(postPreview: PostDTO) = PostModel(
        id = postPreview.id,
        text = postPreview.text,
        imageUrl = postPreview.image,
        publishDate = formatForHuman(postPreview.publishDate),
        owner = ownerPreviewMapper.fromDto(postPreview.owner),
    )

    // fixme need to be moved to th mapper of View Object once created.
    private fun formatForHuman(publishDate: String): String {
        val instant = Instant.parse(publishDate)
        return DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)
            .format(
                LocalDateTime.ofInstant(instant, ZoneOffset.UTC),
            )
    }
}
