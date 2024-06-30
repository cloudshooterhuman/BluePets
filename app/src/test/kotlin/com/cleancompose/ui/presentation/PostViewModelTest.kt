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
package com.cleancompose.ui.presentation

class PostViewModelTest {

    /* fixme seems that ViewModel with pager is only testable with UI Test. https://developer.android.com/topic/libraries/architecture/paging/test#ui-layer-tests
         as the pagingsource is already tested data layer.
     */

    /*
    private val getPostUseCase : GetPostUseCase = mockk()
    private val getPostByTagUseCase : GetPostByTagUseCase = mockk()
    private val testDispatcher = TestDispatchers()
    val dispatcher = TestCoroutineDispatcher()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcherContext = TestCoroutineDispatcher()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestCoroutineScope(testDispatcherContext)

    private val viewModel = PostViewModel(testScope, getPostUseCase,getPostByTagUseCase, testDispatcherContext)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_items_contain_one_to_ten() = runTest {
        // Get the Flow of PagingData from the ViewModel under test


        // Given
        val expectedPostsList = List(23) {
            DomainModelFactory.getDefaultPostModel(UUID.randomUUID().toString())
        }

        coEvery { getPostUseCase.invoke(0) } returns ResultOf.Success(expectedPostsList)

        mockkStatic(Log::class)
        every { Log.isLoggable(any(), any()) } returns true
        every { Log.d(any(), any()) } returns 0

        val items: Flow<PagingData<PostModel>> = viewModel.uiState


        val itemsSnapshot: List<PostModel> = items.asSnapshot {
            // Scroll to the 50th item in the list. This will also suspend till
            // the prefetch requirement is met if there's one.
            // It also suspends until all loading is complete.
            scrollTo(index = 50)
        }

        // With the asSnapshot complete, you can now verify that the snapshot
        // has the expected values
        assertEquals(
            (0..50).map(Int::toString),
            itemsSnapshot
        )
    }*/
}
