package ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.woosuk.designsystem.R
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor

@Composable
fun DeleteDialog(
    closeDialog: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(
                text = stringResource(R.string.delete_dialog_title), fontFamily = defaultFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.extendedColor.warmGray6,
            )
        },
        onDismissRequest = closeDialog,
        confirmButton = {
            Button(
                onClick = {
                    onConfirmClick()
                    closeDialog()
                },
            ) {
                Text(text = stringResource(R.string.delete_dialog_confirm_button_text))
            }
        },
        dismissButton = {
            Button(
                onClick = closeDialog,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.extendedColor.tossRed),
            ) {
                Text(text = stringResource(R.string.delete_dialog_dismiss_button_text))
            }
        },
        containerColor = MaterialTheme.extendedColor.grayScale0,
    )
}
