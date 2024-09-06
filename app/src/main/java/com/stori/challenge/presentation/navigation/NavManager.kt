package com.stori.challenge.presentation.navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.stori.challenge.presentation.ui.view.HomeScreen
import com.stori.challenge.presentation.ui.view.LoginScreen
import com.stori.challenge.presentation.ui.view.RegistrationFormScreen
import com.stori.challenge.presentation.ui.view.SplashScreen
import okhttp3.Route

@Composable
fun NavManager() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Splash) {
        composable<Routes.Splash> {
            SplashScreen(
                navigate = { isUserLogged ->
                    val destination = if (isUserLogged) Routes.Home else Routes.Login
                    navController.navigate(destination) {
                        popUpTo(Routes.Home) { inclusive = true }
                    }
                }
            )
        }
        composable<Routes.Login> {
            LoginScreen(
                onRegisterClicked = { navController.navigate(Routes.Registration) },
                goToHomeScreen = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            )
        }
        composable<Routes.Registration> {
            RegistrationFormScreen()
        }
        composable<Routes.Home> {
            HomeScreen()
        }
    }
}