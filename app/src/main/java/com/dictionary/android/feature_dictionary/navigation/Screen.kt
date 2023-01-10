package com.dictionary.android.feature_dictionary.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val iconFocused: ImageVector,
    val iconUnfocused: ImageVector
){
    object HomeScreen : Screen(
        route = "home_screen",
        title = "Home",
        iconFocused = Icons.Filled.Home,
        iconUnfocused = Icons.Outlined.Home
    )
    object FavoriteScreen : Screen(
        route = "favorite_screen",
        title = "Favorite",
        iconFocused = Icons.Filled.Favorite,
        iconUnfocused = Icons.Outlined.Favorite
    )
    object WordOfTheDayScreen : Screen(
        route = "wotd_screen",
        title = "WOTD",
        iconFocused = Icons.Filled.Notifications,
        iconUnfocused = Icons.Outlined.Notifications
    )
}
