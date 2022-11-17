package pro.yakuraion.englishhelper.vocabulary.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pro.yakuraion.englishhelper.vocabulary.ui.overview.OverviewScreen

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
        composable(SCREEN.ADD_WORDS.route) { Text(text = "add_words") }
        composable(SCREEN.TESTING.route) { Text(text = "testing") }
    }
}
