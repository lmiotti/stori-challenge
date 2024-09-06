package com.stori.challenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stori.challenge.presentation.ui.view.HomeScreen
import com.stori.challenge.presentation.ui.view.LoginScreen
import com.stori.challenge.presentation.ui.view.RegistrationFormScreen
import com.stori.challenge.presentation.ui.view.RegistrationPhotoScreen
import com.stori.challenge.presentation.ui.view.SplashScreen

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
                onRegisterClicked = { navController.navigate(Routes.RegistrationForm) },
                goToHomeScreen = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            )
        }
        composable<Routes.RegistrationForm> {
            RegistrationFormScreen(
                goToPhotoScreen = { navController.navigate(Routes.RegistrationPhoto) }
            )
        }
        composable<Routes.RegistrationPhoto> {
            RegistrationPhotoScreen()
        }
        composable<Routes.Home> {
            HomeScreen()
        }
    }
}