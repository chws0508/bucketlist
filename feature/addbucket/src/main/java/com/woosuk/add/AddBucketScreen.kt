package com.woosuk.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.woosuk.addbucket.R
import com.woosuk.common.BucketUiUtil
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.BucketCategory
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ui.ArrowBackTopAppBar
import ui.DefaultButton
import ui.MultiLineTextField
import ui.SingleLineTextField
import ui.noRippleClickable

@Composable
fun AddBucketRoute(
    viewModel: AddBucketViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    val uiState = viewModel.addBucketUiState.collectAsStateWithLifecycle().value
    AddBucketScreen(
        onBackClick = onBackClick,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary),
        uiState = uiState,
        onBucketTitleChanged = viewModel::onBucketTitleChanged,
        onBucketCategoryChanged = viewModel::onBucketCategoryChanged,
        onBucketAgeRangeChanged = viewModel::onBucketAgeRangeChanged,
        onBucketDescriptionChanged = viewModel::onBucketDescriptionChanged,
        onClickComplete = viewModel::addBucket,
    )
    LaunchedEffect(key1 = null) {
        viewModel.addBucketUiEvent.collectLatest { event ->
            when (event) {
                AddBucketUiEvent.AddCompleteEvent -> {
                    onShowSnackBar("버킷을 추가했어요!")
                    onBackClick()
                }
            }
        }
    }
}

@Composable
fun AddBucketScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    uiState: AddBucketUiState,
    onBucketTitleChanged: (String) -> Unit = {},
    onBucketAgeRangeChanged: (AgeRange) -> Unit = {},
    onBucketCategoryChanged: (BucketCategory) -> Unit = {},
    onBucketDescriptionChanged: (String) -> Unit = {},
    onClickComplete: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        ArrowBackTopAppBar(
            onBackClick = onBackClick,
            title = stringResource(id = R.string.add_bucket_header),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            AddBucketTitle(
                bucketTitle = uiState.title,
                onBucketTitleChanged = onBucketTitleChanged,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AddBucketAgeRange(
                ageRange = uiState.ageRange,
                onBucketAgeRangeChanged = onBucketAgeRangeChanged,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AddBucketCategory(
                bucketCategory = uiState.category,
                onBucketCategoryChanged = onBucketCategoryChanged,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AddBucketDescription(
                bucketDescription = uiState.description,
                onBucketDescriptionChanged = onBucketDescriptionChanged,
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
        AddBucketCompleteButton(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
            onClickComplete = onClickComplete,
            enabled = uiState.canAddBucket,
        )
    }
}

@Composable
fun AddBucketHeader(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.padding(bottom = 15.dp),
        text = stringResource(R.string.add_bucket_header),
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSecondary,
        fontSize = 20.sp,
    )
}

@Composable
fun AddBucketTitle(
    modifier: Modifier = Modifier,
    bucketTitle: String,
    onBucketTitleChanged: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Row {
            Text(
                text = stringResource(R.string.bucket_title_header),
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
            Text(text = "*", fontFamily = defaultFontFamily, color = Color.Red)
        }
        SingleLineTextField(
            modifier = Modifier.fillMaxWidth(),
            text = bucketTitle,
            hint = stringResource(R.string.bucket_title_hint),
            onValueChange = onBucketTitleChanged,
        )
    }
}

@Composable
fun AddBucketDescription(
    modifier: Modifier = Modifier,
    bucketDescription: String,
    onBucketDescriptionChanged: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.bucket_description_header),
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        )
        MultiLineTextField(
            modifier = Modifier.fillMaxWidth(),
            text = bucketDescription,
            hint = stringResource(R.string.bucket_description_hint),
            mineLines = 6,
            maxLines = 10,
            onValueChange = onBucketDescriptionChanged,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBucketAgeRange(
    modifier: Modifier = Modifier,
    ageRange: AgeRange?,
    onBucketAgeRangeChanged: (AgeRange) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row {
            Text(
                text = stringResource(R.string.bucket_age_range_header),
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
            Text(text = "*", fontFamily = defaultFontFamily, color = Color.Red)
        }
        SingleLineTextField(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    showBottomSheet = true
                },
            text = if (ageRange != null) BucketUiUtil.getAgeName(ageRange = ageRange) else "",
            hint = stringResource(R.string.bucket_age_range_hint),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "AddBucketAgeRangeDrowDownIcon",
                    tint = MaterialTheme.extendedColor.warmGray2,
                )
            },
            onValueChange = {},
            enabled = false,
        )
        if (showBottomSheet) {
            AddBucketAgeRangeBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                onClickItem = {
                    onBucketAgeRangeChanged(it)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBucketAgeRangeBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onClickItem: (AgeRange) -> Unit,
) {
    ModalBottomSheet(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(15.dp),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        dragHandle = { },
    ) {
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 30.dp),
        ) {
            Text(
                text = stringResource(id = R.string.bucket_age_range_hint),
                fontFamily = defaultFontFamily,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.extendedColor.warmGray6,
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn {
                items(
                    items = AgeRange.entries,
                ) { ageRange ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .noRippleClickable { onClickItem(ageRange) },
                        text = BucketUiUtil.getAgeName(ageRange = ageRange),
                        fontFamily = defaultFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.extendedColor.coolGray5,
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBucketCategory(
    modifier: Modifier = Modifier,
    bucketCategory: BucketCategory?,
    onBucketCategoryChanged: (BucketCategory) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row {
            Text(
                text = stringResource(R.string.bucket_category_header),
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
            Text(text = "*", fontFamily = defaultFontFamily, color = Color.Red)
        }
        SingleLineTextField(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    showBottomSheet = true
                },
            text = if (bucketCategory != null) BucketUiUtil.getCategoryName(bucketCategory = bucketCategory) else "",
            hint = stringResource(R.string.bucket_category_hint),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "AddBucketAgeRangeDrowDownIcon",
                    tint = MaterialTheme.extendedColor.warmGray2,
                )
            },
            onValueChange = {},
            enabled = false,
        )
        if (showBottomSheet) {
            AddBucketCategoryBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                onClickItem = {
                    onBucketCategoryChanged(it)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBucketCategoryBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onClickItem: (BucketCategory) -> Unit,
) {
    ModalBottomSheet(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(15.dp),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        dragHandle = { },
    ) {
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 30.dp),
        ) {
            Text(
                text = stringResource(id = R.string.bucket_category_hint),
                fontFamily = defaultFontFamily,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.extendedColor.warmGray6,
            )
            Spacer(modifier = Modifier.height(20.dp))
            BucketCategory.entries.forEach { bucketCategory ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .noRippleClickable { onClickItem(bucketCategory) },
                    text = BucketUiUtil.getCategoryName(bucketCategory = bucketCategory),
                    fontFamily = defaultFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.extendedColor.coolGray5,
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun AddBucketCompleteButton(
    modifier: Modifier = Modifier,
    onClickComplete: () -> Unit,
    enabled: Boolean,
) {
    DefaultButton(
        modifier = modifier,
        onClick = onClickComplete,
        text = stringResource(R.string.add_complete_button_text),
        enabled = enabled,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun AddBucketScreenPreview() {
    BucketlistTheme {
        Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
            AddBucketScreen(
                onBackClick = {},
                modifier = Modifier,
                uiState = AddBucketUiState(
                    title = "제목",
                    ageRange = AgeRange.Fifties,
                    category = BucketCategory.Work,
                    description = "서멸입니다",
                ),
            )
        }
    }
}
