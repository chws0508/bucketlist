package com.woosuk.data.repository

import com.google.common.truth.Truth.assertThat
import com.woosuk.data.mapper.toDomain
import com.woosuk.data.mapper.toEntity
import com.woosuk.database.dao.BucketDao
import com.woosuk.database.entity.BucketEntity
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.repository.BucketRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DefaultBucketRepositoryTest {

    private lateinit var bucketRepository: BucketRepository
    private val bucketDao = mockk<BucketDao>(relaxed = true)

    @Test
    fun `모든 버킷을 가져올 수 있다`() = runTest {
        // given
        val testBuckets = listOf(
            testBucketEntity(1),
            testBucketEntity(2),
        )
        coEvery { bucketDao.loadAllBucket() } returns flow {
            emit(testBuckets)
        }
        bucketRepository = DefaultBucketRepository(bucketDao)
        // when
        val actual = bucketRepository.getAllBuckets().first().value
        // then
        assertThat(actual).isEqualTo(testBuckets.map { it.toDomain() })
    }

    @Test
    fun `버킷을 삭제할 수 있다`() = runTest {
        // given
        val testBucket = Bucket(
            id = 0,
            category = BucketCategory.Work,
            ageRange = AgeRange.UnSpecified,
            title = "",
            description = null,
            createdAt = LocalDateTime.of(
                LocalDate.of(1, 1, 1),
                LocalTime.of(1, 1),
            ),
            isCompleted = false,
        )
        bucketRepository = DefaultBucketRepository(bucketDao)
        // when
        bucketRepository.deleteBucket(Bucket.mock())
        // then
        coVerify { bucketDao.deleteBucket(Bucket.mock().toEntity()) }
    }

    private fun testBucketEntity(
        id: Int,
    ) = BucketEntity(
        id = id,
        title = "",
        description = null,
        category = BucketCategory.Work,
        ageRange = AgeRange.UnSpecified,
        createdAt = LocalDateTime.of(
            LocalDate.of(2023, 5, 8),
            LocalTime.of(5, 7),
        ),
        isCompleted = false,
    )
}
