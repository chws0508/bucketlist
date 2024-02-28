package com.woosuk.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.woosuk.common.BucketUiUtil
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.model.Buckets
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor
import ui.DefaultCard
import ui.noRippleClickable

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onEditBucketClick: () -> Unit,
    onBucketCompleteClick: (id: Int) -> Unit,
    topPaddingDp: Dp,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        topPaddingDp = topPaddingDp,
        onEditBucketClick = onEditBucketClick,
        onCompleteBucketClick = onBucketCompleteClick,
        onDeleteBucketClick = viewModel::deleteBucket,
        homeUiState = homeUiState,
        onNavigateToCompletedBucketDetail = onNavigateToCompletedBucketDetail,
        updateBucket = viewModel::updateBucket,
    )
}

@Composable
fun HomeScreen(
    onEditBucketClick: () -> Unit = {},
    onCompleteBucketClick: (id: Int) -> Unit = {},
    onDeleteBucketClick: (Bucket) -> Unit = {},
    homeUiState: HomeUiState,
    topPaddingDp: Dp,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit = {},
    updateBucket: (Bucket) -> Unit = {},
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
            if (homeUiState.buckets.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 80.dp),
                ) {
                    Text(
                        text = "버킷리스트가 비어있습니다.\n\n+버튼을 눌러\n버킷리스트를 추가해주세요",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(),
                        fontSize = 20.sp,
                        fontFamily = defaultFontFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.extendedColor.grayScale3,
                    )
                }
            }
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
                                onNavigateToCompletedBucketDetail = onNavigateToCompletedBucketDetail,
                                updateBucket = updateBucket,
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
    onCompleteBucketClick: (id: Int) -> Unit,
    onDeleteBucketClick: (Bucket) -> Unit,
    achievementRate: Double,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
    updateBucket: (Bucket) -> Unit,
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
        BucketItem(
            modifier = if (bucketList.lastIndex == index) Modifier.padding(bottom = 10.dp) else Modifier,
            bucket = bucket,
            shape = if (bucketList.lastIndex == index) RoundedCornerShape(
                bottomStart = 16.dp,
                bottomEnd = 16.dp,
            ) else RoundedCornerShape(0.dp),
            onCompleteBucketClick = onCompleteBucketClick,
            onEditBucketClick = onEditBucketClick,
            onDeleteBucketClick = onDeleteBucketClick,
            onNavigateToCompletedBucketDetail = onNavigateToCompletedBucketDetail,
            updateBucket = updateBucket,
        )
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
        Column {
            Row(
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 20.dp,
                ),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = BucketUiUtil.getCategoryName(bucketCategory = bucketCategory),
                    fontSize = 24.sp,
                    fontFamily = defaultFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
                Text(
                    text = stringResource(
                        R.string.categroy_achievement_rate_format,
                        achievementRate,
                    ),
                    fontSize = 15.sp,
                    fontFamily = defaultFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.extendedColor.warmGray3,
                )
            }
            HorizontalDivider(
                modifier = Modifier.height(1.dp),
                color = MaterialTheme.extendedColor.grayScale1,
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
    onCompleteBucketClick: (id: Int) -> Unit = {},
    onDeleteBucketClick: (Bucket) -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
    updateBucket: (Bucket) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable {
                if (bucket.isCompleted) {
                    onNavigateToCompletedBucketDetail(bucket.id)
                } else {
                    showBottomSheet = true
                }
            },
        shape = shape,
        color = Color.White,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 10.dp, bottom = 16.dp, top = 16.dp)
                        .align(Alignment.CenterVertically),
                    text = bucket.title,
                    fontFamily = defaultFontFamily,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (bucket.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (bucket.isCompleted) MaterialTheme.extendedColor.grayScale3 else MaterialTheme.extendedColor.warmGray6,
                )
                if (bucket.isCompleted) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .requiredWidth(24.dp),
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "CheckIcon",
                        tint = MaterialTheme.extendedColor.tossGreen,
                    )
                }
            }

            Icon(
                modifier = Modifier.padding(
                    start = 24.dp,
                    bottom = 16.dp,
                    top = 16.dp,
                    end = 24.dp,
                ),
                imageVector = Icons.AutoMirrored.Filled.NavigateNext,
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
                onDeleteBucketClick = {
                    onDeleteBucketClick(it)
                    showBottomSheet = false
                },
                updateBucket = updateBucket,
            )
        }
    }
}
@Composable
fun BucketItemBottomSheetContent(
    bucket: Bucket,
    onEditBucketClick: () -> Unit = {},
    onCompleteBucketClick: (id: Int) -> Unit = {},
    onDeleteBucketClick: (Bucket) -> Unit,
    updateBucket: (Bucket) -> Unit = {},
) {
    var editMode by remember { mutableStateOf(false) }

    if (!editMode)
        DefaultBottomSheetContent(
            bucket = bucket,
            onEditBucketClick = { editMode = true },
            onCompleteBucketClick = onCompleteBucketClick,
            onDeleteBucketClick = onDeleteBucketClick,
        )
    else {
        EditBottomSheetContent(
            updateBucket,
            bucket = bucket,
            closeEdit = { editMode = false },
        )
    }
}
@Preview
@Composable
fun HomeScreenPreview() {
    BucketlistTheme {
        Scaffold {
            HomeScreen(
                onEditBucketClick = {},
                onCompleteBucketClick = {},
                onDeleteBucketClick = {},
                homeUiState = HomeUiState.Success(Buckets.mock()),
                topPaddingDp = it.calculateTopPadding(),
                onNavigateToCompletedBucketDetail = {},
                updateBucket = {},
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
