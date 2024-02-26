package com.woosuk.completedbucketdetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.woosuk.domain.model.CompletedBucket

@Composable
fun CompletedBucketDetailRoute(
    viewModel: CompletedBucketDetailViewModel = hiltViewModel(),
    onNavigateToCompletedList: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    val completedBucket = viewModel.completedBucket.collectAsStateWithLifecycle().value
    if (completedBucket != null) {
        CompletedBucketDetailScreen(
            completedBucket = completedBucket,
            onShowSnackBar = onShowSnackBar,
            onNavigateToCompletedList = onNavigateToCompletedList,
            onImageAdded = viewModel::onImageAdded,
            onImageDeleted = viewModel::onImageDeleted,
            onDiaryChanged = viewModel::onDiaryChanged,
            updateCompletedBucket = viewModel::updateCompletedBucket,
        )
    }
}

@Composable
fun CompletedBucketDetailScreen(
    completedBucket: CompletedBucket,
    onShowSnackBar: (String) -> Unit = {},
    onNavigateToCompletedList: () -> Unit = {},
    onImageAdded: (List<String>) -> Unit = {},
    onImageDeleted: (String) -> Unit = {},
    updateCompletedBucket: () -> Unit = {},
    onDiaryChanged: (String) -> Unit = {},
) {

}
