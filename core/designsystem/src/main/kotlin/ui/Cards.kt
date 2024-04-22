package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woosuk.theme.WoosukTheme
import com.woosuk.theme.defaultFontFamily

@Composable
fun DefaultCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = WoosukTheme.colors.systemWhite,
    shadowElevationDp: Dp = 0.dp,
    roundCornerDp: Dp = 16.dp,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        shadowElevation = shadowElevationDp,
        shape = RoundedCornerShape(roundCornerDp),
        content = content,
    )
}

@Preview
@Composable
fun DefaultCardPreview() {
    WoosukTheme {
        DefaultCard(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "전체 달성률",
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 15.dp),
                    fontSize = 20.sp,
                    fontFamily = defaultFontFamily,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
