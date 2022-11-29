package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppArrowBackButton
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppButtonWithText
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppOutlinedButton
import pro.yakuraion.englishhelper.commonui.openLink
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.daggerViewModel
import java.io.File

@Composable
fun TestingScreen(
    onBackClick: () -> Unit,
    viewModel: TestingViewModel = daggerViewModel(),
) {
    TestingScreen(
        uiState = viewModel.uiState,
        onBackClick = onBackClick,
        onVisitedDictionary = { viewModel.onVisitedDictionary() },
        onRememberClick = { viewModel.onRememberClick() }
    )
}

@Composable
private fun TestingScreen(
    uiState: TestingUiState,
    onBackClick: () -> Unit,
    onVisitedDictionary: () -> Unit,
    onRememberClick: () -> Unit
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
                    WordSimple(
                        word = uiState.word,
                        dictionaryLink = uiState.link,
                        onVisitedDictionary = onVisitedDictionary,
                        onRememberClick = onRememberClick,
                        modifier = alignModifier
                    )
                }
                is TestingUiState.WordWithAudio -> {
                    WordWithAudio(
                        word = uiState.word,
                        soundFile = uiState.soundFile,
                        dictionaryLink = uiState.link,
                        onVisitedDictionary = onVisitedDictionary,
                        onRememberClick = onRememberClick,
                        modifier = alignModifier
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
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

@Composable
private fun WordSimple(
    word: String,
    dictionaryLink: Uri,
    onVisitedDictionary: () -> Unit,
    onRememberClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TestingLayout(
        dictionaryLink = dictionaryLink,
        onVisitedDictionary = onVisitedDictionary,
        modifier = modifier
    ) {
        TestingWordSimple(
            word = word,
            onRememberClick = onRememberClick
        )
    }
}

@Composable
private fun WordWithAudio(
    word: String,
    soundFile: File,
    dictionaryLink: Uri,
    onVisitedDictionary: () -> Unit,
    onRememberClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TestingLayout(
        dictionaryLink = dictionaryLink,
        onVisitedDictionary = onVisitedDictionary,
        modifier = modifier
    ) {
        TestingWordWithAudio(
            state = rememberTestingWordWithAudioState(
                word = word,
                soundFile = soundFile
            ),
            onRememberClick = onRememberClick
        )
    }
}

@Composable
private fun TestingLayout(
    dictionaryLink: Uri,
    onVisitedDictionary: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        val context = LocalContext.current
        AppOutlinedButton(
            text = stringResource(id = R.string.vocabulary_testing_screen_help_button),
            onClick = {
                context.openLink(dictionaryLink)
                onVisitedDictionary()
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.HelpOutline, contentDescription = null)
            }
        )
        content()
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingScreenSimplePreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.WordSimple(
                word = "word",
                link = Uri.EMPTY
            ),
            onBackClick = {},
            onVisitedDictionary = {},
            onRememberClick = {}
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
                word = "word",
                soundFile = File(""),
                link = Uri.EMPTY
            ),
            onBackClick = {},
            onVisitedDictionary = {},
            onRememberClick = {}
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
            onRememberClick = {}
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
            onRememberClick = {}
        )
    }
}
