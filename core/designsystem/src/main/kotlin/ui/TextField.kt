package ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woosuk.theme.WoosukTheme
import com.woosuk.theme.defaultFontFamily

@Composable
fun SingleLineTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable () -> Unit = {},
    enabled: Boolean = true,
    textStyle: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = defaultFontFamily,
        color = WoosukTheme.colors.systemBlack,
    ),
) {
    TextField(
        modifier = modifier.padding(vertical = 4.dp),
        value = text,
        placeholder = {
            Text(
                text = hint,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                color = WoosukTheme.colors.coolGray2,
            )
        },
        textStyle = textStyle,
        trailingIcon = { trailingIcon() },
        onValueChange = onValueChange,
        shape = RoundedCornerShape(15.dp),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = WoosukTheme.colors.systemBlack,
            focusedContainerColor = WoosukTheme.colors.grayScale1,
            unfocusedTextColor = WoosukTheme.colors.systemBlack,
            unfocusedContainerColor = WoosukTheme.colors.grayScale1,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = WoosukTheme.colors.grayScale1,
            disabledTextColor = WoosukTheme.colors.systemBlack,
            disabledIndicatorColor = Color.Transparent,
        ),
        enabled = enabled,
    )
}

@Composable
fun MultiLineTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    mineLines: Int = 10,
    maxLines: Int = 10,
    enabled: Boolean = true,
) {
    TextField(
        modifier = modifier.padding(vertical = 4.dp),
        value = text,
        placeholder = {
            Text(
                text = hint,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                color = WoosukTheme.colors.coolGray2,
            )
        },
        textStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = defaultFontFamily,
            color = WoosukTheme.colors.systemBlack,
        ),
        onValueChange = onValueChange,
        shape = RoundedCornerShape(15.dp),
        singleLine = false,
        minLines = mineLines,
        maxLines = maxLines,
        enabled = enabled,
        colors = TextFieldDefaults.colors(
            focusedTextColor = WoosukTheme.colors.systemBlack,
            focusedContainerColor = WoosukTheme.colors.grayScale1,
            unfocusedTextColor = WoosukTheme.colors.systemBlack,
            unfocusedContainerColor = WoosukTheme.colors.grayScale1,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = WoosukTheme.colors.grayScale1,
            disabledTextColor = WoosukTheme.colors.systemBlack,
            disabledIndicatorColor = Color.Transparent,
        ),
    )
}

@Preview(showBackground = false)
@Composable
fun SingleLineTextFieldPreview() {
    WoosukTheme {
        SingleLineTextField(
            text = "미국가서 릴스 찍기", hint = "당신의 버킷리스트를 적어주세요", onValueChange = {},
        )
    }
}

@Preview(showBackground = false)
@Composable
fun MultiLineTextFieldPreview() {
    WoosukTheme {
        MultiLineTextField(text = "", hint = "버킷리스트에 대해 설명해주세요", onValueChange = {})
    }
}
