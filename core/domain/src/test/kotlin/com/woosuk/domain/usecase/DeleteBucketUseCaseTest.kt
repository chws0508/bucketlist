package com.woosuk.domain.usecase

import com.woosuk.domain.repository.BucketRepository
import com.woosuk.domain.testBucket
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteBucketUseCaseTest {

    private lateinit var deleteBucketUseCase: DeleteBucketUseCase
    private val bucketRepository: BucketRepository = mockk(relaxed = true)

    @Test
    fun `Bucket을 삭제할 수 있다`() = runTest {
        // given
        deleteBucketUseCase = DeleteBucketUseCase(bucketRepository)
        // when
        deleteBucketUseCase(testBucket(1))
        // then
        coVerify { bucketRepository.deleteBucket(testBucket(1)) }
    }
}
