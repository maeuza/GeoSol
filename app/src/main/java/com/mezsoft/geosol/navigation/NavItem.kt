package com.mezsoft.geosol.navigation

import androidx.navigation.NavArgs
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    internal val route: String

) {
    object SpNavItem : NavItem("splash")
 //   object SppNavItem : NavItem("splashP")
    object LoNavItem : NavItem("login")
    object MainNavItem : NavItem("main")
    object PoNavItem : NavItem("pollinization")
    object HaNavItem : NavItem("harvest")
    object WoNavItem : NavItem("work")


}
