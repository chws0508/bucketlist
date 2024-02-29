package com.woosuk.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woosuk.common.BucketUiUtil
import com.woosuk.domain.model.Bucket
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor
import ui.DefaultButton
import ui.DefaultCard
import ui.DeleteDialog
import ui.MultiLineTextField
import ui.noRippleClickable

@Composable
fun DefaultBottomSheetContent(
    bucket: Bucket,
    onEditBucketClick: () -> Unit = {},
    onCompleteBucketClick: (id: Int) -> Unit = {},
    onDeleteBucketClick: (Bucket) -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .verticalScroll(scrollState),
    ) {
        BottomSheetBucketItemInfo(bucket = bucket)
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            BottomSheetSelectionCard(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(5.dp),
                iconImageVector = Icons.Rounded.Edit,
                iconTint = MaterialTheme.extendedColor.tossBlue2,
                title = stringResource(R.string.edit_button_text),
                onClick = onEditBucketClick,
            )
            BottomSheetSelectionCard(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(5.dp),
                iconImageVector = Icons.Rounded.Delete,
                iconTint = Color.Red,
                title = stringResource(R.string.delete_button_text),
                onClick = {
                    showDeleteDialog = true
                },
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        DefaultButton(
            onClick = {
                onCompleteBucketClick(bucket.id)
            },
            text = stringResource(R.string.complete_bucket_button_text),
            enabled = true,
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
    if (showDeleteDialog) {
        DeleteDialog(
            closeDialog = {
                showDeleteDialog = false
            },
            onConfirmClick = {
                onDeleteBucketClick(bucket)
            },
        )
    }
}

@Composable
fun BottomSheetBucketItemInfo(
    modifier: Modifier = Modifier,
    bucket: Bucket,
) {
    Column(modifier = modifier) {
        Text(
            text = bucket.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            textAlign = TextAlign.Center,
            fontFamily = defaultFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.extendedColor.warmGray6,
        )
        Text(
            text = stringResource(
                R.string.bucket_tag_format,
                BucketUiUtil.getCategoryName(bucketCategory = bucket.category),
                BucketUiUtil.getAgeName(ageRange = bucket.ageRange),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            textAlign = TextAlign.Center,
            fontFamily = defaultFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.extendedColor.warmGray2,
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (bucket.description != null) {
            MultiLineTextField(
                modifier = Modifier.fillMaxWidth(),
                text = bucket.description!!,
                hint = "",
                mineLines = 5,
                maxLines = 10,
                onValueChange = {},
                enabled = false,
            )
        }
    }
}

@Composable
fun BottomSheetSelectionCard(
    modifier: Modifier,
    iconImageVector: ImageVector,
    iconTint: Color,
    title: String,
    onClick: () -> Unit = {},
) {
    DefaultCard(
        modifier = modifier.noRippleClickable {
            onClick()
        },
        backgroundColor = MaterialTheme.extendedColor.coolGray0,
        roundCornerDp = 8.dp,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.padding(10.dp),
                imageVector = iconImageVector,
                contentDescription = stringResource(R.string.edit_icon_contentdescription),
                tint = iconTint,
            )
            Text(
                text = title,
                modifier = Modifier.padding(bottom = 10.dp),
                fontFamily = defaultFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
