package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.PrimaryOutlineButton
import pro.yakuraion.englishhelper.commonui.openLink
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.di.daggerViewModel
import java.io.File
import java.net.URLEncoder

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
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.vocabulary_testing_screen_title))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is TestingUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is TestingUiState.NoWords -> {
                    Text(
                        text = "No words",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is TestingUiState.WordSimple -> {
                    TestingLayout(
                        word = uiState.word,
                        onVisitedDictionary = onVisitedDictionary
                    ) {
                        TestingWordSimple(
                            word = uiState.word,
                            onRememberClick = onRememberClick
                        )
                    }
                }
                is TestingUiState.WordWithAudio -> {
                    TestingLayout(
                        word = uiState.word,
                        onVisitedDictionary = onVisitedDictionary
                    ) {
                        TestingWordWithAudio(
                            state = rememberTestingWordWithAudioState(
                                word = uiState.word,
                                soundFile = uiState.soundFile
                            ),
                            onRememberClick = onRememberClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TestingLayout(
    word: String,
    onVisitedDictionary: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        PrimaryOutlineButton(
            onClick = {
                context.openLink(getWordLink(word))
                onVisitedDictionary()
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
        ) {
            Icon(imageVector = Icons.Filled.HelpOutline, contentDescription = null)
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            Text(
                text = stringResource(id = R.string.vocabulary_testing_screen_help_button),
                fontWeight = FontWeight.Bold
            )
        }
        content()
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingScreenSimplePreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.WordSimple("word"),
            onBackClick = {},
            onVisitedDictionary = {},
            onRememberClick = {}
        )
    }
}

private fun getWordLink(word: String): Uri {
    val encodedWord = URLEncoder.encode(word, "utf-8")
    return Uri.parse("https://wooordhunt.ru/word/$encodedWord")
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingScreenWithAudioPreview() {
    AppTheme {
        TestingScreen(
            uiState = TestingUiState.WordWithAudio(
                word = "word",
                soundFile = File("")
            ),
            onBackClick = {},
            onVisitedDictionary = {},
            onRememberClick = {}
        )
    }
}
