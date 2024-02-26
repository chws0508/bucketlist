package com.woosuk.updatecompletedbucket.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.woosuk.common.LocalDateTimeUtil.convertMillsToDateTime
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.BucketCategory
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor
import com.woosuk.updatecompletedbucket.R
import ui.ArrowBackTopAppBar
import ui.DefaultButton
import ui.DefaultCard
import ui.MultiLineTextField
import ui.SingleLineTextField
import ui.noRippleClickable
import java.time.LocalDateTime

@Composable
fun AddCompletedBucketRoute(
    viewModel: AddCompletedBucketViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    AddCompletedBucketScreen(
        onBackClick = onBackClick,
        uiState = uiState,
        onDiaryChanged = viewModel::onDiaryChanged,
        onAddImage = viewModel::addImageUris,
        onImageDeleted = viewModel::deleteImage,
        onAddCompletedBucket = viewModel::addCompletedBucket,
        onShowSnackBar = onShowSnackBar,
        onCompletedDateChanged = viewModel::onCompletedDateChanged,
    )
    LaunchedEffect(null) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is AddCompletedBucketUiEvent.AddAddCompletedEvent,
                -> onNavigateToCompletedBucketDetail(uiEvent.bucketId)
            }
        }
    }
}

@Composable
fun AddCompletedBucketScreen(
    onBackClick: () -> Unit,
    uiState: CompletedBucketUiState?,
    onDiaryChanged: (String) -> Unit = {},
    onAddImage: (List<String>) -> Unit = {},
    onAddCompletedBucket: () -> Unit = {},
    onImageDeleted: (Int) -> Unit = {},
    onCompletedDateChanged: (LocalDateTime) -> Unit = {},
    onShowSnackBar: (String) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        ArrowBackTopAppBar(
            onBackClick = onBackClick,
            title = stringResource(R.string.add_completed_bucket_topappbar_title),
        )
        uiState?.let {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .weight(1f),
            ) {
                BucketInfo(
                    modifier = Modifier.padding(16.dp),
                    title = uiState.bucket.title,
                    category = uiState.bucket.category,
                    ageRange = uiState.bucket.ageRange,
                    description = uiState.bucket.description!!,
                )
                HorizontalDivider(Modifier.height(1.dp))
                BucketCompleteDate(
                    modifier = Modifier.padding(16.dp),
                    completedDate = uiState.completedDate,
                    onCompletedDateChanged = onCompletedDateChanged,
                )
                BucketDiary(
                    modifier = Modifier.padding(16.dp),
                    diaryText = uiState.diary,
                    onDiaryChanged = onDiaryChanged,
                )
                BucketPhotos(
                    modifier = Modifier.padding(16.dp),
                    imageUris = uiState.imageUris,
                    onAddImage = onAddImage,
                    onImageDeleted = onImageDeleted,
                )
            }
            RegisterButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onAddCompletedBucket = onAddCompletedBucket,
                onShowSnackBar = onShowSnackBar,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BucketCompleteDate(
    modifier: Modifier,
    completedDate: LocalDateTime,
    onCompletedDateChanged: (LocalDateTime) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    Column(modifier = modifier) {
        Row {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "달성일",
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
            Text(text = "*", fontFamily = defaultFontFamily, color = Color.Red)
        }
        SingleLineTextField(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { showDatePicker = true },
            text = completedDate.toLocalDate().toString(),
            hint = "",
            onValueChange = {},
            enabled = false,
        )
    }
    if (showDatePicker) {
        BucketDatePicker(
            onDismissRequest = { showDatePicker = false },
            onDateSelected = {
                onCompletedDateChanged(it)
                showDatePicker = false
            },
            datePickerState = datePickerState,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BucketDatePicker(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDateTime) -> Unit,
    datePickerState: DatePickerState,
) {
    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.extendedColor.grayScale0,
        ),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    val selectedDateMills = datePickerState.selectedDateMillis ?: return@Button
                    onDateSelected(selectedDateMills.convertMillsToDateTime())
                },
            ) {
                Text(text = "선택")
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.extendedColor.grayScale0,
            ),
        )
    }
}

@Composable
fun RegisterButton(
    modifier: Modifier = Modifier,
    onAddCompletedBucket: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    DefaultButton(
        modifier = modifier,
        onClick = {
            onAddCompletedBucket()
            onShowSnackBar("달성 카드가 등록되었어요!")
        },
        text = stringResource(R.string.complete_bucket_button_text),
        enabled = true,
    )
}

@Composable
fun BucketPhotos(
    modifier: Modifier = Modifier,
    imageUris: List<String>,
    onAddImage: (List<String>) -> Unit,
    onImageDeleted: (Int) -> Unit,
) {
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10),
        onResult = { uris -> onAddImage(uris.map { it.toString() }) },
    )
    Box(modifier = modifier) {
        if (imageUris.isNotEmpty()) {
            LazyRow {
                itemsIndexed(
                    key = { index, uri -> uri + index },
                    items = imageUris,
                ) { index, uri ->
                    DeletableImage(
                        imageUri = uri,
                        onClickDelete = { onImageDeleted(index) },
                        onClickImage = {},
                    )
                }
                item {
                    AddImageCard(
                        modifier = Modifier
                            .size(300.dp)
                            .padding(10.dp),
                        onClick = {
                            multiplePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly,
                                ),
                            )
                        },
                    )
                }
            }
        } else {
            AddImageCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f),
                onClick = {
                    multiplePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly,
                        ),
                    )
                },
            )
        }
    }
}

@Composable
fun DeletableImage(
    modifier: Modifier = Modifier,
    imageUri: String,
    onClickDelete: () -> Unit,
    onClickImage: () -> Unit,
) {
    Box {
        GlideImage(
            modifier = modifier
                .widthIn(min = 200.dp, max = 300.dp)
                .heightIn(min = 200.dp, max = 300.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(15.dp))
                .clickable { onClickImage() },
            imageModel = { Uri.parse(imageUri) },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
            ),
        )
        Icon(
            imageVector = Icons.Filled.Cancel,
            contentDescription = stringResource(R.string.imagedeleteicon),
            modifier = Modifier
                .clickable { onClickDelete() }
                .align(Alignment.TopEnd),
        )
    }
}

@Composable
fun AddImageCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    DefaultCard(
        modifier = modifier.clickable { onClick() },
        backgroundColor = MaterialTheme.extendedColor.grayScale1,
    ) {
        Box {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(text = stringResource(R.string.add_image_introduction))
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_image_icon_contentdescription),
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally,
                    ),
                )
            }
        }
    }
}

@Composable
fun BucketDiary(
    modifier: Modifier = Modifier,
    diaryText: String,
    onDiaryChanged: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Row {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = "달성 소감",
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
        }
        MultiLineTextField(
            modifier = Modifier.fillMaxWidth(),
            text = diaryText,
            hint = stringResource(R.string.bucket_diary_hint),
            onValueChange = onDiaryChanged,
            enabled = true,
        )
    }
}

@Composable
fun BucketInfo(
    modifier: Modifier = Modifier,
    title: String,
    category: BucketCategory,
    ageRange: AgeRange,
    description: String,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            fontFamily = defaultFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = stringResource(
                R.string.completed_bucket_tag_format,
                BucketUiUtil.getCategoryName(bucketCategory = category),
                BucketUiUtil.getAgeName(ageRange = ageRange),
            ),
            fontFamily = defaultFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.extendedColor.coolGray1,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = description,
            fontFamily = defaultFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.extendedColor.warmGray6,
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun AddCompletedBucketScreenPreview() {
    BucketlistTheme {
        AddCompletedBucketScreen(
            onBackClick = {},
            uiState = CompletedBucketUiState(
                bucket = Bucket.mock(),
            ),
            onShowSnackBar = {},
        )
    }
}
