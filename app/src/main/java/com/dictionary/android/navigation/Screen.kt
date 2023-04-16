package com.dictionary.android.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import com.dictionary.android.R

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
    object OnBoardingScreen : Screen(
        route = "onboarding_screen",
        title = "OnBoarding",
        iconFocused = Icons.Default.Notifications,
        iconUnfocused = Icons.Default.Notifications
    )
}

 class OnBoardingPage(
    @DrawableRes
    val image: Int,
    @StringRes
    val title: Int,
    @StringRes
    val description: Int
) {
     companion object {
         fun getData(): List<OnBoardingPage>{
             return listOf(
                 OnBoardingPage(
                     image = R.drawable.ic_launcher_foreground,
                     title = R.string.FirstOnBoardingPageTitle,
                     description = R.string.FirstOnBoardingPageDescription
                 ),
                 OnBoardingPage(
                     image = R.drawable.ic_launcher_foreground,
                     title = R.string.SecondOnBoardingPageTitle,
                     description = R.string.SecondOnBoardingPageDescription
                 ),
                 OnBoardingPage(
                     image = R.drawable.ic_launcher_foreground,
                     title = R.string.ThirdOnBoardingPageTitle,
                     description = R.string.ThirdOnBoardingPageDescription
                 )
             )
         }
     }
}
