package com.dictionary.android.feature_dictionary.presentation.favorite

import android.graphics.fonts.FontFamily
import android.graphics.fonts.FontStyle
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.airbnb.lottie.compose.*
import com.dictionary.android.R
import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity

@Composable
fun FavoriteScreen() {
    FavoriteScreenUI()
}

@Composable
fun FavoriteScreenUI(
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val list: List<FavoriteEntity> = viewModel.list
    list.forEach {
        Log.d("LİST",it.word)

    }
    if (list.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GifImage(Modifier)
                LoaderLottie(modifier = Modifier, image = R.raw.no_favorite_icon)
                Text(
                    text = stringResource(R.string.EmptyFavorite),
                    style = MaterialTheme.typography.h3,
                    color = Color.Gray
                )
            }

        }
    }
    if (list.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            ) {
            items(list){
                Text(text = "Word: ${it.id} - ${it.word}")
            }
        }
    }


}

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(R.drawable.location).apply(block = {
                size((Size.ORIGINAL))
            }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun LoaderLottie(modifier: Modifier, image: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(image))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
    )
}