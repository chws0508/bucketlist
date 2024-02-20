package com.woosuk.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.model.Buckets
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor
import ui.DefaultButton
import ui.DefaultCard
import ui.MultiLineTextField
import ui.noRippleClickable

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onEditBucketClick: () -> Unit,
    onBucketCompleteClick: () -> Unit,
    topPaddingDp: Dp,
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        topPaddingDp = topPaddingDp,
        onEditBucketClick = onEditBucketClick,
        onCompleteBucketClick = onBucketCompleteClick,
        onDeleteBucketClick = viewModel::deleteBucket,
        homeUiState = homeUiState,
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onEditBucketClick: () -> Unit = {},
    onCompleteBucketClick: () -> Unit = {},
    onDeleteBucketClick: (Bucket) -> Unit = {},
    homeUiState: HomeUiState,
    topPaddingDp: Dp,
) {
    when (homeUiState) {
        is HomeUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = MaterialTheme.extendedColor.tossBlue2,
                    modifier = Modifier.testTag(stringResource(R.string.progress_indicator_test_tag)),
                )
            }
        }

        is HomeUiState.Success -> {
            Column {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = topPaddingDp,
                        start = 17.dp,
                        end = 17.dp,
                    ),
                ) {
                    item {
                        HomeAllAchievementRate(homeUiState.buckets.getAllAchievementRate())
                    }
                    BucketCategory.entries.forEach { bucketCategory ->
                        val buckets = homeUiState.buckets.getBucketListByCategory(bucketCategory)
                        val categoryAchievementRate =
                            homeUiState.buckets.getCategoryAchievementRate(bucketCategory)
                        if (buckets.isNotEmpty()) {
                            HomeCategoryItems(
                                bucketList = buckets,
                                category = bucketCategory,
                                achievementRate = categoryAchievementRate,
                                onEditBucketClick = onEditBucketClick,
                                onCompleteBucketClick = onCompleteBucketClick,
                                onDeleteBucketClick = onDeleteBucketClick,
                            )
                        }
                    }
                }
            }
        }

        is HomeUiState.Error -> {}
    }
}

@Composable
fun HomeAllAchievementRate(
    allAchievementRate: Double,
) {
    DefaultCard(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.overall_achievement_rate),
                modifier = Modifier.padding(top = 20.dp),
                fontSize = 20.sp,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = String.format(
                    stringResource(id = R.string.percentage_format),
                    allAchievementRate,
                ),
                modifier = Modifier.padding(bottom = 20.dp),
                fontSize = 30.sp,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.extendedColor.tossBlue2,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
fun LazyListScope.HomeCategoryItems(
    modifier: Modifier = Modifier,
    category: BucketCategory,
    bucketList: List<Bucket>,
    onEditBucketClick: () -> Unit,
    onCompleteBucketClick: () -> Unit,
    onDeleteBucketClick: (Bucket) -> Unit,
    achievementRate: Double,
) {
    item {
        CategoryInfoItem(
            bucketCategory = category,
            modifier = modifier,
            achievementRate = achievementRate,
        )
    }

    itemsIndexed(
        items = bucketList,
    ) { index, bucket ->
        when (index) {
            bucketList.lastIndex ->
                BucketItem(
                    modifier = Modifier.padding(bottom = 10.dp),
                    bucket = bucket,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                    onCompleteBucketClick = onCompleteBucketClick,
                    onEditBucketClick = onEditBucketClick,
                    onDeleteBucketClick = onDeleteBucketClick,
                )

            else -> BucketItem(
                bucket = bucket,
                shape = RoundedCornerShape(0.dp),
                onEditBucketClick = onEditBucketClick,
                onCompleteBucketClick = onCompleteBucketClick,
                onDeleteBucketClick = onDeleteBucketClick,
            )
        }
    }
}

@Composable
fun CategoryInfoItem(
    modifier: Modifier = Modifier,
    bucketCategory: BucketCategory = BucketCategory.Unspecified,
    achievementRate: Double,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = Color.White,
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 30.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = BucketUiUtil.getCategoryName(bucketCategory = bucketCategory),
                fontSize = 24.sp,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary,
            )
            Text(
                text = stringResource(
                    R.string.categroy_achievement_rate_format,
                    achievementRate,
                ),
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.End,
                color = MaterialTheme.extendedColor.warmGray3,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BucketItem(
    bucket: Bucket,
    modifier: Modifier = Modifier,
    shape: Shape,
    onEditBucketClick: () -> Unit = {},
    onCompleteBucketClick: () -> Unit = {},
    onDeleteBucketClick: (Bucket) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable {
                showBottomSheet = true
            },
        shape = shape,
        color = Color.White,
    ) {
        Box {
            Text(
                text = bucket.title,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, bottom = 16.dp, top = 16.dp),
                color = MaterialTheme.extendedColor.warmGray5,
            )

            Icon(
                modifier = Modifier
                    .padding(start = 24.dp, bottom = 16.dp, top = 16.dp, end = 24.dp)
                    .align(Alignment.CenterEnd),
                imageVector = Icons.Filled.NavigateNext,
                contentDescription = "BucketItemNavigateNext",
            )
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            modifier = Modifier.wrapContentSize(),
            onDismissRequest = { showBottomSheet = false },
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            BucketItemBottomSheetContent(
                bucket,
                onEditBucketClick = onEditBucketClick,
                onCompleteBucketClick = onCompleteBucketClick,
                onDeleteBucketClick = onDeleteBucketClick,
            )
        }
    }
}

@Composable
fun BucketItemBottomSheetContent(
    bucket: Bucket,
    onEditBucketClick: () -> Unit = {},
    onCompleteBucketClick: () -> Unit = {},
    onDeleteBucketClick: (Bucket) -> Unit,
) {
    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
    ) {
        BottomSheetBucketItemInfo(bucket = bucket)
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            BottomSheetSelectionCard(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(5.dp),
                iconImageVector = Icons.Rounded.Edit,
                iconTint = MaterialTheme.extendedColor.tossBlue2,
                title = stringResource(R.string.edit_button_text),
                onClick = onEditBucketClick,
            )
            BottomSheetSelectionCard(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .clickable {
                        onDeleteBucketClick(bucket)
                    }
                    .padding(5.dp),
                iconImageVector = Icons.Rounded.Delete,
                iconTint = Color.Red,
                title = stringResource(R.string.delete_button_text),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        DefaultButton(
            onClick = onCompleteBucketClick,
            text = stringResource(R.string.complete_bucket_button_text),
            enabled = true,
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun BottomSheetBucketItemInfo(
    bucket: Bucket,
) {
    Column {
        Text(
            text = bucket.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            textAlign = TextAlign.Center,
            fontFamily = defaultFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.extendedColor.warmGray6,
        )
        Text(
            text = stringResource(
                R.string.bucket_tag_format,
                BucketUiUtil.getCategoryName(bucketCategory = bucket.category),
                BucketUiUtil.getAgeName(ageRange = bucket.ageRange),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            textAlign = TextAlign.Center,
            fontFamily = defaultFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.extendedColor.warmGray2,
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (bucket.description != null) {
            MultiLineTextField(
                text = bucket.description!!,
                hint = "",
                mineLines = 5,
                maxLines = 5,
                onValueChange = {},
                enabled = false,
            )
        }
    }
}

@Composable
fun BottomSheetSelectionCard(
    modifier: Modifier,
    iconImageVector: ImageVector,
    iconTint: Color,
    title: String,
    onClick: () -> Unit = {},
) {
    DefaultCard(
        modifier = modifier.noRippleClickable {
            onClick()
        },
        backgroundColor = MaterialTheme.extendedColor.coolGray0,
        roundCornerDp = 8.dp,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.padding(10.dp),
                imageVector = iconImageVector,
                contentDescription = stringResource(R.string.edit_icon_contentdescription),
                tint = iconTint,
            )
            Text(
                text = title,
                modifier = Modifier.padding(bottom = 10.dp),
                fontFamily = defaultFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    BucketlistTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen(
                topPaddingDp = 50.dp,
                onEditBucketClick = {},
                onCompleteBucketClick = {},
                onDeleteBucketClick = {},
                homeUiState = HomeUiState.Success(Buckets.mock()),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 320, heightDp = 640)
@Composable
fun BucketItemBottomSheetContentPreview() {
    BucketlistTheme {
        BucketItemBottomSheetContent(bucket = Bucket.mock(), onDeleteBucketClick = {})
    }
}
