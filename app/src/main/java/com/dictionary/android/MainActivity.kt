package com.dictionary.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.dictionary.android.feature_dictionary.presentation.onboarding.SplashViewModel
import com.dictionary.android.navigation.NavGraph
import com.dictionary.android.ui.theme.DictionaryTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }
        setContent {
            DictionaryTheme {
                val screen by splashViewModel.startDestination
                val navController = rememberNavController()
                NavGraph(navController = navController, startDestination = screen)
            }
        }
    }
}