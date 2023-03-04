package com.dictionary.android.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dictionary.android.navigation.graph.BottomNavGraph

@Composable
fun MainScreen(navController : NavHostController, startDestination: String) {

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Box(
            modifier = Modifier.padding(
                PaddingValues(0.dp, 0.dp, 0.dp, it.calculateBottomPadding())
            )
        ) {
            BottomNavGraph(navController = navController, startDestination = startDestination)

        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val screens = listOf(
        Screen.HomeScreen,
        Screen.FavoriteScreen,
        Screen.WordOfTheDayScreen
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val onBoardingScreen = Screen.OnBoardingScreen
    val onBoardingDestination = onBoardingScreen.route == currentDestination?.route

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {

        if(!onBoardingDestination){
            BottomNavigation(
                modifier = modifier,
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 5.dp
            ) {
                screens.forEach {
                    AddItem(
                        screen = it,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}


@Composable
fun RowScope.AddItem(
    screen: Screen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    BottomNavigationItem(
        label = {
            Text(text = screen.title, color = MaterialTheme.colors.primaryVariant)
        },
        icon = {
            Icon(
                imageVector = if (selected) screen.iconFocused else screen.iconUnfocused,
                contentDescription = "Navigation Icon"
            )
        },
        selected = selected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }


        },
        alwaysShowLabel = false,
        )

}