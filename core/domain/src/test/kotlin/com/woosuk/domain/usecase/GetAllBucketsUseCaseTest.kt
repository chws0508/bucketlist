package com.woosuk.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.woosuk.domain.model.Buckets
import com.woosuk.domain.repository.BucketRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetAllBucketsUseCaseTest {

    private lateinit var getAllBucketsUseCase: GetAllBucketsUseCase
    private val bucketRepository = mockk<BucketRepository>()

    @Test
    fun `모든 Bucket을 불러온다`() = runTest {
        // given
        coEvery { bucketRepository.getAllBuckets() } returns flow {
            emit(Buckets.mock())
        }
        getAllBucketsUseCase = GetAllBucketsUseCase(bucketRepository)
        // when
        val actual = getAllBucketsUseCase().first().value
        // then
        assertThat(actual).isEqualTo(Buckets.mock().value)
    }
}
