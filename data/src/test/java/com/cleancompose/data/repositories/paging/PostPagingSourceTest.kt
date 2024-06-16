package com.cleancompose.data.repositories.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.cleancompose.domain.ResultOf
import com.cleancompose.domain.models.DomainModelFactory
import com.cleancompose.domain.usecases.GetPostUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

private const val ITEMS_PER_PAGE = 20

class PostPagingSourceTest {
    private val getPostUseCase: GetPostUseCase = mockk()
    private var postPagingSource: PostPagingSource = PostPagingSource(getPostUseCase)

    @Test
    fun `Given getPostUseCase returns full list, Then postPagingSource return Page When OnSuccessful Load Of Item Keyed Data`() =
        runTest {
            // Given
            val expectedPostsList = List(23) {
                DomainModelFactory.getDefaultPostModel(UUID.randomUUID().toString())
            }

            coEvery { getPostUseCase.invoke(0) } returns ResultOf.Success(expectedPostsList)


            val pager = TestPager(
                config = PagingConfig(
                    pageSize = ITEMS_PER_PAGE,
                    enablePlaceholders = true
                ), postPagingSource
            )

            val result = pager.refresh() as PagingSource.LoadResult.Page

            // Then Write assertions against the loaded data
            assertEquals(result.data, expectedPostsList)
        }

    @Test
    fun `Refresh return error`() =
        runTest {
            // Given
            coEvery { getPostUseCase.invoke(0) } returns ResultOf.Failure("", Throwable())


            val pager = TestPager(
                config = PagingConfig(
                    pageSize = ITEMS_PER_PAGE,
                    enablePlaceholders = true
                ), postPagingSource
            )

            val result = pager.refresh()
            assertTrue(result is PagingSource.LoadResult.Error)

            val page = pager.getLastLoadedPage()
            assertNull(page)
        }


}