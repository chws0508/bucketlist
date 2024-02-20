package com.woosuk.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BucketsTest {

    @Test
    fun `달성률을 계산할 수 있다`() {
        // given
        val buckets1 = Buckets(
            listOf(
                testBucket(1, true),
                testBucket(2, false),
            ),
        )
        val buckets2 = Buckets(listOf())
        // when
        val actual1 = buckets1.getAllAchievementRate()
        val actual2 = buckets2.getAllAchievementRate()
        // then
        assertThat(actual1).isEqualTo(50.0)
        assertThat(actual2).isEqualTo(0.0)
    }

    @Test
    fun `카테고리 별로 분류할 수 있다`() {
        // given
        val buckets = Buckets(
            listOf(
                testBucket(id = 1, category = BucketCategory.Work),
                testBucket(id = 2, category = BucketCategory.Work),
                testBucket(id = 3, category = BucketCategory.Unspecified),
                testBucket(id = 4, category = BucketCategory.Health),
            ),
        )
        // when
        val testCase1 = buckets.getBucketListByCategory(BucketCategory.Work)
        val testCase2 = buckets.getBucketListByCategory(BucketCategory.Unspecified)
        val testCase3 = buckets.getBucketListByCategory(BucketCategory.Health)
        // then
        assertThat(testCase1).isEqualTo(
            listOf(
                testBucket(id = 1, category = BucketCategory.Work),
                testBucket(id = 2, category = BucketCategory.Work),
            ),
        )
        assertThat(testCase2).isEqualTo(
            listOf(
                testBucket(id = 3, category = BucketCategory.Unspecified),
            ),
        )
        assertThat(testCase3).isEqualTo(
            listOf(
                testBucket(id = 4, category = BucketCategory.Health),
            ),
        )
    }

    @Test
    fun `카테고리 별 달성률을 계산할 수 있다`() {
        // given
        // given
        val buckets = Buckets(
            listOf(
                testBucket(id = 1, category = BucketCategory.Work, isCompleted = true),
                testBucket(id = 2, category = BucketCategory.Work, isCompleted = false),
                testBucket(id = 3, category = BucketCategory.Health, isCompleted = true),
            ),
        )
        // when
        val testcase1 = buckets.getCategoryAchievementRate(BucketCategory.Work)
        val testCase2 = buckets.getCategoryAchievementRate(BucketCategory.Health)
        val testCase3 = buckets.getCategoryAchievementRate(BucketCategory.Unspecified)
        // then
        assertThat(testcase1).isEqualTo(50.0)
        assertThat(testCase2).isEqualTo(100.0)
        assertThat(testCase3).isEqualTo(0.0)
    }

    private fun testBucket(
        id: Int,
        isCompleted: Boolean = false,
        category: BucketCategory = BucketCategory.Work,
        ageRange: AgeRange = AgeRange.Fifties,
    ) = Bucket(
        id = id,
        category = category,
        ageRange = ageRange,
        title = "",
        description = null,
        createdAt = LocalDateTime.of(
            LocalDate.of(2023, 5, 8),
            LocalTime.of(10, 3),
        ),
        isCompleted = isCompleted,
    )
}