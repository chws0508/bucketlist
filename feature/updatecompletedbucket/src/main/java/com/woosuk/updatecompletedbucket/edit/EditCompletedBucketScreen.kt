package com.woosuk.updatecompletedbucket.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.woosuk.common.LocalDateTimeUtil.convertMillsToDateTime
import com.woosuk.domain.model.CompletedBucket
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor
import com.woosuk.updatecompletedbucket.R
import com.woosuk.updatecompletedbucket.add.BucketDiary
import com.woosuk.updatecompletedbucket.add.BucketInfo
import com.woosuk.updatecompletedbucket.add.BucketPhotos
import ui.ArrowBackTopAppBar
import ui.DefaultButton
import ui.SingleLineTextField
import ui.noRippleClickable
import java.time.LocalDateTime

@Composable
fun EditCompletedBucketRoute(
    viewModel: EditCompletedBucketViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    EditCompletedBucketScreen(
        onBackClick = onBackClick,
        uiState = uiState,
        onDiaryChanged = viewModel::onDiaryChanged,
        onAddImage = viewModel::addImageUris,
        onImageDeleted = viewModel::deleteImage,
        updateCompletedBucket = viewModel::updateCompletedBucket,
        onShowSnackBar = onShowSnackBar,
        onCompletedDateChanged = viewModel::onCompletedDateChanged,
    )
    LaunchedEffect(null) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is EditCompletedBucketUiEvent.EditSuccess,
                -> onNavigateToCompletedBucketDetail(uiEvent.bucketId)
            }
        }
    }
}

@Composable
fun EditCompletedBucketScreen(
    onBackClick: () -> Unit,
    uiState: CompletedBucket?,
    onDiaryChanged: (String) -> Unit = {},
    onAddImage: (List<String>) -> Unit = {},
    updateCompletedBucket: () -> Unit = {},
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
            title = stringResource(R.string.edit_completed_bucket_topappbar_title),
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
                    completedDate = uiState.completedAt,
                    onCompletedDateChanged = onCompletedDateChanged,
                )
                BucketDiary(
                    modifier = Modifier.padding(16.dp),
                    diaryText = uiState.description,
                    onDiaryChanged = onDiaryChanged,
                )
                BucketPhotos(
                    modifier = Modifier.padding(16.dp),
                    imageUris = uiState.imageUrls,
                    onAddImage = onAddImage,
                    onImageDeleted = onImageDeleted,
                )
            }
            EditButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onEditCompletedBucket = updateCompletedBucket,
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
fun EditButton(
    modifier: Modifier = Modifier,
    onEditCompletedBucket: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    DefaultButton(
        modifier = modifier,
        onClick = {
            onEditCompletedBucket()
            onShowSnackBar("달성 카드가 수정되었어요!")
        },
        text = stringResource(R.string.edit_completed_bucket_button_text),
        enabled = true,
    )
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun EditCompletedBucketScreenPreview() {
    BucketlistTheme {
        EditCompletedBucketScreen(
            onBackClick = {},
            uiState = CompletedBucket.mock(1),
            onShowSnackBar = {},
        )
    }
}
