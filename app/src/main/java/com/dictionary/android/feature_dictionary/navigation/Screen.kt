package com.dictionary.android.feature_dictionary.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object HomeScreen : Screen(
        route = "home_screen",
        title = "Home",
        icon = Icons.Default.Home
    )
    object FavoriteScreen : Screen(
        route = "favorite_screen",
        title = "Favorite",
        icon = Icons.Default.Favorite
    )
    object WordOfTheDayScreen : Screen(
        route = "wotd_screen",
        title = "WOTD",
        icon = Icons.Default.Notifications
    )
}
