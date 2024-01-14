package com.mezsoft.geosol.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mezsoft.geosol.ui.screens.login.LoginScreen
import com.mezsoft.geosol.ui.screens.main.MainScreen
import com.mezsoft.geosol.ui.screens.pollination.PollinationScreen
import com.mezsoft.geosol.ui.screens.splash.SplashPresScreen
import com.mezsoft.tope.ui.screens.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavItem.SpNavItem.route
    ) {
        composable(NavItem.SpNavItem.route) {
            SplashScreen(onNavigate = {
                navController.navigate(NavItem.LoNavItem.route)
            })
        }
      /*  composable(NavItem.SppNavItem.route) {
            SplashPresScreen(onNavigate = {
                navController.navigate(NavItem.LoNavItem.route)
            })
        }*/
        composable(NavItem.LoNavItem.route) {
            LoginScreen(onNavigate = {
                navController.navigate(NavItem.MainNavItem.route)
            })
        }
        composable(NavItem.PoNavItem.route) {
            PollinationScreen()
        }
        composable(NavItem.MainNavItem.route) {
            MainScreen(
                onNavigateToPo = { navController.navigate(NavItem.PoNavItem.route)},
                onNavigateToHa  = { navController.navigate(NavItem.HaNavItem.route) },
                onNavigateToWo  = { navController.navigate(NavItem.WoNavItem.route) }
            )
        }
    }}

