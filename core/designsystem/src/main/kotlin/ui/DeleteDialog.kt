package ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.woosuk.designsystem.R
import com.woosuk.theme.WoosukTheme
import com.woosuk.theme.defaultFontFamily

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
                color = WoosukTheme.colors.systemBlack,
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
                colors = ButtonDefaults.buttonColors(containerColor = WoosukTheme.colors.tossRed),
            ) {
                Text(text = stringResource(R.string.delete_dialog_dismiss_button_text))
            }
        },
        containerColor = WoosukTheme.colors.systemWhite,
    )
}
