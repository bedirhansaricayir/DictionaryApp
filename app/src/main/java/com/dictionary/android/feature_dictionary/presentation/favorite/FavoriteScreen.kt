package com.dictionary.android.feature_dictionary.presentation.favorite

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dictionary.android.R

@Composable
fun FavoriteScreen() {
    FavoriteScreenUI()
}

@Composable
fun FavoriteScreenUI(
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val favoriteData = state.value.favoriteItems

    if (favoriteData.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
                Text(
                    text = stringResource(R.string.EmptyFavorite),
                    style = MaterialTheme.typography.h3,
                    color = Color.Gray
                )
        }
    }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(favoriteData) {
                val visible = remember { mutableStateOf(false) }
                val offset = remember { androidx.compose.animation.core.Animatable(0f) }

                LaunchedEffect(key1 = it) {
                    visible.value = true
                    offset.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
                    )
                }
                Box(
                    modifier = Modifier.offset(y = offset.value.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedVisibility(
                        visible = visible.value,
                        enter = fadeIn() + slideInVertically(
                            initialOffsetY = { -40 },
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutLinearInEasing
                            )
                        ),
                        exit = fadeOut() + slideOutVertically(
                            targetOffsetY = { -40 },
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            )
                        )
                    ) {
                        FavoriteItem(
                            favoriteWord = it,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onDeleteClick = {
                                viewModel.removeFromFavorite(it.word)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

}

