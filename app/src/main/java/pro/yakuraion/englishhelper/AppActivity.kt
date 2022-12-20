package pro.yakuraion.englishhelper

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import pro.yakuraion.englishhelper.commonui.compose.compositionlocal.LocalUseCasesProvider
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.startup.ui.StartUpScreen
import pro.yakuraion.englishhelper.vocabulary.ui.VocabularyScreen

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val useCasesProvider = (application as App).appComponent
            CompositionLocalProvider(LocalUseCasesProvider provides useCasesProvider) {
                AppTheme {
                    SetUpStatusBarColorEffect()
                    SetUpActivityBackgroundEffect()

                    AppScreen()
                }
            }
        }
    }

    @Composable
    private fun SetUpStatusBarColorEffect() {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()
        val backgroundColor = MaterialTheme.colorScheme.background

        LaunchedEffect(systemUiController, useDarkIcons, backgroundColor) {
            systemUiController.setSystemBarsColor(
                color = backgroundColor,
                darkIcons = useDarkIcons
            )
        }
    }

    @Composable
    private fun SetUpActivityBackgroundEffect() {
        val backgroundColor = MaterialTheme.colorScheme.background

        LaunchedEffect(backgroundColor) {
            val drawable = ColorDrawable(backgroundColor.toArgb())
            window.setBackgroundDrawable(drawable)
        }
    }

    @Composable
    private fun AppScreen() {
        val useCasesProvider = LocalUseCasesProvider.current!!
        val isNeedToUpdate = useCasesProvider.provideUpdateDatabaseAfterMigrationsUseCase().getIsNeedToUpdateDatabase()
        var isUpdated by remember { mutableStateOf(!isNeedToUpdate) }

        if (isUpdated) {
            VocabularyScreen()
        } else {
            StartUpScreen(onUpdated = { isUpdated = true })
        }
    }
}
