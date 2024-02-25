package com.woosuk.data.repository

import com.woosuk.database.dao.CompletedBucketDao
import com.woosuk.database.entity.CompletedBucketEntity
import com.woosuk.domain.model.CompletedBucket
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDateTime

class DefaultCompletedBucketRepositoryTest {

    private lateinit var completedBucketRepository: DefaultCompletedBucketRepository
    private val completedBucketDao: CompletedBucketDao = mockk(relaxed = true)

    @Test
    fun `달성 카드를 등록할 수 있다`() = runTest {
        // given
        completedBucketRepository = DefaultCompletedBucketRepository(completedBucketDao)
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
}
