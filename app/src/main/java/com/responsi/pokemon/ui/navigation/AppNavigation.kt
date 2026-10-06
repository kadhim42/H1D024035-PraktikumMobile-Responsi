package com.responsi.pokemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.responsi.pokemon.ui.screen.HomeScreen
import com.responsi.pokemon.ui.screen.PokemonDetailScreen
import com.responsi.pokemon.ui.viewmodel.PokemonViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: PokemonViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onPokemonClick = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            PokemonDetailScreen(
                viewModel = viewModel,
                pokemonId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}