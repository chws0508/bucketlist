package ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArrowBackTopAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    backgroundColor: Color = Color.Transparent,
    title: String = "",
) {
    TopAppBar(
        modifier = modifier.padding(start = 8.dp),
        title = {
            Text(
                text = title,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .noRippleClickable { onBackClick() },
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "BackArrowTopAppBar_NavigationIcon",
                tint = MaterialTheme.colorScheme.onSecondary,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),
    )
}

@Preview
@Composable
fun BackArrowTopAppBarPreview() {
    BucketlistTheme {
        Surface {
            ArrowBackTopAppBar(title = "추가", onBackClick = {})
        }
    }
}
