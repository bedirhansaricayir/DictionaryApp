package com.dictionary.android.feature_dictionary.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dictionary.android.feature_dictionary.domain.model.WordInfo
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.util.*

@Composable
fun WordInfoItem(
    wordInfo: WordInfo,
    modifier: Modifier = Modifier,
    onSwipe: () -> Unit,
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val addFavorite = SwipeAction(
        onSwipe = {
            onSwipe()
    },
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.Default.Favorite,
                contentDescription = ("Add Favorite"),
                tint = Color.White)
        },
        background =  MaterialTheme.colors.secondary,
        isUndo = true
    )
    SwipeableActionsBox(
        endActions = listOf(addFavorite),
        swipeThreshold = 50.dp,
        backgroundUntilSwipeThreshold = Color.Transparent
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = 5.dp,
                shape = RoundedCornerShape(12.dp),
                backgroundColor = MaterialTheme.colors.background
            ) {

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
                    text = wordInfo.word.uppercase(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Start
                )
            }

        }

    }

        Column(modifier = modifier) {

            /*wordInfo.phonetic?.let { phonetic ->
                Text(text = phonetic, fontWeight = FontWeight.Light)
            }

             */
            Spacer(modifier = Modifier.height(14.dp))


            wordInfo.meanings.forEach { meaning ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                //https://developer.android.com/jetpack/compose/gestures
                                onDoubleTap = {
                                    clipboardManager.setText(annotatedString = AnnotatedString(""))
                                }
                            )
                        },
                    shape = RoundedCornerShape(12.dp),
                    elevation = 5.dp,
                    backgroundColor = MaterialTheme.colors.background

                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {

                        Column() {
                            Text(
                                text = meaning.partOfSpeech.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.getDefault()
                                    ) else it.toString()
                                },
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colors.primary,
                                fontSize = 18.sp,
                                modifier = modifier.padding(bottom = 8.dp),
                                textAlign = TextAlign.Left,
                            )
                            meaning.definitions.forEachIndexed { i, definition ->
                                Text(
                                    text = "${i + 1}. ${definition.definition}",
                                    color = MaterialTheme.colors.onBackground,
                                    modifier = Modifier.pointerInput(Unit) {
                                        detectTapGestures(
                                            onDoubleTap = {
                                                clipboardManager.setText(
                                                    annotatedString = AnnotatedString(
                                                        text = "${i + 1}. ${definition.definition}"
                                                    )
                                                )
                                                showToast(context, "Definition copied to clipboard")
                                            }
                                        )
                                    })
                                Spacer(modifier = Modifier.height(8.dp))
                                definition.example?.let { example ->
                                    Text(
                                        text = "Example: $example",
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colors.onBackground,
                                        modifier = Modifier.pointerInput(Unit) {
                                            detectTapGestures(
                                                onDoubleTap = {
                                                    clipboardManager.setText(
                                                        annotatedString = AnnotatedString(
                                                            text = "Example: $example"
                                                        )
                                                    )
                                                    showToast(context, "Example copied to clipboard")
                                                }
                                            )
                                        })
                                }
                                Spacer(modifier = Modifier.height(8.dp))

                            }
                        }

                    }

                }
                Spacer(modifier = Modifier.height(16.dp))

            }
        }


}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
