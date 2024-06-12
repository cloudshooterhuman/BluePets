package com.cleancompose.domain.models

import org.junit.Assert.*

object DomainModelFactory {

    const val POST_ID = "POST_ID"
    const val POST_TEXT = "POST_TEXT"
    const val POST_IMAGE_URL = "POST_IMAGE_URL"
    const val POST_PUBLISH_DATE = "2020-03-06T23:00:40.972Z"

    const val OWNER_ID = "OWNER_ID"
    const val OWNER_TITLE = "OWNER_TITLE"
    const val OWNER_FIRST_NAME = "OWNER_FIRST_NAME"
    const val OWNER_LAST_NAME = "OWNER_LAST_NAME"
    const val OWNER_PICTURE_URL = "OWNER_PICTURE_URL"

    fun getDefaultPostModel(
        id: String = POST_ID
    ) = PostModel(
        id = id,
        text = POST_TEXT,
        imageUrl = POST_IMAGE_URL,
        publishDate = POST_PUBLISH_DATE,
        owner = getDefaultOwnerPreviewModel(),
    )

    fun getDefaultOwnerPreviewModel() = OwnerPreviewModel(
        id = OWNER_ID, name = "$OWNER_FIRST_NAME $OWNER_LAST_NAME", pictureUrl = OWNER_PICTURE_URL
    )

}
