package com.woosuk.domain.usecase

import com.woosuk.domain.model.CompletedBucket
import com.woosuk.domain.repository.CompletedBucketRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteCompletedBucketUseCaseTest {
    private lateinit var deleteCompleteBucketUseCase: DeleteCompleteBucketUseCase
    private val deleteBucketUseCase: DeleteBucketUseCase = mockk(relaxed = true)
    private val completedBucketRepository: CompletedBucketRepository = mockk(relaxed = true)

    @Test
    fun `완료 버킷 정보를 삭제한다`() = runTest {
        // given
        deleteCompleteBucketUseCase =
            DeleteCompleteBucketUseCase(deleteBucketUseCase, completedBucketRepository)
        // when
        val testCompletedBucket = CompletedBucket.mock(1)
        deleteCompleteBucketUseCase(completedBucket = testCompletedBucket)
        // then
        coVerify { completedBucketRepository.deleteCompletedBucket(testCompletedBucket) }
    }

    @Test
    fun `완료 버킷 정보를 삭제하면, 원본 버킷도 삭제된다`() = runTest {
        // given
        deleteCompleteBucketUseCase =
            DeleteCompleteBucketUseCase(deleteBucketUseCase, completedBucketRepository)
        // when
        val testCompletedBucket = CompletedBucket.mock(1)
        deleteCompleteBucketUseCase(completedBucket = testCompletedBucket)
        // then
        coVerify { deleteBucketUseCase(testCompletedBucket.bucket) }
    }
}
