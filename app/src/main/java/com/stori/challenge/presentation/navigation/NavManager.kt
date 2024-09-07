package com.stori.challenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.presentation.ui.view.HomeScreen
import com.stori.challenge.presentation.ui.view.LoginScreen
import com.stori.challenge.presentation.ui.view.RegistrationFormScreen
import com.stori.challenge.presentation.ui.view.RegistrationPhotoScreen
import com.stori.challenge.presentation.ui.view.SplashScreen
import com.stori.challenge.presentation.ui.viewmodel.RegistrationPhotoViewModel

@Composable
fun NavManager() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Splash) {
        composable<Routes.Splash> {
            SplashScreen(
                navigate = { isUserLogged ->
                    val destination = if (isUserLogged) Routes.Home else Routes.Login
                    navController.navigate(destination) {
                        popUpTo(Routes.Splash) { inclusive = true }
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
                goToPhotoScreen = { form ->
                    navController.navigate(
                        Routes.RegistrationPhoto(
                            name = form.name,
                            surname = form.surname,
                            email = form.email,
                            password = form.password
                        )
                    )
                },
                goBack = { navController.popBackStack() }
            )
        }
        composable<Routes.RegistrationPhoto> {
            val route: Routes.RegistrationPhoto = it.toRoute()
            val form = RegistrationForm(
                name = route.name,
                surname = route.surname,
                email = route.email,
                password = route.password
            )
            RegistrationPhotoScreen(
                viewModel = hiltViewModel(
                    creationCallback = { factory: RegistrationPhotoViewModel.Factory ->
                        factory.create(form)
                    }
                ),
                goToHomeScreen = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            )
        }
        composable<Routes.Home> {
            HomeScreen()
        }
    }
}