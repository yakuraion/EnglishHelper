package pro.yakuraion.englishhelper.vocabulary.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pro.yakuraion.englishhelper.vocabulary.ui.addwords.AddWordsScreen
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.ListWordsScreen
import pro.yakuraion.englishhelper.vocabulary.ui.overview.OverviewScreen
import pro.yakuraion.englishhelper.vocabulary.ui.testing.TestingScreen

private enum class Screen(val route: String) {
    OVERVIEW("overview"),
    ADD_WORDS("add_words"),
    TESTING("testing"),
    LIST_WORDS("list_words"),
}

@Composable
fun VocabularyScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.OVERVIEW.route) {
        composable(Screen.OVERVIEW.route) {
            OverviewScreen(
                onAddWordsClick = { navController.navigate(Screen.ADD_WORDS.route) },
                onStartTestingClick = { navController.navigate(Screen.TESTING.route) },
                onListWordsClick = { navController.navigate(Screen.LIST_WORDS.route) }
            )
        }
        composable(Screen.ADD_WORDS.route) {
            AddWordsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.TESTING.route) {
            TestingScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.LIST_WORDS.route) {
            ListWordsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
