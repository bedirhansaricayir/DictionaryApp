package com.dictionary.android.feature_dictionary.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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

@Composable
fun WordInfoItem(
    wordInfo: WordInfo,
    modifier: Modifier = Modifier
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Column(modifier = modifier) {
        Text(
            text = wordInfo.word,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        /*wordInfo.phonetic?.let { phonetic ->
            Text(text = phonetic, fontWeight = FontWeight.Light)
        }
         */
        Spacer(modifier = Modifier.height(14.dp))


        wordInfo.meanings.forEach { meaning ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = 5.dp,
                backgroundColor = MaterialTheme.colors.primary

            ) {
                Text(
                    text = meaning.partOfSpeech,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = modifier.padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

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
                backgroundColor = MaterialTheme.colors.primary

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {

                    Column() {
                        meaning.definitions.forEachIndexed { i, definition ->
                            Text(
                                text = "${i + 1}. ${definition.definition}",
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