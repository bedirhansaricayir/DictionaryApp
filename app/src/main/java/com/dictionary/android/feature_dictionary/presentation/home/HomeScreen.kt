package com.dictionary.android.feature_dictionary.presentation.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity
import com.dictionary.android.navigation.Screen
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(viewModel: WordInfoViewModel = hiltViewModel(),state: WordInfoState,onFavoriteButtonClicked: () -> Unit,onNotificationButtonClicked: () -> Unit) {

    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val currentDate = LocalDate.now()


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is WordInfoViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "OKEY"
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            elevation = 5.dp,
            backgroundColor = MaterialTheme.colors.primary,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = viewModel.searchQuery.value,
                    onValueChange = viewModel::onSearch,
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(text = "Search")
                    },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            tint = MaterialTheme.colors.secondary,
                            contentDescription = "Search Button"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        autoCorrect = true
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.Black,
                        backgroundColor = MaterialTheme.colors.background,
                        focusedIndicatorColor = MaterialTheme.colors.primary,
                        unfocusedIndicatorColor = MaterialTheme.colors.primary,
                        cursorColor = MaterialTheme.colors.primary,
                    ),
                    maxLines = 45)
                IconButton(onClick = {onFavoriteButtonClicked()}) {
                    Icon(imageVector = Screen.FavoriteScreen.iconUnfocused, contentDescription = "Favorite Button", tint = MaterialTheme.colors.background)
                }
                IconButton(onClick = {onNotificationButtonClicked()}) {
                    Icon(imageVector = Screen.WordOfTheDayScreen.iconUnfocused, contentDescription = "Notification Button", tint = MaterialTheme.colors.background)
                }
            }
        }
    }) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
                .padding(it.calculateTopPadding())
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(state.wordInfoItems.size) { i ->
                    val wordInfo = state.wordInfoItems[i]
                    if (i > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    WordInfoItem(wordInfo = wordInfo) {
                        val favoriteEntity = FavoriteEntity(
                            word = wordInfo.word,
                            comment = wordInfo.word,
                            date = currentDate.toString()
                        )
                        viewModel.insertFavorite(favoriteEntity)
                        Log.d("ITEM", wordInfo.word)
                    }

                    if (i < state.wordInfoItems.size - 1) {
                        Divider(
                            thickness = 0.5.dp,
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                        )
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 2.dp
                )
            }
        }

    }

}

