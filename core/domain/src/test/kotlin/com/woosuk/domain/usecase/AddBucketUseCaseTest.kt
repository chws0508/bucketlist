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

class AddBucketUseCaseTest {

    private lateinit var addBucketUseCase: AddBucketUseCase
    private val bucketRepository: BucketRepository = mockk(relaxed = true)

    @Test
    fun `제목이 비어있지 않다면, 버킷을 추가한다`() = runTest {
        // when
        addBucketUseCase = AddBucketUseCase(bucketRepository)
        addBucketUseCase(
            title = "제목",
            bucketCategory = BucketCategory.Career,
            ageRange = AgeRange.OldAge,
            description = "",
            createdAt = LocalDateTime.of(LocalDate.of(1, 1, 1), LocalTime.of(1, 1)),
        )
        // then
        coVerify {
            bucketRepository.insertBucket(
                bucket = Bucket(
                    id = 0,
                    category = BucketCategory.Career,
                    ageRange = AgeRange.OldAge,
                    title = "제목",
                    description = "",
                    createdAt = LocalDateTime.of(LocalDate.of(1, 1, 1), LocalTime.of(1, 1)),
                    isCompleted = false,
                ),
            )
        }
    }
}
