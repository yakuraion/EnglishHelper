package pro.yakuraion.englishhelper.main.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.main.di.viewmodel.featureDaggerViewModel
import pro.yakuraion.englishhelper.vocabulary.ui.VocabularyScreen

@Composable
fun MainScreen(
    viewModel: MainViewModel = featureDaggerViewModel(),
) {
    MainScreen()
}

@Composable
internal fun MainScreen() {
    VocabularyScreen()
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    AppTheme {
        MainScreen()
    }
}
