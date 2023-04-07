package com.dictionary.android.feature_dictionary.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dictionary.android.R
import com.dictionary.android.ui.theme.Shapes

@Composable
fun DialogComment(dialogState: Boolean, successState: Boolean , onDismiss: () -> Unit) {
    var userComment by remember {
        mutableStateOf("")
    }
    val maxChar = 250

    if (dialogState) {
        Dialog(
            onDismissRequest = { onDismiss.invoke() },
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 8.dp,
                backgroundColor = Color.White
            ) {
                if(successState){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), contentAlignment = Alignment.Center){
                        Image(painter = painterResource(id = R.drawable.baseline_check_circle_outline_24), modifier = Modifier.size(100.dp), contentDescription = "Successfully added")
                    }
                } else {

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp), verticalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = stringResource(id = R.string.FirstYourComment),
                            style = MaterialTheme.typography.h3,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = userComment,
                            onValueChange = { if (it.length <= maxChar) userComment = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.yourComment),
                                    style = MaterialTheme.typography.h3,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            },
                            shape = Shapes.small
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "${userComment.count()}/250",
                                style = MaterialTheme.typography.h3,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    }
                }


            }
        }
    }
}
