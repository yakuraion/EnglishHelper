package pro.yakuraion.englishhelper.vocabulary.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pro.yakuraion.englishhelper.vocabulary.ui.addwords.AddWordsScreen
import pro.yakuraion.englishhelper.vocabulary.ui.overview.OverviewScreen
import pro.yakuraion.englishhelper.vocabulary.ui.testing.TestingScreen

private enum class SCREEN(val route: String) {
    OVERVIEW("overview"),
    ADD_WORDS("add_words"),
    TESTING("testing")
}

@Composable
fun VocabularyScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SCREEN.OVERVIEW.route) {
        composable(SCREEN.OVERVIEW.route) {
            OverviewScreen(
                onAddWordsClick = { navController.navigate(SCREEN.ADD_WORDS.route) },
                onStartTestingClick = { navController.navigate(SCREEN.TESTING.route) }
            )
        }
        composable(SCREEN.ADD_WORDS.route) {
            AddWordsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(SCREEN.TESTING.route) {
            TestingScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
