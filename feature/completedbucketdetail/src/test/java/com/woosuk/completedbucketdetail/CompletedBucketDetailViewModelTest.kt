package com.woosuk.completedbucketdetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.woosuk.completedbucketdetail.CompletedBucketDetailUiEvent.DeleteSuccess
import com.woosuk.completedbucketdetail.navigation.COMPLETED_BUCKET_ID_ARGUMENT
import com.woosuk.domain.model.CompletedBucket
import com.woosuk.domain.usecase.DeleteCompletedBucketUseCase
import com.woosuk.domain.usecase.GetCompletedBucketUseCase
import com.woosuk.domain.usecase.UpdateCompletedBucketUseCase
import com.woosuk.testing.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class CompletedBucketDetailViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var completedBucketDetailViewModel: CompletedBucketDetailViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private val getCompletedBucketUseCase: GetCompletedBucketUseCase = mockk()
    private val deleteCompletedBucketUseCase: DeleteCompletedBucketUseCase = mockk(relaxed = true)
    private val updateCompletedBucketUseCase: UpdateCompletedBucketUseCase = mockk(relaxed = true)

    private fun initializeViewModel(): CompletedBucketDetailViewModel {
        return CompletedBucketDetailViewModel(
            savedStateHandle,
            deleteCompletedBucketUseCase,
            getCompletedBucketUseCase,
        )
    }

    @Test(expected = IllegalStateException::class)
    fun `ID를 받지 못하면 에러가 발생한다`() = runTest {
        // given
        savedStateHandle = SavedStateHandle()
        // when
        completedBucketDetailViewModel = initializeViewModel()
    }

    @Test
    fun `생성 시 달성 카드 정보를 받아온다`() = runTest {
        // given
        savedStateHandle = SavedStateHandle(mapOf(COMPLETED_BUCKET_ID_ARGUMENT to 1))
        coEvery { getCompletedBucketUseCase(1) } returns CompletedBucket.mock(1)
        // when
        completedBucketDetailViewModel = initializeViewModel()
        val actual = completedBucketDetailViewModel.completedBucket.value
        // then
        assertThat(actual).isEqualTo(CompletedBucket.mock(1))
    }

    @Test
    fun `달성 카드 정보를 삭제할 수 있다`() = runTest {
        // given
        savedStateHandle = SavedStateHandle(mapOf(COMPLETED_BUCKET_ID_ARGUMENT to 1))
        coEvery { getCompletedBucketUseCase(1) } returns CompletedBucket.mock(1)
        completedBucketDetailViewModel = initializeViewModel()
        // when
        completedBucketDetailViewModel.deleteCompletedBucket()
        // then
        coVerify { deleteCompletedBucketUseCase(CompletedBucket.mock(1)) }
    }

    @Test
    fun `달성 카드 정보를 삭제하면 삭제 성공 이벤트가 발생한다`() = runTest {
        // given
        savedStateHandle = SavedStateHandle(mapOf(COMPLETED_BUCKET_ID_ARGUMENT to 1))
        coEvery { getCompletedBucketUseCase(1) } returns CompletedBucket.mock(1)
        completedBucketDetailViewModel = initializeViewModel()
        // when & then
        completedBucketDetailViewModel.uiEvent.test {
            completedBucketDetailViewModel.deleteCompletedBucket()
            val actual = awaitItem()
            assertThat(actual).isEqualTo(DeleteSuccess)
        }
    }
}
