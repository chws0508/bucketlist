package com.woosuk.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.model.Buckets
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    var composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun 로딩_상태_일경우_로딩창이_나타난다() {
        var testTag = ""
        // when
        composeTestRule.activity.apply {
            testTag = getString(R.string.progress_indicator_test_tag)
        }
        composeTestRule.setContent {
            HomeScreen(
                homeUiState = HomeUiState.Loading,
                topPaddingDp = 10.dp,
            )
        }
        // then
        composeTestRule.onNodeWithTag(testTag).assertIsDisplayed()
    }

    @Test
    fun Bucket이_존재하고_성공적으로_불러왔을_경우_전체_달성률과_Bucket_목록과_카테고리별_달성률이_나타난다() {
        // when
        composeTestRule.setContent {
            HomeScreen(
                homeUiState = HomeUiState.Success(Buckets(testBuckets)),
                topPaddingDp = 10.dp,
            )
        }
        // then
        composeTestRule.onNodeWithText("33.3%")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("달성률 50.0%")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("건강")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("커리어")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("달성률 0.0%")
            .assertIsDisplayed()
    }

    private val testBuckets = listOf(
        testBucket(
            id = 1,
            isCompleted = true,
            bucketCategory = BucketCategory.Health,
            ageRange = AgeRange.OldAge,
            title = "test1",
        ),
        testBucket(
            id = 2,
            isCompleted = false,
            bucketCategory = BucketCategory.Health,
            ageRange = AgeRange.OldAge,
            title = "test2",
        ),
        testBucket(
            id = 3,
            isCompleted = false,
            bucketCategory = BucketCategory.Work,
            ageRange = AgeRange.OldAge,
            title = "test3",
        ),
    )

    private fun testBucket(
        id: Int,
        isCompleted: Boolean,
        bucketCategory: BucketCategory,
        ageRange: AgeRange,
        title: String,
    ) = Bucket(
        id = id,
        category = bucketCategory,
        ageRange = ageRange,
        title = title,
        description = null,
        createdAt = LocalDateTime.now(),
        isCompleted = isCompleted,
    )
}
