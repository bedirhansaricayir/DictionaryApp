package com.dictionary.android.feature_dictionary.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dictionary.android.R
import com.dictionary.android.feature_dictionary.presentation.WordInfoItem
import com.dictionary.android.feature_dictionary.presentation.WordInfoViewModel
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen() {

    val viewModel: WordInfoViewModel = hiltViewModel()
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val interactionSource = MutableInteractionSource()





    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is WordInfoViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(it)
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
                    trailingIcon = {
                        IconButton(
                            onClick = { /*TODO*/ },
                            interactionSource = interactionSource,
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(end = 8.dp),
                                painter = painterResource(id = R.drawable.ic_baseline_mic_24),
                                tint = MaterialTheme.colors.secondary,
                                contentDescription = "Call Button"
                            )
                        }
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
                        WordInfoItem(wordInfo = wordInfo)
                        if (i < state.wordInfoItems.size - 1) {
                            Divider(thickness = 2.dp, color = MaterialTheme.colors.primaryVariant)
                        }
                    }
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

