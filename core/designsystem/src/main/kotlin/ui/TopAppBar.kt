package ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woosuk.theme.WoosukTheme
import com.woosuk.theme.defaultFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArrowBackTopAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    backgroundColor: Color = Color.Transparent,
    title: String = "",
    actions: @Composable () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier.padding(start = 8.dp),
        title = {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = title,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = WoosukTheme.colors.systemBlack,
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .noRippleClickable { onBackClick() },
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "BackArrowTopAppBar_NavigationIcon",
                tint = WoosukTheme.colors.systemBlack,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),
        actions = { actions() },
    )
}

@Preview
@Composable
fun BackArrowTopAppBarPreview() {
    WoosukTheme {
        Surface {
            ArrowBackTopAppBar(title = "추가", onBackClick = {})
        }
    }
}
