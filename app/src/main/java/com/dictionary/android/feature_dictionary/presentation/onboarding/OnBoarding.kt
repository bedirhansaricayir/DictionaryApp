package com.dictionary.android.feature_dictionary.presentation.onboarding

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dictionary.android.navigation.OnBoardingPage
import com.dictionary.android.navigation.Screen
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoarding(
    navController: NavHostController,
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel()
) {
    val items = OnBoardingPage.getData()
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState()
    var onClickGetStarted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopSection(
            onSkipClick = {
                if (pageState.currentPage + 1 < items.size) scope.launch {
                    pageState.scrollToPage(items.size - 1)
                }
            },
        )
        HorizontalPager(
            count = items.size,
            state = pageState,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.weight(10f)
        ) { page ->
            OnBoardingItem(items = items[page])
        }
        HorizontalPagerIndicator(
            pagerState = pageState,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            activeColor = MaterialTheme.colors.primary,
            inactiveColor = Color(0XFFF8E2E7)
        )
        BottomSection(modifier = Modifier.weight(1f), pagerState = pageState, onButtonClick = {
            onBoardingViewModel.saveOnBoardingState(completed = true)
            onClickGetStarted = true
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.OnBoardingScreen.route) {
                    inclusive = true
                }
            }
        })
        if (onClickGetStarted) NotificationPermission()

    }
}

@ExperimentalPagerApi
@Composable
fun TopSection(onSkipClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        TextButton(
            onClick = onSkipClick,
            modifier = Modifier.align(Alignment.TopEnd),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(text = "Skip", color = MaterialTheme.colors.onBackground)
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomSection(modifier: Modifier, pagerState: PagerState, onButtonClick: () -> Unit = {}) {
    Row(
        modifier = modifier.padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 2
        ) {
            OutlinedButton(
                modifier = modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.Bottom),
                onClick = { onButtonClick() },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.primary
                ),
            ) {
                Text(
                    text = "Get Started",
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(vertical = 8.dp, horizontal = 40.dp)
                )
            }
        }
    }
}

@Composable
fun Indicators(size: Int, index: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 20.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) MaterialTheme.colors.primary else Color(0XFFF8E2E7)
            )
    ) {

    }
}

@Composable
fun OnBoardingItem(items: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier.padding(start = 40.dp, end = 40.dp),
            painter = painterResource(id = items.image),
            contentDescription = "Pager Image",
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = items.title),
            style = MaterialTheme.typography.h3,
            // fontSize = 24.sp,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp,
        )

        Text(
            text = stringResource(id = items.description),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp),
            letterSpacing = 1.sp,
        )
    }
}

@Composable
fun NotificationPermission(){
    val context = LocalContext.current
    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
        }
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        SideEffect {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}