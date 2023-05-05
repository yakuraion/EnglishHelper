package pro.yakuraion.englishhelper.main.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.main.di.viewmodel.featureDaggerViewModel
import pro.yakuraion.englishhelper.startup.ui.StartUpScreen
import pro.yakuraion.englishhelper.vocabulary.ui.VocabularyScreen

@Composable
fun MainScreen(
    viewModel: MainViewModel = featureDaggerViewModel(),
) {
    MainScreen(
        isUpdated = viewModel.isUpdated,
        onUpdated = { viewModel.onUpdated() }
    )
}

@Composable
internal fun MainScreen(
    isUpdated: Boolean,
    onUpdated: () -> Unit,
) {
    if (isUpdated) {
        VocabularyScreen()
    } else {
        StartUpScreen(onUpdated = onUpdated)
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    AppTheme {
        MainScreen(
            isUpdated = false,
            onUpdated = {},
        )
    }
}
