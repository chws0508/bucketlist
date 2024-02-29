package com.woosuk.completedbucketdetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import ui.ArrowBackTopAppBar
import ui.DeleteDialog
import ui.noRippleClickable
import java.time.LocalDateTime

@Composable
fun CompletedBucketDetailRoute(
    viewModel: CompletedBucketDetailViewModel = hiltViewModel(),
    onNavigateToEditScreen: (bucketId: Int) -> Unit,
    onShowSnackBar: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val completedBucket = viewModel.completedBucket.collectAsStateWithLifecycle().value
    if (completedBucket != null) {
        CompletedBucketDetailScreen(
            onBackClick = onBackClick,
            completedBucket = completedBucket,
            deleteCompleteBucket = viewModel::deleteCompletedBucket,
            onNavigateToEditScreen = onNavigateToEditScreen,
        )
    }
    LaunchedEffect(key1 = null) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                CompletedBucketDetailUiEvent.DeleteSuccess -> {
                    onShowSnackBar("삭제가 완료되었어요!!")
                    onBackClick()
                }
            }
        }
    }
}

@Composable
fun CompletedBucketDetailScreen(
    completedBucket: CompletedBucket,
    deleteCompleteBucket: () -> Unit = {},
    onBackClick: () -> Unit,
    onNavigateToEditScreen: (bucketId: Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.extendedColor.grayScale0),
    ) {
        CompletedBucketDetailTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            onBackClick = onBackClick,
            onBucketDelete = deleteCompleteBucket,
            onBucketUpdate = { onNavigateToEditScreen(completedBucket.bucket.id) },
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
        ) {
            BucketCompletedDate(completedDate = completedBucket.completedAt)
            Spacer(modifier = Modifier.height(10.dp))
            BucketInfo(bucket = completedBucket.bucket)
            Spacer(modifier = Modifier.height(10.dp))
            BucketDiary(diary = completedBucket.description)
            Spacer(modifier = Modifier.height(20.dp))
            BucketPhotos(imageUris = completedBucket.imageUrls)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BucketPhotos(
    modifier: Modifier = Modifier,
    imageUris: List<String>,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { imageUris.size })
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(
            state = pagerState,
        ) { index ->
            GlideImage(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                ),
                imageModel = { imageUris[index] },
                previewPlaceholder = R.drawable.ic_launcher,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ImagePageSlider(currentPage = pagerState.currentPage, size = imageUris.size)
    }
}

@Composable
fun ImagePageSlider(
    currentPage: Int,
    size: Int,
) {
    LazyRow() {
        itemsIndexed(
            items = List(size) {},
        ) { index, _ ->
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = Icons.Filled.FiberManualRecord,
                contentDescription = "",
                tint = if (currentPage == index) MaterialTheme.extendedColor.tossBlue3
                else MaterialTheme.extendedColor.grayScale2,
            )
        }
    }
}

@Composable
fun BucketDiary(
    modifier: Modifier = Modifier,
    diary: String,
) {
    Text(
        modifier = modifier,
        text = diary,
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = MaterialTheme.extendedColor.warmGray6,
    )
}

@Composable
fun BucketCompletedDate(completedDate: LocalDateTime) {
    Text(
        text = completedDate.toLocalDate().toString() + "에 달성!",
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = MaterialTheme.extendedColor.warmGray4,
    )
}

@Composable
fun BucketInfo(
    bucket: Bucket,
) {
    var showMemo by remember { mutableStateOf(false) }
    Column {
        Text(
            text = bucket.title,
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
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
                text = bucket.description!!,
                fontFamily = defaultFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.extendedColor.warmGray6,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedBucketDetailTopAppBar(
    modifier: Modifier,
    onBackClick: () -> Unit,
    onBucketDelete: () -> Unit,
    onBucketUpdate: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    ArrowBackTopAppBar(
        modifier = modifier,
        title = stringResource(R.string.app_bar_title),
        onBackClick = onBackClick,
    ) {
        Icon(
            modifier = Modifier
                .noRippleClickable {
                    showBottomSheet = true
                }
                .padding(end = 8.dp),
            imageVector = Icons.Rounded.MoreHoriz,
            contentDescription = "TopAppBarMoreButton",
        )
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = { showBottomSheet = false },
            containerColor = MaterialTheme.extendedColor.grayScale0,
            contentColor = MaterialTheme.extendedColor.warmGray6,
        ) {
            TopBarBottomSheetContent(
                onBucketDelete = onBucketDelete,
                onBucketUpdate = onBucketUpdate,
            )
        }
    }
}

@Composable
fun TopBarBottomSheetContent(
    onBucketDelete: () -> Unit,
    onBucketUpdate: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    Column() {
        Text(
            modifier = Modifier
                .clickable {
                    onBucketUpdate()
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
                    showDeleteDialog = true
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
    if (showDeleteDialog) {
        DeleteDialog(closeDialog = { showDeleteDialog = false }) {
            onBucketDelete()
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun CompletedBucketDetailScreenPreview() {
    BucketlistTheme {
        CompletedBucketDetailScreen(
            completedBucket = CompletedBucket.mock(1),
            onBackClick = {},
            onNavigateToEditScreen = {},
            deleteCompleteBucket = {},
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun TopBarBottomSheetPreview() {
    BucketlistTheme {
        TopBarBottomSheetContent(
            onBucketDelete = {}, onBucketUpdate = {},
        )
    }
}
