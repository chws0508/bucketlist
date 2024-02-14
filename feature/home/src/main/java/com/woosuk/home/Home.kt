package com.woosuk.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.model.BucketList
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily
import ui.DefaultCard

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToBucketDetail: () -> Unit,
    topPaddingDp: Dp,
) {
    HomeScreen(
        topPaddingDp = topPaddingDp,
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    topPaddingDp: Dp,
) {
    Column {
        LazyColumn(
            contentPadding = PaddingValues(
                top = topPaddingDp,
                start = 17.dp,
                end = 17.dp,
            ),
        ) {
            item {
                HomeBucketListPercentage()
            }
            BucketCategory.entries.forEach {
                val bucketList = BucketList.mock().getBucketListByCategory(it)
                if (bucketList.isNotEmpty()) {
                    HomeCategoryItems(
                        bucketList = bucketList,
                        category = it,
                    )
                }
            }
        }
    }
}

@Composable
fun HomeBucketListPercentage(percentage: Double = 50.0) {
    DefaultCard(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.overall_achievement_rate),
                modifier = Modifier.padding(top = 20.dp),
                fontSize = 20.sp,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = String.format(stringResource(id = R.string.percentage_format), percentage),
                modifier = Modifier.padding(bottom = 20.dp),
                fontSize = 30.sp,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
fun LazyListScope.HomeCategoryItems(
    modifier: Modifier = Modifier,
    category: BucketCategory = BucketCategory.Unspecified,
    bucketList: List<Bucket> = List(100) { Bucket.mock() },
) {
    item {
        CategoryItem(category = category, modifier = modifier)
    }

    itemsIndexed(
        items = bucketList,
    ) { index, bucket ->
        when (index) {
            bucketList.lastIndex ->
                BucketItem(
                    modifier = Modifier.padding(bottom = 10.dp),
                    bucket = bucket,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                )

            else -> BucketItem(bucket = bucket, shape = RoundedCornerShape(0.dp))
        }
    }
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: BucketCategory = BucketCategory.Unspecified,
) {
    // 나중에 uiState로 옮긴다.
    val categoryName =
        when (category) {
            BucketCategory.Travel -> stringResource(id = R.string.travel)
            BucketCategory.Health -> stringResource(id = R.string.health)
            BucketCategory.Work -> stringResource(id = R.string.work)
            BucketCategory.Learning -> stringResource(id = R.string.learning)
            BucketCategory.Unspecified -> stringResource(id = R.string.unspecified)
        }
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        color = Color.White,
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 30.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = categoryName,
                fontSize = 24.sp,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary,
            )
            Text(
                text = "달성률 20%",
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Composable
fun BucketItem(
    bucket: Bucket,
    modifier: Modifier = Modifier,
    shape: Shape,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        color = Color.White,
    ) {
        Text(
            text = bucket.title,
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            color = MaterialTheme.colorScheme.tertiary,
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    BucketlistTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen(topPaddingDp = 50.dp)
        }
    }
}
