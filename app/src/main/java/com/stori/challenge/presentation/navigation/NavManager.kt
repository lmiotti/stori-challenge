package com.stori.challenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stori.challenge.presentation.ui.view.LoginScreen
import okhttp3.Route

@Composable
fun NavManager() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Login) {
        composable<Routes.Login> {
            LoginScreen(onRegisterClicked = {})
        }
    }
}