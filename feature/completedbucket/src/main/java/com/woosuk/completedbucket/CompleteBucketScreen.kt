package com.woosuk.completedbucket

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.woosuk.theme.WoosukTheme
import com.woosuk.theme.defaultFontFamily
import ui.DefaultCard
import ui.DeleteDialog
import ui.noRippleClickable

@Composable
fun CompleteBucketRoute(
    viewModel: CompletedBucketViewModel = hiltViewModel(),
    onNavigateToEditCompletedBucket: (bucketId: Int) -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
) {
    val completedBucketUiState by viewModel.completeBucketsUiState.collectAsStateWithLifecycle()

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
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Box(Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {

        CompleteBucketList(
            modifier = Modifier.fillMaxSize(),
            onNavigateToCompletedBucketDetail = onNavigateToCompletedBucketDetail,
            completedBuckets = completedBucketUiState.completedBuckets,
            onClickMenu = {
                showBottomSheet = true
                clickedCompletedBucket = it
            },
        )
        if (completedBucketUiState.completedBuckets.isEmpty()) {
            EmptyListScreen(modifier = Modifier.fillMaxSize())
        }

        CompletedBucketTopAppBar(
            selectedCategory = completedBucketUiState.selectedCategory,
            selectableCategories = selectableCategories,
            onCategoryChanged = onSelectedCategoryChanged,
            scrollBehavior = scrollBehavior,
        )
    }

    if (showBottomSheet) {
        CompletedBucketBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = WoosukTheme.colors.systemWhite,
            onClickEdit = {
                onNavigateToEditCompletedBucket(
                    clickedCompletedBucket?.bucket?.id ?: return@CompletedBucketBottomSheet,
                )
                showBottomSheet = false
            },
            onClickDelete = {
                deleteBucket(clickedCompletedBucket ?: return@CompletedBucketBottomSheet)
                showBottomSheet = false
            },
        )
    }
    if (showDialog) {
        DeleteDialog(closeDialog = { showDialog = false }) {
            deleteBucket(clickedCompletedBucket ?: return@DeleteDialog)
            showBottomSheet = false
        }
    }
}

@Composable
fun EmptyListScreen(modifier: Modifier) {
    Box(modifier = modifier) {
        Text(
            text = stringResource(R.string.empty_list_message),
            color = WoosukTheme.colors.systemBlack,
            modifier = Modifier.align(Alignment.Center),
            fontFamily = defaultFontFamily,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedBucketTopAppBar(
    modifier: Modifier = Modifier,
    selectedCategory: SelectableCategory,
    selectableCategories: List<SelectableCategory>,
    onCategoryChanged: (SelectableCategory) -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior,
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }

    Column {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = WoosukTheme.colors.grayScale1,
                scrolledContainerColor = WoosukTheme.colors.grayScale1,
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
                        color = WoosukTheme.colors.systemBlack,
                    )
                    Icon(
                        modifier = Modifier.align(Alignment.Bottom),
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "KeyboardArrowDown",
                        tint = WoosukTheme.colors.grayScale3,
                    )
                }
            },
        )
        DropdownMenu(
            modifier = Modifier
                .background(WoosukTheme.colors.systemWhite)
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
                            color = WoosukTheme.colors.systemBlack,
                        )
                    },
                    onClick = {
                        onCategoryChanged(category)
                        showMenu = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = WoosukTheme.colors.systemWhite,
                    ),
                )
            }
        }
        HorizontalDivider(color = WoosukTheme.colors.grayScale1)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CompleteBucketList(
    modifier: Modifier = Modifier,
    completedBuckets: List<CompletedBucket>,
    onClickMenu: (CompletedBucket) -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = TopAppBarHeightDp)
    ) {
        items(
            items = completedBuckets,
            key = { it.bucket.id },
        ) { completedBucket ->
            DefaultCard(
                modifier = Modifier
                    .animateItemPlacement()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
            ) {
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
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = completedBucket.bucket.title,
                            fontFamily = defaultFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = WoosukTheme.colors.systemBlack,
                            modifier = Modifier.weight(1f),
                        )
                        Row {
                            Text(
                                text = LocalContext.current.getString(
                                    R.string.achivement_date_format,
                                    completedBucket.completedAt.toLocalDate(),
                                ),
                                modifier = Modifier.padding(end = 8.dp),
                                fontFamily = defaultFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = WoosukTheme.colors.grayScale3,
                            )
                            Icon(
                                modifier = Modifier.clickable {
                                    onClickMenu(completedBucket)
                                },
                                imageVector = Icons.Rounded.MoreHoriz,
                                contentDescription = "MoreIcon",
                                tint = WoosukTheme.colors.systemBlack,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
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
                        color = WoosukTheme.colors.systemBlack,
                    )
                    BucketPhotos(
                        imageUris = completedBucket.imageUrls,
                        modifier = Modifier.padding(bottom = 10.dp, start = 8.dp),
                    )
                }
            }
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
            text = stringResource(
                R.string.bucket_tag_format,
                BucketUiUtil.getCategoryName(bucketCategory = bucket.category),
                BucketUiUtil.getAgeName(ageRange = bucket.ageRange),
            ),
            fontFamily = defaultFontFamily,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            color = WoosukTheme.colors.grayScale3,
        )
        Text(
            modifier = Modifier.clickable {
                showMemo = !showMemo
            },
            text = if (showMemo) stringResource(R.string.himeMemo) else stringResource(R.string.showMemo),
            fontFamily = defaultFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = WoosukTheme.colors.grayScale2,
        )
        if (showMemo) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = bucket.description!!,
                fontFamily = defaultFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = WoosukTheme.colors.systemBlack,
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
    WoosukTheme {
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

private val TopAppBarHeightDp = 56.dp
