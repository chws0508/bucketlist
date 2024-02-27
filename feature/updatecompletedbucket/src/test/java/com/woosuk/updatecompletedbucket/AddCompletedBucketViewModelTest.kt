package com.woosuk.updatecompletedbucket

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.usecase.AddCompletedBucketUseCase
import com.woosuk.domain.usecase.GetBucketUseCase
import com.woosuk.domain.usecase.UpdateBucketUseCase
import com.woosuk.testing.MainDispatcherRule
import com.woosuk.updatecompletedbucket.add.AddCompletedBucketUiEvent
import com.woosuk.updatecompletedbucket.add.AddCompletedBucketViewModel
import com.woosuk.updatecompletedbucket.navigation.BUCKET_ID_ARGUMENT
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class AddCompletedBucketViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AddCompletedBucketViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private val getBucketUseCase: GetBucketUseCase = mockk()
    private val updateBucketUseCase: UpdateBucketUseCase = mockk(relaxed = true)
    private val addCompletedBucketUseCase: AddCompletedBucketUseCase = mockk(relaxed = true)

    private fun initializeViewModel(): AddCompletedBucketViewModel {
        return AddCompletedBucketViewModel(
            savedStateHandle,
            getBucketUseCase,
            updateBucketUseCase,
            addCompletedBucketUseCase,
        )
    }

    @Test
    fun `bucketID를 넘겨주지 않으면 error가 발생한다`() {
        // given
        savedStateHandle = SavedStateHandle()

        // when
        val exception = assertThrows(IllegalStateException::class.java) {
            viewModel = initializeViewModel()
        }
        assertThat(exception.message).isEqualTo("bucketID를 전달받지 못했어요")
    }

    @Test(expected = IllegalStateException::class)
    fun `bucketID가 DB에 존재하지 않으면, error가 발생한다`() = runTest {
        // given
        savedStateHandle = SavedStateHandle(mapOf(BUCKET_ID_ARGUMENT to 1))
        coEvery { getBucketUseCase(1) } returns null
        // when
        viewModel = initializeViewModel()
    }

    @Test
    fun `bucketID가 DB에 존재하면, uiState는 null이 아니다`() = runTest {
        // given
        savedStateHandle = SavedStateHandle(mapOf(BUCKET_ID_ARGUMENT to 1))
        coEvery { getBucketUseCase.invoke(1) } returns Bucket.mock(1)
        viewModel = initializeViewModel()

        // when
        assertThat(viewModel.uiState.value).isNotNull()
    }

    @Test
    fun `감상문을 업데이트 할 수 있다`() {
        // given
        savedStateHandle = SavedStateHandle(mapOf(BUCKET_ID_ARGUMENT to 1))
        coEvery { getBucketUseCase.invoke(1) } returns Bucket.mock(1)
        viewModel = initializeViewModel()
        // when
        viewModel.onDiaryChanged("test")
        val actual = viewModel.uiState.value?.diary
        // then
        assertThat(actual).isEqualTo("test")
    }

    @Test
    fun `사진을 추가할 수 있다`() = runTest {
        // given
        savedStateHandle = SavedStateHandle(mapOf(BUCKET_ID_ARGUMENT to 1))
        coEvery { getBucketUseCase.invoke(1) } returns Bucket.mock(1)
        viewModel = initializeViewModel()
        // when
        viewModel.addImageUris(listOf("1", "2"))
        val actual = viewModel.uiState.value?.imageUris
        // then
        assertThat(actual).isEqualTo(listOf("1", "2"))
    }

    @Test
    fun `사진을 삭제할 수 있다`() = runTest {
        // given
        savedStateHandle = SavedStateHandle(mapOf(BUCKET_ID_ARGUMENT to 1))
        coEvery { getBucketUseCase.invoke(1) } returns Bucket.mock(1)
        viewModel = initializeViewModel()
        // when
        viewModel.addImageUris(listOf("1", "2"))
        viewModel.deleteImage(0)
        val actual = viewModel.uiState.value?.imageUris
        // then
        assertThat(actual).isEqualTo(listOf("2"))
    }

    @Test
    fun `달성 날짜를 업데이트 수 있다`() = runTest {
        // given
        savedStateHandle = SavedStateHandle(mapOf(BUCKET_ID_ARGUMENT to 1))
        coEvery { getBucketUseCase.invoke(1) } returns Bucket.mock(1)
        viewModel = initializeViewModel()
        // when
        val inputDate = LocalDateTime.of(
            LocalDate.of(2023, 5, 8),
            LocalTime.of(10, 20),
        )
        viewModel.onCompletedDateChanged(inputDate)
        val actual = viewModel.uiState.value?.completedDate
        // then
        assertThat(actual).isEqualTo(inputDate)
    }

    @Test
    fun `달성 카드를 저장할 수 있다`() = runTest {
        // given
        savedStateHandle = SavedStateHandle(mapOf(BUCKET_ID_ARGUMENT to 1))
        coEvery { getBucketUseCase.invoke(1) } returns Bucket.mock(1)
        viewModel = initializeViewModel()
        // when
        launch {
            viewModel.addCompletedBucket(
                LocalDateTime.of(
                    LocalDate.of(1, 1, 1),
                    LocalTime.of(1, 1),
                ),
            )
        }
        // then
        viewModel.uiEvent.test {
            val actual = awaitItem()
            assertThat(actual).isEqualTo(AddCompletedBucketUiEvent.AddAddCompletedEvent(1))
            coVerify {
                addCompletedBucketUseCase(
                    bucketId = 1,
                    completedDate = LocalDateTime.of(
                        LocalDate.of(1, 1, 1),
                        LocalTime.of(1, 1),
                    ),
                    imageUris = listOf(),
                    diary = "",
                )
            }
        }
    }
}
