package com.woosuk.domain.usecase

import com.woosuk.domain.repository.CompletedBucketRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.time.LocalDateTime

class AddCompletedBucketUseCaseTest {

    private lateinit var addCompletedBucketUseCase: AddCompletedBucketUseCase
    private val completedBucketRepository: CompletedBucketRepository = mockk(relaxed = true)

    @Test
    fun `달성 카드를 등록할 수 있다`() = runTest {
        // given
        addCompletedBucketUseCase = AddCompletedBucketUseCase(completedBucketRepository)
        // when
        val testLocalDateTime = LocalDateTime.now()
        addCompletedBucketUseCase.invoke(
            bucketId = 0,
            completedDate = testLocalDateTime,
            imageUris = listOf(),
            diary = "",
        )
        // then
        coVerify {
            completedBucketRepository.addCompletedBucket(
                bucketId = 0,
                completedDate = testLocalDateTime,
                imageUris = listOf(),
                diary = "",
            )
        }
    }
}
