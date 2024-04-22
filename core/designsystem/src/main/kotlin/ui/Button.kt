package ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woosuk.theme.WoosukTheme
import com.woosuk.theme.defaultFontFamily

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean,
    containerColor: Color = WoosukTheme.colors.tossBlue3,
    disabledContainerColor: Color = WoosukTheme.colors.grayScale1,
    disabledContentColor: Color = WoosukTheme.colors.coolGray1,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
        ),
        enabled = enabled,
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = text,
            fontFamily = defaultFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
