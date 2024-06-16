package com.cleancompose.domain.models




import com.cleancompose.api.models.PostDTO
import com.cleancompose.api.models.UserPreviewDTO
import com.cleancompose.domain.models.DomainModelFactory.OWNER_FIRST_NAME
import com.cleancompose.domain.models.DomainModelFactory.OWNER_ID
import com.cleancompose.domain.models.DomainModelFactory.OWNER_LAST_NAME
import com.cleancompose.domain.models.DomainModelFactory.OWNER_PICTURE_URL
import com.cleancompose.domain.models.DomainModelFactory.OWNER_TITLE
import com.cleancompose.domain.models.DomainModelFactory.POST_ID

object ModelDataFactory {


    fun getPostDTO(id: String = POST_ID) = PostDTO(
        id = id,
        text = DomainModelFactory.POST_TEXT,
        image = DomainModelFactory.POST_IMAGE_URL,
        likes = 23,
        tags = listOf("tag1", "tag2", "tag3"),
        publishDate = DomainModelFactory.POST_PUBLISH_DATE,
        owner = getUserPreviewDTO(),
    )

    fun getUserPreviewDTO(id: String = OWNER_ID) =
        UserPreviewDTO(
            id = OWNER_ID,
            title = OWNER_TITLE,
            firstName = OWNER_FIRST_NAME,
            lastName = OWNER_LAST_NAME,
            picture = OWNER_PICTURE_URL
        )


}