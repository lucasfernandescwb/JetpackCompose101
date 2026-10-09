package com.example.routerplusdata.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.routerplusdata.ui.DetailsScreen
import com.example.routerplusdata.ui.HomeScreen
import com.example.routerplusdata.ui.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = Modifier.fillMaxSize(),
    ) {
        composable("splash") {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate("home") {
                        popUpTo("splash") {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable("home") {
            HomeScreen(
                onNavigateToDetails = { animeId ->
                    navController.navigate("details/$animeId")
                },
            )
        }
        composable("details/{animeId}") { backStackEntry ->
            DetailsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                animeId = backStackEntry.arguments?.getString("animeId"),
            )
        }
    }
}
