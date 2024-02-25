package com.woosuk.completebucket

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woosuk.domain.model.Buckets
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor
import ui.DefaultCard

@Composable
fun CompleteBucketRoute() {
    CompleteBucketScreen()
}

@Composable
fun CompleteBucketScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 40.dp,
            ),
        ) {
            item {
                CategoryDropBox()
                Spacer(modifier = Modifier.height(10.dp))
            }
            CompleteBucketList()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropBox(
    modifier: Modifier = Modifier,
    value: String = "전체",
    onValueChange: (String) -> Unit = {},
) {
    DefaultCard(
        backgroundColor = MaterialTheme.extendedColor.grayScale0,
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Text(
                text = value,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.extendedColor.tossBlue3,
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "CategoryDropBoxIcon",
                tint = MaterialTheme.extendedColor.tossBlue5,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
    }
}

fun LazyListScope.CompleteBucketList() {
    items(
        items = Buckets.mock().value,
    ) { bucket ->
        DefaultCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
        ) {
            Box {
                Icon(
                    imageVector = Icons.Rounded.MoreHoriz,
                    contentDescription = "MoreIcon",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(18.dp),
                    tint = MaterialTheme.extendedColor.grayScale3,
                )
                Column(modifier = Modifier.padding(15.dp)) {
                    Text(modifier = Modifier.padding(bottom = 10.dp), text = "2024.02.17")
                    Text(
                        text = bucket.title,
                        fontFamily = defaultFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "#${bucket.category.name}#${bucket.ageRange.value.first}대",
                        fontFamily = defaultFontFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.extendedColor.grayScale3,
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = bucket.description.toString(),
                        fontFamily = defaultFontFamily,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.extendedColor.coolGray5,
                    )
                    LazyRow {
                        items(
                            items = List(10) { R.drawable.sample_img },
                        ) { imageUrl ->
                            Image(
                                modifier = Modifier
                                    .widthIn(min = 200.dp, max = 300.dp)
                                    .heightIn(min = 200.dp, max = 300.dp)
                                    .padding(10.dp)
                                    .clip(RoundedCornerShape(15.dp)),
                                painter = painterResource(id = imageUrl),
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(widthDp = 410, heightDp = 900)
@Composable
fun CompleteBucketScreenPreview() {
    BucketlistTheme {
        Surface {
            CompleteBucketScreen()
        }
    }
}
