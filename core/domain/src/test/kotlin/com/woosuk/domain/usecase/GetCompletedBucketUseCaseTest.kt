package com.woosuk.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.woosuk.domain.model.CompletedBucket
import com.woosuk.domain.repository.CompletedBucketRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetCompletedBucketUseCaseTest {
    private lateinit var getCompletedBucketUseCase: GetCompletedBucketUseCase
    private val completedBucketRepository: CompletedBucketRepository = mockk()

    @Test
    fun `완료된 Bucket정보를 ID를 통해 가져온다`() = runTest {
        // given
        val testCompletedBucket = CompletedBucket.mock(1)
        coEvery { completedBucketRepository.getCompletedBucket(1) } returns testCompletedBucket
        getCompletedBucketUseCase = GetCompletedBucketUseCase(completedBucketRepository)
        // when
        val actual = getCompletedBucketUseCase(1)
        // then
        assertThat(actual).isEqualTo(testCompletedBucket)
    }
}
