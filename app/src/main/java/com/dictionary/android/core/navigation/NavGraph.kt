package com.dictionary.android.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dictionary.android.feature_dictionary.presentation.home.HomeScreen


@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
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