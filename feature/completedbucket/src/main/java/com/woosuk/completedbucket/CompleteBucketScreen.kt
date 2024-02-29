package com.woosuk.completedbucket

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.woosuk.common.BucketUiUtil
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.CompletedBucket
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor
import ui.DeleteDialog
import ui.noRippleClickable

@Composable
fun CompleteBucketRoute(
    viewModel: CompletedBucketViewModel = hiltViewModel(),
    onNavigateToEditCompletedBucket: (bucketId: Int) -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
) {
    val completedBucketUiState =
        viewModel.completeBucketsUiState.collectAsStateWithLifecycle().value
    CompleteBucketScreen(
        selectableCategories = viewModel.allSelectableCategories,
        completedBucketUiState = completedBucketUiState,
        onNavigateToCompletedBucketDetail = onNavigateToCompletedBucketDetail,
        onNavigateToEditCompletedBucket = onNavigateToEditCompletedBucket,
        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
        deleteBucket = viewModel::deleteCompletedBucket,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteBucketScreen(
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit = {},
    onNavigateToEditCompletedBucket: (bucketId: Int) -> Unit = {},
    completedBucketUiState: CompletedBucketsUiState,
    selectableCategories: List<SelectableCategory>,
    onSelectedCategoryChanged: (SelectableCategory) -> Unit = {},
    deleteBucket: (completedBucket: CompletedBucket) -> Unit = {},
) {
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var clickedCompletedBucket by remember { mutableStateOf<CompletedBucket?>(null) }
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                CompletedBucketTopAppBar(
                    selectedCategory = completedBucketUiState.selectedCategory,
                    selectableCategories = selectableCategories,
                    onCategoryChanged = onSelectedCategoryChanged,
                )
            }
            CompleteBucketList(
                onNavigateToCompletedBucketDetail = onNavigateToCompletedBucketDetail,
                completedBuckets = completedBucketUiState.completedBuckets,
                onClickMenu = {
                    showBottomSheet = true
                    clickedCompletedBucket = it
                },
            )
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.extendedColor.grayScale0,
        ) {
            Column() {
                Text(
                    modifier = Modifier
                        .clickable {
                            onNavigateToEditCompletedBucket(
                                clickedCompletedBucket?.bucket?.id ?: return@clickable,
                            )
                            showBottomSheet = false
                        }
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                    text = "수정하기",
                    fontFamily = defaultFontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.extendedColor.warmGray6,
                )
                Text(
                    modifier = Modifier
                        .clickable {
                            showDialog = true
                        }
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                    text = "삭제하기",
                    fontFamily = defaultFontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.extendedColor.warmGray6,
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
    if (showDialog) {
        DeleteDialog(closeDialog = { showDialog = false }) {
            deleteBucket(clickedCompletedBucket ?: return@DeleteDialog)
            showBottomSheet = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedBucketTopAppBar(
    modifier: Modifier = Modifier,
    selectedCategory: SelectableCategory,
    selectableCategories: List<SelectableCategory>,
    onCategoryChanged: (SelectableCategory) -> Unit = {},
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }

    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.extendedColor.grayScale0,
            ),
            modifier = modifier,
            title = {
                Row(
                    modifier = Modifier.noRippleClickable {
                        showMenu = true
                    },
                ) {
                    Text(
                        text = stringResource(id = selectedCategory.stringResourceId),
                        fontFamily = defaultFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.extendedColor.warmGray6,
                    )
                    Icon(
                        modifier = Modifier.align(Alignment.Bottom),
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "KeyboardArrowDown",
                        tint = MaterialTheme.extendedColor.grayScale3,
                    )
                }
            },
        )
        DropdownMenu(
            modifier = Modifier
                .background(MaterialTheme.extendedColor.grayScale0)
                .requiredHeight(600.dp),
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
        ) {
            selectableCategories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = category.stringResourceId),
                            fontFamily = defaultFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.extendedColor.warmGray6,
                        )
                    },
                    onClick = {
                        onCategoryChanged(category)
                        showMenu = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = Color.White,
                    ),
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.height(1.dp),
            color = MaterialTheme.extendedColor.grayScale1,
        )
    }
}

fun LazyListScope.CompleteBucketList(
    completedBuckets: List<CompletedBucket>,
    onClickMenu: (CompletedBucket) -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
) {
    items(
        items = completedBuckets,
    ) { completedBucket ->
        Column(
            modifier = Modifier.clickable {
                onNavigateToCompletedBucketDetail(completedBucket.bucket.id)
            },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "달성일: ${completedBucket.completedAt.toLocalDate()}",
                    modifier = Modifier,
                    fontFamily = defaultFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.extendedColor.grayScale3,
                )
                Icon(
                    modifier = Modifier.clickable {
                        onClickMenu(completedBucket)
                    },
                    imageVector = Icons.Rounded.MoreHoriz,
                    contentDescription = "MoreIcon",
                    tint = MaterialTheme.extendedColor.grayScale3,
                )
            }
            BucketInfo(
                modifier = Modifier.padding(horizontal = 16.dp),
                bucket = completedBucket.bucket,
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = completedBucket.description,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.extendedColor.warmGray6,
            )
            BucketPhotos(
                imageUris = completedBucket.imageUrls,
                modifier = Modifier.padding(bottom = 10.dp),
            )
            HorizontalDivider(
                modifier = Modifier.height(1.dp),
                color = MaterialTheme.extendedColor.grayScale2,
            )
        }
    }
}

@Composable
fun BucketInfo(
    modifier: Modifier = Modifier,
    bucket: Bucket,
) {
    var showMemo by rememberSaveable { mutableStateOf(false) }
    Column(modifier = modifier) {
        Text(
            text = bucket.title,
            fontFamily = defaultFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = stringResource(
                R.string.bucket_tag_format,
                BucketUiUtil.getCategoryName(bucketCategory = bucket.category),
                BucketUiUtil.getAgeName(ageRange = bucket.ageRange),
            ),
            fontFamily = defaultFontFamily,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.extendedColor.grayScale3,
        )
        Text(
            modifier = Modifier.clickable {
                showMemo = !showMemo
            },
            text = if (showMemo) "메모 숨기기" else "메모 보기",
            fontFamily = defaultFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.extendedColor.grayScale2,
        )
        if (showMemo) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = bucket.description!!,
                fontFamily = defaultFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.extendedColor.warmGray6,
            )
        }
    }
}

@Composable
fun BucketPhotos(
    modifier: Modifier = Modifier,
    imageUris: List<String>,
) {
    LazyRow(modifier = modifier) {
        items(
            items = imageUris,
        ) { imageUrl ->
            GlideImage(
                modifier = Modifier
                    .widthIn(min = 200.dp, max = 300.dp)
                    .heightIn(min = 200.dp, max = 300.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp)),
                imageModel = { imageUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                ),
                previewPlaceholder = R.drawable.sample_img,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 410, heightDp = 900)
@Composable
fun CompleteBucketScreenPreview() {
    BucketlistTheme {
        CompleteBucketScreen(
            completedBucketUiState = CompletedBucketsUiState(
                selectedCategory = SelectableCategory.All,
                completedBuckets = List(10) { CompletedBucket.mock(1) },
            ),
            selectableCategories = listOf(),
            deleteBucket = {},
        )
    }
}
