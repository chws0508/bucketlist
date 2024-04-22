package com.woosuk.completedbucket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woosuk.theme.WoosukTheme
import com.woosuk.theme.defaultFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedBucketBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    containerColor: Color,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = containerColor,
    ) {
        Column {
            Text(
                modifier = Modifier
                    .clickable { onClickEdit() }
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.completed_bucket_edit_text),
                fontFamily = defaultFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = WoosukTheme.colors.systemBlack,
            )
            Text(
                modifier = Modifier
                    .clickable { onClickDelete() }
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.completed_bucket_delete_text),
                fontFamily = defaultFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = WoosukTheme.colors.systemBlack,
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
