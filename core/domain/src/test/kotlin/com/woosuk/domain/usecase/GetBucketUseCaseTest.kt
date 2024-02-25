package com.woosuk.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.woosuk.domain.repository.BucketRepository
import com.woosuk.domain.testBucket
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetBucketUseCaseTest {

    private lateinit var getBucketUseCase: GetBucketUseCase
    private val bucketRepository: BucketRepository = mockk()

    @Test
    fun `ID를 통해 Bucket을 가져온다`() = runTest {
        // given
        coEvery { bucketRepository.getBucket(id = 1) } returns testBucket(1)
        getBucketUseCase = GetBucketUseCase(bucketRepository)
        // when
        val actual = getBucketUseCase(1)
        // then
        assertThat(actual).isEqualTo(testBucket(1))
    }

    @Test
    fun `일치하는 ID가 없다면, null을 반환한다`() = runTest {
        // given
        coEvery { bucketRepository.getBucket(id = 1) } returns null
        getBucketUseCase = GetBucketUseCase(bucketRepository)
        // when
        val actual = getBucketUseCase(1)
        // then
        assertThat(actual).isNull()
    }
}
