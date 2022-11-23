package pro.yakuraion.englishhelper

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.di.LocalUseCasesProvider
import pro.yakuraion.englishhelper.domain.di.UseCasesProviderHolder
import pro.yakuraion.englishhelper.vocabulary.ui.VocabularyScreen

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val useCasesProvider = (application as UseCasesProviderHolder).getUseCasesProvider()
            CompositionLocalProvider(LocalUseCasesProvider provides useCasesProvider) {
                AppTheme {
                    setUpStatusBarColor()
                    setUpActivityBackground()
                    VocabularyScreen()
                }
            }
        }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    private fun setUpStatusBarColor() {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()
        val backgroundColor = MaterialTheme.colorScheme.background
        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setSystemBarsColor(
                color = backgroundColor,
                darkIcons = useDarkIcons
            )
            onDispose {}
        }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    private fun setUpActivityBackground() {
        val backgroundDrawable = ColorDrawable(MaterialTheme.colorScheme.background.toArgb())
        LaunchedEffect(Unit) {
            window.setBackgroundDrawable(backgroundDrawable)
        }
    }
}
