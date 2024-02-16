package com.woosuk.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.BucketCategory
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor
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
) {
    AddBucketScreen(onBackClick = onBackClick)
}

@Composable
fun AddBucketScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary),
    ) {
        Column {
            ArrowBackTopAppBar(onBackClick = onBackClick)
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState),
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                AddBucketHeader()
                AddBucketTitle()
                Spacer(modifier = Modifier.height(20.dp))
                AddBucketAgeRange()
                Spacer(modifier = Modifier.height(20.dp))
                AddBucketCategory()
                Spacer(modifier = Modifier.height(20.dp))
                AddBucketDescription()
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
        AddBucketCompleteButton(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
fun AddBucketHeader() {
    Text(
        modifier = Modifier.padding(bottom = 15.dp),
        text = "버킷리스트 추가",
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSecondary,
        fontSize = 20.sp,
    )
}

@Composable
fun AddBucketTitle(modifier: Modifier = Modifier) {
    Column {
        Row {
            Text(
                text = "버킷리스트",
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
            Text(text = "*", fontFamily = defaultFontFamily, color = Color.Red)
        }
        SingleLineTextField(
            text = "",
            hint = "당신의 버킷리스트를 적어주세요",
            onValueChange = {},
        )
    }
}

@Composable
fun AddBucketDescription(modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "설명",
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        )
        MultiLineTextField(
            text = "",
            hint = "버킷리스트에 대해 설명해주세요(선택)",
            mineLines = 5,
            maxLines = 5,
            onValueChange = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBucketAgeRange(modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    Column {
        Row {
            Text(
                text = "목표 나이대",
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
            Text(text = "*", fontFamily = defaultFontFamily, color = Color.Red)
        }
        SingleLineTextField(
            modifier = Modifier.noRippleClickable {
                showBottomSheet = true
            },
            text = text,
            hint = "목표 나이대를 선택해주세요",
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
                    text = it.value.first.toString()
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
        modifier = Modifier.wrapContentHeight(),
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
                "목표 나이대를 선택해주세요",
                fontFamily = defaultFontFamily,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.extendedColor.warmGray6,
            )
            Spacer(modifier = Modifier.height(20.dp))
            AgeRange.values().forEach { ageRange ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .noRippleClickable { onClickItem(ageRange) },
                    text = "${ageRange.value.first}대",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBucketCategory(modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    Column {
        Row {
            Text(
                text = "카테고리",
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
            Text(text = "*", fontFamily = defaultFontFamily, color = Color.Red)
        }
        SingleLineTextField(
            modifier = Modifier.noRippleClickable {
                showBottomSheet = true
            },
            text = text,
            hint = "카테고리를 선택해주세요",
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
                    text = it.name
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
        modifier = Modifier.wrapContentHeight(),
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
                "카테고리를 선택해주세요",
                fontFamily = defaultFontFamily,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.extendedColor.warmGray6,
            )
            Spacer(modifier = Modifier.height(20.dp))
            BucketCategory.values().forEach { bucketCategory ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .noRippleClickable { onClickItem(bucketCategory) },
                    text = bucketCategory.name,
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
) {
    DefaultButton(
        modifier = modifier,
        onClick = {},
        text = "추가하기",
        enabled = false,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun AddBucketScreenPreview() {
    BucketlistTheme {
        Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
            AddBucketScreen(onBackClick = {})
        }
    }
}
