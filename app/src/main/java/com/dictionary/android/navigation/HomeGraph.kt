package com.dictionary.android.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dictionary.android.feature_dictionary.presentation.favorite.FavoriteScreen
import com.dictionary.android.feature_dictionary.presentation.favorite.FavoriteViewModel
import com.dictionary.android.feature_dictionary.presentation.home.HomeScreen
import com.dictionary.android.feature_dictionary.presentation.home.WordInfoViewModel
import com.dictionary.android.feature_dictionary.presentation.onboarding.OnBoarding
import com.dictionary.android.feature_dictionary.presentation.onboarding.OnBoardingViewModel
import com.dictionary.android.feature_dictionary.presentation.wotd.WordOfTheDayScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.OnBoardingScreen.route) {
            val viewModel: OnBoardingViewModel = hiltViewModel()
            OnBoarding(onGetStartedButtonClick = {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.OnBoardingScreen.route) {
                        inclusive = true
                    }
                }
            },
            onBoardingViewModel = viewModel)
        }
        composable(route = Screen.HomeScreen.route) {
            val viewModel: WordInfoViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                state = viewModel.state.value,
                onFavoriteButtonClicked = { navController.navigate(Screen.FavoriteScreen.route) },
                onNotificationButtonClicked = { navController.navigate(Screen.WordOfTheDayScreen.route) })
        }
        composable(route = Screen.FavoriteScreen.route) {
            val viewModel: FavoriteViewModel = hiltViewModel()
            FavoriteScreen(
                state = viewModel.state.collectAsState().value,
                onItemDeleteClicked = { viewModel.removeFromFavorite(it) })
        }
        composable(route = Screen.WordOfTheDayScreen.route) {
            WordOfTheDayScreen()
        }
    }
}