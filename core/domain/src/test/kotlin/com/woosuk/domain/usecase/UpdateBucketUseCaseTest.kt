package com.woosuk.domain.usecase

import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.repository.BucketRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class UpdateBucketUseCaseTest {

    private lateinit var updateBucketUseCase: UpdateBucketUseCase
    private val bucketRepository: BucketRepository = mockk(relaxed = true)

    @Test
    fun `bucket상태를 업데이트 할 수 있다`() = runTest {
        // given
        updateBucketUseCase = UpdateBucketUseCase(bucketRepository)
        val testBucket = Bucket(
            id = 1,
            category = BucketCategory.Work,
            ageRange = AgeRange.UnSpecified,
            title = "제목",
            description = null,
            createdAt = LocalDateTime.of(
                LocalDate.of(1, 1, 1),
                LocalTime.of(1, 1),
            ),
            isCompleted = true,
        )
        // when
        updateBucketUseCase.invoke(testBucket)
        // then
        coVerify { bucketRepository.updateBucket(testBucket) }
    }
}
