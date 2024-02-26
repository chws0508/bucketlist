package com.woosuk.data.repository

import com.google.common.truth.Truth.assertThat
import com.woosuk.data.mapper.toEntity
import com.woosuk.database.dao.BucketDao
import com.woosuk.database.dao.CompletedBucketDao
import com.woosuk.database.entity.CompletedBucketEntity
import com.woosuk.domain.model.CompletedBucket
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDateTime

class DefaultCompletedBucketRepositoryTest {

    private lateinit var completedBucketRepository: DefaultCompletedBucketRepository
    private val completedBucketDao: CompletedBucketDao = mockk(relaxed = true)
    private val bucketDao: BucketDao = mockk(relaxed = true)

    @Test
    fun `달성 카드를 등록할 수 있다`() = runTest {
        // given
        completedBucketRepository = DefaultCompletedBucketRepository(bucketDao, completedBucketDao)
        val testBucket = CompletedBucket.mock(1)
        val testLocalDateTime = LocalDateTime.now()
        // when
        completedBucketRepository.addCompletedBucket(
            bucketId = testBucket.bucket.id,
            completedDate = testLocalDateTime,
            imageUris = listOf(),
            diary = "",
        )
        // then
        coVerify {
            completedBucketDao.insertCompletedBucket(
                completedBucketEntity = CompletedBucketEntity(
                    bucketId = testBucket.bucket.id,
                    completedDate = testLocalDateTime,
                    imageUrls = listOf(),
                    description = "",
                ),
            )
        }
    }

    @Test
    fun `달성 카드를 삭제할 수 있다`() = runTest {
        // given
        completedBucketRepository = DefaultCompletedBucketRepository(bucketDao, completedBucketDao)
        // when
        val testCompletedBucket = CompletedBucket.mock(1)
        completedBucketRepository.deleteCompletedBucket(testCompletedBucket)
        // then
        coVerify { completedBucketDao.deleteCompletedBucket(testCompletedBucket.toEntity()) }
    }

    @Test
    fun `달성 카드를 아이디를 통해 가져올 수 있다`() = runTest {
        // given
        val testCompletedBucket = CompletedBucket.mock(1)
        completedBucketRepository = DefaultCompletedBucketRepository(bucketDao, completedBucketDao)
        coEvery { bucketDao.getBucket(1) } returns testCompletedBucket.bucket.toEntity()
        coEvery { completedBucketDao.getCompletedBucket(1) } returns CompletedBucketEntity(
            bucketId = 1,
            completedDate = testCompletedBucket.completedAt,
            imageUrls = testCompletedBucket.imageUrls,
            description = testCompletedBucket.description,
        )
        // when
        val actual = completedBucketRepository.getCompletedBucket(1)
        // then
        assertThat(actual).isEqualTo(testCompletedBucket)
    }
}
