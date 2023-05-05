package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppTopAppBar
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppArrowBackButton
import pro.yakuraion.englishhelper.domain.entities.WordExtra
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.featureDaggerViewModel
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingContentLite
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingContentLoading
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingContentNoMoreWords
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingUiState
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular.TestingContentRegular

@Composable
fun TestingScreen(
    onBackClick: () -> Unit,
    viewModel: TestingViewModel = featureDaggerViewModel(),
) {
    TestingScreen(
        uiState = viewModel.uiState,
        onBackClick = onBackClick,
        onRegularVisitedDictionary = { viewModel.onRegularVisitedDictionary() },
        onRegularWordCheck = { viewModel.onRegularWordCheck() },
        onLiteWordCheckSuccess = { viewModel.onLiteWordCheckSuccess() },
        onLiteWordCheckFailed = { viewModel.onLiteWordCheckFailed() },
        onContinueClick = { viewModel.onRegularContinueClick() }
    )
}

@Composable
private fun TestingScreen(
    uiState: TestingUiState,
    onBackClick: () -> Unit,
    onRegularVisitedDictionary: () -> Unit,
    onRegularWordCheck: () -> Unit,
    onLiteWordCheckSuccess: () -> Unit,
    onLiteWordCheckFailed: () -> Unit,
    onContinueClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(onBackClick = onBackClick)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    top = paddingValues.calculateTopPadding(),
                    end = 16.dp,
                    bottom = 16.dp + paddingValues.calculateBottomPadding()
                ),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is TestingUiState.Loading -> TestingContentLoading()

                is TestingUiState.NoMoreWords -> TestingContentNoMoreWords(onReturnClick = onBackClick)

                is TestingUiState.Lite -> TestingContentLite(
                    state = uiState,
                    onWordCheckSuccess = onLiteWordCheckSuccess,
                    onWordCheckFailed = onLiteWordCheckFailed
                )

                is TestingUiState.Regular -> TestingContentRegular(
                    uiState = uiState,
                    onVisitedDictionary = onRegularVisitedDictionary,
                    onWordTested = onRegularWordCheck,
                    onContinueClick = onContinueClick
                )
            }
        }
    }
}

@Composable
private fun TopBar(onBackClick: () -> Unit) {
    AppTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.vocabulary_testing_screen_title))
        },
        navigationIcon = {
            AppArrowBackButton(onBackClick = onBackClick)
        }
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingRegularPreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.Regular(
                0,
                wordExtra = WordExtra(
                    name = "word",
                    remoteSoundUri = "",
                    localSoundUri = "",
                    examples = persistentListOf()
                ),
                dictionaryUrl = "",
            ),
            onBackClick = {},
            onRegularVisitedDictionary = {},
            onRegularWordCheck = {},
            onLiteWordCheckSuccess = {},
            onLiteWordCheckFailed = {},
            onContinueClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingRegularAnsweredPreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.Regular(
                0,
                wordExtra = WordExtra(
                    name = "word",
                    remoteSoundUri = "",
                    localSoundUri = "",
                    examples = persistentListOf()
                ),
                dictionaryUrl = "",
            ).apply { isAnswered = true },
            onBackClick = {},
            onRegularVisitedDictionary = {},
            onRegularWordCheck = {},
            onLiteWordCheckSuccess = {},
            onLiteWordCheckFailed = {},
            onContinueClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingLitePreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.Lite(
                0,
                word = "word",
                dictionaryUrl = ""
            ),
            onBackClick = {},
            onRegularVisitedDictionary = {},
            onRegularWordCheck = {},
            onLiteWordCheckSuccess = {},
            onLiteWordCheckFailed = {},
            onContinueClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingNoMoreWordsPreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.NoMoreWords,
            onBackClick = {},
            onRegularVisitedDictionary = {},
            onRegularWordCheck = {},
            onLiteWordCheckSuccess = {},
            onLiteWordCheckFailed = {},
            onContinueClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingLoadingPreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.Loading,
            onBackClick = {},
            onRegularVisitedDictionary = {},
            onRegularWordCheck = {},
            onLiteWordCheckSuccess = {},
            onLiteWordCheckFailed = {},
            onContinueClick = {}
        )
    }
}
