package com.syriantrivia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.syriantrivia.ui.home.HomeScreen
import com.syriantrivia.ui.difficulty.DifficultyScreen
import com.syriantrivia.ui.quiz.QuizScreen
import com.syriantrivia.ui.results.ResultsScreen
import com.syriantrivia.ui.settings.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onModuleSelected = { moduleType ->
                    navController.navigate(Routes.difficulty(moduleType))
                },
                onSettingsClick = {
                    navController.navigate(Routes.SETTINGS)
                }
            )
        }

        composable(
            route = Routes.DIFFICULTY,
            arguments = listOf(
                navArgument("moduleType") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val moduleType = backStackEntry.arguments?.getString("moduleType") ?: "culture"
            DifficultyScreen(
                moduleType = moduleType,
                onDifficultySelected = { difficulty ->
                    navController.navigate(Routes.quiz(moduleType, difficulty))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.QUIZ,
            arguments = listOf(
                navArgument("moduleType") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val moduleType = backStackEntry.arguments?.getString("moduleType") ?: "culture"
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "easy"
            QuizScreen(
                moduleType = moduleType,
                difficulty = difficulty,
                onQuizComplete = { score, total ->
                    navController.navigate(Routes.results(score, total)) {
                        popUpTo(Routes.HOME)
                    }
                }
            )
        }

        composable(
            route = Routes.RESULTS,
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 0
            ResultsScreen(
                score = score,
                total = total,
                onPlayAgain = {
                    navController.popBackStack()
                },
                onHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
