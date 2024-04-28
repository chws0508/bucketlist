package com.woosuk.updatecompletedbucket.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.woosuk.domain.model.CompletedBucket
import com.woosuk.theme.WoosukTheme
import com.woosuk.updatecompletedbucket.R
import com.woosuk.updatecompletedbucket.add.BucketCompleteDate
import com.woosuk.updatecompletedbucket.add.BucketDiary
import com.woosuk.updatecompletedbucket.add.BucketInfo
import com.woosuk.updatecompletedbucket.add.BucketPhotos
import ui.ArrowBackTopAppBar
import ui.DefaultButton
import java.time.LocalDateTime

@Composable
fun EditCompletedBucketRoute(
    viewModel: EditCompletedBucketViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    EditCompletedBucketScreen(
        onBackClick = onBackClick,
        uiState = uiState,
        onDiaryChanged = viewModel::onDiaryChanged,
        onAddImage = viewModel::addImageUris,
        onImageDeleted = viewModel::deleteImage,
        updateCompletedBucket = viewModel::updateCompletedBucket,
        onCompletedDateChanged = viewModel::onCompletedDateChanged,
    )
    LaunchedEffect(null) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is EditCompletedBucketUiEvent.EditSuccess -> {
                    onNavigateToCompletedBucketDetail(uiEvent.bucketId)
                    onShowSnackBar(context.getString(R.string.succeed_edit_record))
                }
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
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WoosukTheme.colors.systemWhite),
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
            )
        }
    }
}

@Composable
fun EditButton(
    modifier: Modifier = Modifier,
    onEditCompletedBucket: () -> Unit,
) {
    DefaultButton(
        modifier = modifier,
        onClick = { onEditCompletedBucket() },
        text = stringResource(R.string.edit_completed_bucket_button_text),
        enabled = true,
    )
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun EditCompletedBucketScreenPreview() {
    WoosukTheme {
        EditCompletedBucketScreen(
            onBackClick = {},
            uiState = CompletedBucket.mock(1),
        )
    }
}
