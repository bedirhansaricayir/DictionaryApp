package com.dictionary.android.feature_dictionary.presentation.home

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dictionary.android.core.view.AnimationLoading
import com.dictionary.android.feature_dictionary.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.util.*


@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen() {

    val viewModel: WordInfoViewModel = hiltViewModel()
    val state = viewModel.state.value
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

    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.searchQuery.value,
                onValueChange = viewModel::onSearch,
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth(),
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
                    backgroundColor = MaterialTheme.colors.secondary,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.primary,
                    cursorColor = MaterialTheme.colors.primary,
                ),
                maxLines = 45


            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
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
                        Divider(thickness = 0.5.dp, color = MaterialTheme.colors.onBackground, modifier = Modifier.padding(start = 8.dp, end = 8.dp))
                    }
                }
            }
        }
        if (state.isLoading) {
            //CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), strokeWidth = 1.dp)
            Box(modifier = Modifier.align(Alignment.Center)){
                AnimationLoading()
            }
        }
        /*Box(modifier = Modifier.fillMaxSize().padding(bottom = 16.dp, end = 16.dp),Alignment.BottomEnd){
            FloatingActionButton(onClick = {
                val favoriteEntity = FavoriteEntity(
                    word = "deneme",
                    comment = "Kullanıcı Yorumu",
                    date = currentDate.toString()
                )
                viewModel.insertFavorite(favoriteEntity)
            }) {
//Eklenebilir.
            }
        }*/
    }
}

