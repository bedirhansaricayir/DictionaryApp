package com.dictionary.android.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dictionary.android.feature_dictionary.presentation.favorite.FavoriteScreen
import com.dictionary.android.feature_dictionary.presentation.home.HomeScreen
import com.dictionary.android.feature_dictionary.presentation.onboarding.OnBoarding
import com.dictionary.android.navigation.Screen
import com.dictionary.android.feature_dictionary.presentation.wotd.WordOfTheDayScreen


@Composable
fun BottomNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.OnBoardingScreen.route){
            OnBoarding(navController = navController)
        }
        composable(route = Screen.HomeScreen.route){
            HomeScreen()
        }
        composable(route = Screen.FavoriteScreen.route){
            FavoriteScreen()
        }
        composable(route = Screen.WordOfTheDayScreen.route){
            WordOfTheDayScreen()
        }
    }
}