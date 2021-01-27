package com.beomjo.coroutine.codelab

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.beomjo.coroutine.codelab.main.TitleRefreshError
import com.beomjo.coroutine.codelab.main.TitleRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class TitleRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun whenRefreshTitleSuccess_insertRows() =
        runBlockingTest {
            val titleDao = TitleDaoFake("title")

            val subject = TitleRepository(
                MainNetworkFake("OK"),
                titleDao
            )
            subject.refreshTitle()
            assertThat(titleDao.nextInsertedOrNull(), equalTo("OK"))
        }

    @Test(expected = TitleRefreshError::class)
    fun whenRefreshTitleTimeout_throws() = runBlockingTest {
        val network = MainNetworkCompletableFake()
        val subject = TitleRepository(
            network,
            TitleDaoFake("title")
        )

        launch {
            subject.refreshTitle()
        }

        advanceTimeBy(5_000)
    }
}