package me.brisson.tvcompose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.brisson.tvcompose.ui.screen.catalog.CatalogRoute
import me.brisson.tvcompose.ui.screen.details.DetailsRoute

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "/catalog",
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("/catalog") {
            CatalogRoute(
                onMovie = { id -> navController.navigate("/movie/$id") },
            )
        }

        composable(
            route = "/movie/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType }),
        ) {
            DetailsRoute(
                onBack = { navController.popBackStack() },
            )
        }
    }
}
