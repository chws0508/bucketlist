package com.woosuk.home

import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.model.Buckets
import com.woosuk.domain.usecase.DeleteBucketUseCase
import com.woosuk.domain.usecase.GetAllBucketsUseCase
import com.woosuk.testing.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class HomeViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var homeViewModel: HomeViewModel
    private val getAllBucketsUseCase = mockk<GetAllBucketsUseCase>(relaxed = true)
    private val deleteBucketUseCase = mockk<DeleteBucketUseCase>(relaxed = true)

    @Test
    fun `버킷을 가져오기 전 homeUiState의 상태는 Loading상태이다`() = runTest {
        // given
        coEvery { getAllBucketsUseCase() } returns flow {}
        homeViewModel = HomeViewModel(getAllBucketsUseCase, deleteBucketUseCase)

        // then
        assertTrue(homeViewModel.homeUiState.value is HomeUiState.Loading)
    }

    @Test
    fun `모든 버킷을 성공적으로 가져온다면, uistate는 Success상태이다`() = runTest {
        // given
        coEvery { getAllBucketsUseCase() } returns flow {
            emit(Buckets.mock())
        }
        homeViewModel = HomeViewModel(getAllBucketsUseCase, deleteBucketUseCase)

        // then
        assertTrue(homeViewModel.homeUiState.value is HomeUiState.Success)
    }

    @Test
    fun `버킷을 삭제할 수 있다`() = runTest {
        // given
        coEvery { deleteBucketUseCase(Bucket.mock()) } answers {}
        homeViewModel = HomeViewModel(getAllBucketsUseCase, deleteBucketUseCase)

        // when
        homeViewModel.deleteBucket(Bucket.mock())

        // then
        coVerify { getAllBucketsUseCase() }
    }

    @Test
    fun `버킷을 삭제하면 uiState상태도 삭제된 값으로 바뀐다`() = runTest {
        // given
        val testBuckets = Buckets(List(10) { testBucket(it) })
        val databaseBuckets = MutableStateFlow(testBuckets)
        coEvery { deleteBucketUseCase(testBucket(9)) } answers {
            databaseBuckets.value = Buckets(List(9) { testBucket(it) })
        }
        coEvery { getAllBucketsUseCase() } returns channelFlow {
            databaseBuckets.collectLatest { buckets ->
                send(buckets)
            }
        }
        homeViewModel = HomeViewModel(getAllBucketsUseCase, deleteBucketUseCase)

        // when
        homeViewModel.deleteBucket(testBucket(9))
        val actualState = homeViewModel.homeUiState.value as HomeUiState.Success
        val actualValue = actualState.buckets.value
        // then
        assertEquals(0, actualValue[0].id)
        assertEquals(8, actualValue.last().id)
    }

    private fun testBucket(
        id: Int,
    ) = Bucket(
        id = id,
        category = BucketCategory.Work,
        ageRange = AgeRange.Forties,
        title = "antiopam",
        description = null,
        createdAt = LocalDateTime.of(
            LocalDate.of(2023, 5, 30),
            LocalTime.of(20, 5),
        ),
        isCompleted = false,
    )
}
