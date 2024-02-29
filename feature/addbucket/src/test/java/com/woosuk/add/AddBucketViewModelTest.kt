package com.woosuk.add

import com.google.common.truth.Truth.assertThat
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.usecase.AddBucketUseCase
import com.woosuk.testing.MainDispatcherRule
import com.woosuk.testing.getTestLocalDateTime
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class AddBucketViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var addBucketViewModel: AddBucketViewModel
    private val addBucketUseCase: AddBucketUseCase = mockk(relaxed = true)

    @Test
    fun `버킷리스트 제목을 입력하면, 버킷리스트 제목 상태가 바뀐다`() {
        // given
        addBucketViewModel = AddBucketViewModel(addBucketUseCase)
        // when
        addBucketViewModel.onBucketTitleChanged("돈 벌기")
        val actual = addBucketViewModel.addBucketUiState.value.title
        // then
        assertThat(actual).isEqualTo("돈 벌기")
    }

    @Test
    fun `버킷리스트 목표나이대를 선택하면, 목표나이대 상태가 바뀐다`() {
        // given
        addBucketViewModel = AddBucketViewModel(addBucketUseCase)
        // when
        addBucketViewModel.onBucketAgeRangeChanged(AgeRange.OldAge)
        val actual = addBucketViewModel.addBucketUiState.value.ageRange
        // then
        assertThat(actual).isEqualTo(AgeRange.OldAge)
    }

    @Test
    fun `버킷리스트 카테고리를 선택하면, 카테고리 상태가 바뀐다`() {
        addBucketViewModel = AddBucketViewModel(addBucketUseCase)
        // when
        addBucketViewModel.onBucketCategoryChanged(BucketCategory.Career)
        val actual = addBucketViewModel.addBucketUiState.value.category
        // then
        assertThat(actual).isEqualTo(BucketCategory.Career)
    }

    @Test
    fun `버킷리스트 설명을 입력하면, 설명 상태가 바뀐다`() {
        addBucketViewModel = AddBucketViewModel(addBucketUseCase)
        // when
        addBucketViewModel.onBucketDescriptionChanged("설명")
        val actual = addBucketViewModel.addBucketUiState.value.description
        // then
        assertThat(actual).isEqualTo("설명")
    }

    @Test
    fun `제목이 비어있다면, 버킷을 추가할 수 없다`() {
        addBucketViewModel = AddBucketViewModel(addBucketUseCase)
        // when
        addBucketViewModel.onBucketDescriptionChanged("설명")
        addBucketViewModel.onBucketTitleChanged("")
        addBucketViewModel.onBucketAgeRangeChanged(AgeRange.OldAge)
        addBucketViewModel.onBucketCategoryChanged(BucketCategory.Career)
        val actual = addBucketViewModel.addBucketUiState.value.canAddBucket
        // then
        assertThat(actual).isEqualTo(false)
    }

    @Test
    fun `목표나이대가 비어있다면, 버킷을 추가할 수 없다`() {
        addBucketViewModel = AddBucketViewModel(addBucketUseCase)
        // when
        addBucketViewModel.onBucketDescriptionChanged("설명")
        addBucketViewModel.onBucketTitleChanged("제목")
        addBucketViewModel.onBucketCategoryChanged(BucketCategory.Career)
        val actual = addBucketViewModel.addBucketUiState.value.canAddBucket
        // then
        assertThat(actual).isEqualTo(false)
    }

    @Test
    fun `제목,목표나이대,카테고리가 모두 입력되있으면, 버킷을 추가할 수 있다`() {
        addBucketViewModel = AddBucketViewModel(addBucketUseCase)
        // when
        addBucketViewModel.onBucketTitleChanged("제목")
        addBucketViewModel.onBucketCategoryChanged(BucketCategory.Career)
        addBucketViewModel.onBucketAgeRangeChanged(AgeRange.OldAge)
        val actual = addBucketViewModel.addBucketUiState.value.canAddBucket
        // then
        assertThat(actual).isEqualTo(true)
    }

    @Test
    fun `버킷을 추가할 수 있다`() = runTest {
        // given
        addBucketViewModel = AddBucketViewModel(addBucketUseCase)
        // when
        addBucketViewModel.onBucketCategoryChanged(BucketCategory.Career)
        addBucketViewModel.onBucketAgeRangeChanged(AgeRange.OldAge)
        addBucketViewModel.onBucketTitleChanged("제목")
        addBucketViewModel.addBucket(createdAt = getTestLocalDateTime())
        // then
        coVerify {
            addBucketUseCase(
                title = "제목",
                ageRange = AgeRange.OldAge,
                bucketCategory = BucketCategory.Career,
                description = "",
                createdAt = getTestLocalDateTime(),
            )
        }
    }
}
