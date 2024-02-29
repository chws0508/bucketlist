package com.woosuk.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woosuk.common.BucketUiUtil
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.BucketCategory
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor
import ui.DefaultButton
import ui.MultiLineTextField
import ui.SingleLineTextField
import ui.noRippleClickable

@Composable
fun EditBottomSheetContent(
    updateBucket: (Bucket) -> Unit,
    bucket: Bucket,
    closeEdit: () -> Unit,
) {
    var title by rememberSaveable { mutableStateOf(bucket.title) }
    var category by rememberSaveable { mutableStateOf(bucket.category) }
    var ageRange by rememberSaveable { mutableStateOf(bucket.ageRange) }
    var description by rememberSaveable { mutableStateOf(bucket.description) }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .verticalScroll(scrollState),
    ) {
        EditBucketTitle(
            bucketTitle = title,
            onBucketTitleChanged = { title = it },
        )
        Spacer(modifier = Modifier.height(8.dp))
        EditBucketAgeRange(
            ageRange = ageRange,
            onBucketAgeRangeChanged = { ageRange = it },
        )
        EditBucketCategory(
            bucketCategory = category,
            onBucketCategoryChanged = { category = it },
        )
        Spacer(modifier = Modifier.height(8.dp))
        EditBucketDescription(
            bucketDescription = description ?: "",
            onBucketDescriptionChanged = { description = it },
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            DefaultButton(
                modifier = Modifier.weight(1f),
                onClick = closeEdit,
                text = "취소",
                enabled = true,
                containerColor = MaterialTheme.extendedColor.tossRed,
            )
            Spacer(modifier = Modifier.width(10.dp))
            DefaultButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    updateBucket(
                        bucket.copy(
                            title = title,
                            ageRange = ageRange,
                            category = category,
                            description = description,
                        ),
                    )
                    closeEdit()
                },
                text = stringResource(R.string.complete_edit_button_text),
                enabled = title.isNotBlank(),
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun EditBucketTitle(
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
        }
        SingleLineTextField(
            modifier = Modifier.fillMaxWidth(),
            text = bucketTitle,
            hint = stringResource(R.string.edit_bucket_title_hint),
            onValueChange = onBucketTitleChanged,
        )
    }
}

@Composable
fun EditBucketDescription(
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
            mineLines = 5,
            maxLines = 5,
            onValueChange = onBucketDescriptionChanged,
        )
    }
}

@Composable
fun EditBucketCategory(
    modifier: Modifier = Modifier,
    bucketCategory: BucketCategory?,
    onBucketCategoryChanged: (BucketCategory) -> Unit,
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row {
            Text(
                text = stringResource(R.string.bucket_category_header),
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
        }
        SingleLineTextField(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    showMenu = true
                },
            text = if (bucketCategory != null) BucketUiUtil.getCategoryName(bucketCategory = bucketCategory) else "",
            hint = "",
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
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .background(MaterialTheme.extendedColor.grayScale0)
                .requiredHeight(250.dp),
            offset = DpOffset.Zero,
        ) {
            BucketCategory.entries.forEach {
                DropdownMenuItem(
                    onClick = {
                        onBucketCategoryChanged(it)
                        showMenu = false
                    },
                    text = {
                        Text(
                            modifier = Modifier.clickable {
                                onBucketCategoryChanged(it)
                                showMenu = false
                            },
                            text = BucketUiUtil.getCategoryName(bucketCategory = it),
                            fontFamily = defaultFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun EditBucketAgeRange(
    modifier: Modifier = Modifier,
    ageRange: AgeRange?,
    onBucketAgeRangeChanged: (AgeRange) -> Unit,
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.bucket_age_range_header),
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        )
        SingleLineTextField(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    showMenu = true
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
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .background(MaterialTheme.extendedColor.grayScale0)
                .requiredHeight(250.dp),
            offset = DpOffset.Zero,
        ) {
            AgeRange.entries.forEach {
                DropdownMenuItem(
                    onClick = {
                        onBucketAgeRangeChanged(it)
                        showMenu = false
                    },
                    text = {
                        Text(
                            modifier = Modifier.clickable {
                                onBucketAgeRangeChanged(it)
                                showMenu = false
                            },
                            text = BucketUiUtil.getAgeName(ageRange = it),
                            fontFamily = defaultFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                        )
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditBottomSheetContentPreview() {
    BucketlistTheme {
        EditBottomSheetContent(
            bucket = Bucket.mock(1),
            updateBucket = {},
            closeEdit = {},
        )
    }
}
