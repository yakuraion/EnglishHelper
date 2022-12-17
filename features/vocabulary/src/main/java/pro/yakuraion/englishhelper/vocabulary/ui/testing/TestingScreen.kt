package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppTopAppBar
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppArrowBackButton
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppButtonWithText
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.daggerViewModel

@Composable
fun TestingScreen(
    onBackClick: () -> Unit,
    viewModel: TestingViewModel = daggerViewModel(),
) {
    TestingScreen(
        uiState = viewModel.uiState,
        onBackClick = onBackClick,
        onVisitedDictionary = { viewModel.onVisitedDictionary() },
        onWordTested = { viewModel.onWordTested() }
    )
}

@Composable
private fun TestingScreen(
    uiState: TestingUiState,
    onBackClick: () -> Unit,
    onVisitedDictionary: () -> Unit,
    onWordTested: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(onBackClick = onBackClick)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val alignModifier = Modifier.align(Alignment.Center)
            when (uiState) {
                is TestingUiState.Loading -> {
                    Loading(modifier = alignModifier)
                }
                is TestingUiState.NoMoreWords -> {
                    NoMoreWords(
                        onBackClick = onBackClick,
                        modifier = alignModifier
                    )
                }
                is TestingUiState.WordSimple -> {
                    TestingWordSimple(
                        word = uiState.word,
                        onWordTested = onWordTested
                    )
                }
                is TestingUiState.WordWithAudio -> {
                    TestingWordWithAudio(
                        state = rememberTestingWordWithAudioState(
                            queueId = uiState.queueId,
                            word = uiState.word,
                            soundUri = uiState.soundUri,
                            examples = uiState.examples,
                            dictionaryUrl = uiState.linkUrl
                        ),
                        onDictionaryViewed = onVisitedDictionary,
                        onWordTested = onWordTested
                    )
                }
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

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
    )
}

@Composable
private fun NoMoreWords(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.vocabulary_testing_screen_no_more_words_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        AppButtonWithText(
            text = stringResource(id = R.string.vocabulary_testing_screen_no_more_words_button),
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingScreenSimplePreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.WordSimple(
                0,
                word = "word",
                linkUrl = ""
            ),
            onBackClick = {},
            onVisitedDictionary = {},
            onWordTested = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingScreenWithAudioPreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.WordWithAudio(
                0,
                word = "word",
                soundUri = "",
                linkUrl = "",
                examples = emptyList()
            ),
            onBackClick = {},
            onVisitedDictionary = {},
            onWordTested = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingScreenNoMoreWordsPreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.NoMoreWords,
            onBackClick = {},
            onVisitedDictionary = {},
            onWordTested = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingScreenLoadingPreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.Loading,
            onBackClick = {},
            onVisitedDictionary = {},
            onWordTested = {}
        )
    }
}
