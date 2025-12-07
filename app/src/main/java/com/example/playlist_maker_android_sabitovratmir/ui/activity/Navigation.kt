package com.example.playlist_maker_android_sabitovratmir.ui.activity// Navigation.kt
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Screen(val route: String) {
    MAIN("main"),
    SEARCH("search"),
    SETTINGS("settings"),
    PLAYLISTS("playlists"),
    FAVORITES("favorites"),
    CREATE_PLAYLIST("create_playlist"),
    TRACK_DETAILS("track_details/{trackId}");

    companion object {
        fun trackDetails(id: Long) = "track_details/$id"
    }
}



@Composable
fun PlaylistHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route
    ) {
        composable(Screen.MAIN.route) {
            MainScreen(
                onSearchClick = { navController.navigate(Screen.SEARCH.route) },
                onSettingsClick = { navController.navigate(Screen.SETTINGS.route) },
                onPlaylistsClick = { navController.navigate(Screen.PLAYLISTS.route) },
                onFavoritesClick = { navController.navigate(Screen.FAVORITES.route) }
            )
        }

        composable(Screen.SEARCH.route) {
            SearchScreen(
                onBackClick = { navController.navigateUp() },
                onTrackClick = { trackId ->
                    navController.navigate(Screen.trackDetails(trackId))
                }
            )
        }

        composable(Screen.SETTINGS.route) {
            SettingsScreen(onBackClick = { navController.navigateUp() })
        }

        composable(Screen.PLAYLISTS.route) {
            PlaylistsScreen(
                onBackClick = { navController.navigateUp() },
                onCreatePlaylistClick = {
                    navController.navigate(Screen.CREATE_PLAYLIST.route)
                }
            )
        }

        composable(Screen.CREATE_PLAYLIST.route) {
            CreatePlaylistScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { name, description ->
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.FAVORITES.route) {
            FavoritesScreen(onBackClick = { navController.navigateUp() })
        }

        composable(
            route = Screen.TRACK_DETAILS.route
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("trackId")?.toLong() ?: -1
            TrackDetailsScreen(
                trackId = id,
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