package com.woosuk.addcompletebucket

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.woosuk.domain.model.Bucket
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.extendedColor
import ui.ArrowBackTopAppBar
import ui.DefaultButton
import ui.DefaultCard
import ui.MultiLineTextField

@Composable
fun AddCompleteBucketRoute(
    onBackClick: () -> Unit,
) {
    AddCompleteBucketScreen(
        onBackClick = onBackClick,
    )
}

@Composable
fun AddCompleteBucketScreen(
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            ArrowBackTopAppBar(onBackClick = {}, title = "달성 카드 만들기")
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState),
            ) {
                BucketInfo()
                BucketDiary()
                BucketPhotos()
            }
            Box(modifier = Modifier.fillMaxSize()) {
                RegisterButton(modifier = Modifier.align(Alignment.BottomEnd))
            }
        }
    }
}

@Composable
fun RegisterButton(modifier: Modifier = Modifier) {
    DefaultButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = {},
        text = "달성 카드 등록",
        enabled = false,
    )
}

@Composable
fun BucketPhotos() {
    var selectedImages by remember { mutableStateOf<List<Uri>>(listOf()) }
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10),
        onResult = { uris -> selectedImages = uris },
    )
    if (selectedImages.isNotEmpty()) {
        LazyRow() {
            items(
                items = selectedImages,
            ) { uri ->
                GlideImage(
                    modifier = Modifier
                        .widthIn(min = 200.dp, max = 300.dp)
                        .heightIn(min = 200.dp, max = 300.dp)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    imageModel = { uri },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                    ),
                )
            }
        }
    } else {
        DefaultCard(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .clickable {
                    multiplePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly,
                        ),
                    )
                },
            backgroundColor = MaterialTheme.extendedColor.grayScale1,
        ) {
            Box {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(text = "사진을 추가해보세요!")
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",
                        modifier = Modifier.align(
                            Alignment.CenterHorizontally,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun BucketDiary() {
    MultiLineTextField(
        modifier = Modifier.fillMaxWidth(),
        text = "",
        hint = "소감문을 작성해주세요",
        onValueChange = {},
        enabled = true,
    )
}

@Composable
fun BucketInfo(
    bucket: Bucket = Bucket.mock(),
) {
    Column {
        Text(text = bucket.title)
        Text(text = "#${bucket.category}#${bucket.ageRange.value.first}대")
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = bucket.description!!,
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun AddCompleteBucketScreenPreview() {
    BucketlistTheme {
        AddCompleteBucketScreen(onBackClick = {})
    }
}
