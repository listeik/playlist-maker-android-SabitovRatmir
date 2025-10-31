package com.example.playlist_maker_android_sabitovratmir// Navigation.kt
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Screen {
    MAIN,
    SEARCH,
    SETTINGS
}

@Composable
fun PlaylistHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.name
    ) {
        composable(Screen.MAIN.name) {
            MainScreen(
                onSearchClick = { navController.navigateToSearch() },
                onSettingsClick = { navController.navigateToSettings() }
            )
        }

        composable(Screen.SEARCH.name) {
            SearchScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Screen.SETTINGS.name) {
            SettingsScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}

fun NavController.navigateToSearch() {
    this.navigate(Screen.SEARCH.name)
}

fun NavController.navigateToSettings() {
    this.navigate(Screen.SETTINGS.name)
}

fun NavController.navigateToMain() {
    this.navigate(Screen.MAIN.name) {
        popUpTo(Screen.MAIN.name) { inclusive = true }
    }
}