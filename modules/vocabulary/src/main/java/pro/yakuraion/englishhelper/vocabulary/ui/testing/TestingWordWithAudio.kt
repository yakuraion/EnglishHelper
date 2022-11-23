package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.content.Context
import android.content.res.Configuration
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextField
import pro.yakuraion.englishhelper.vocabulary.R
import java.io.File

class TestingWordWithAudioState(
    val word: String,
    val soundFile: File
) {

    var answer: String by mutableStateOf("")
        private set

    var actionEnabled by mutableStateOf(false)
        private set

    var isWrongAnswerError by mutableStateOf(false)
        private set

    fun onAnswerChanged(answer: String) {
        this.answer = answer
        actionEnabled = answer.length > 2
        isWrongAnswerError = false
    }

    fun onDoneClick(onRightAnswer: () -> Unit) {
        if (word.equals(answer.trim(), false)) {
            onRightAnswer()
        } else {
            isWrongAnswerError = true
        }
    }
}

@Composable
fun rememberTestingWordWithAudioState(
    word: String,
    soundFile: File
): TestingWordWithAudioState {
    return remember(word, soundFile) {
        TestingWordWithAudioState(word, soundFile)
    }
}

@Composable
fun TestingWordWithAudio(
    state: TestingWordWithAudioState,
    onRememberClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val content = LocalContext.current
        Icon(
            imageVector = Icons.Filled.VolumeUp,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .clickable {
                    playSound(content, state.soundFile)
                }
        )
        val errorMessage = if (state.isWrongAnswerError) {
            stringResource(id = R.string.vocabulary_testing_screen_wrong_answer_error)
        } else {
            ""
        }
        val focusRequester = remember { FocusRequester() }
        CustomTextField(
            value = state.answer,
            onValueChange = { state.onAnswerChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .focusRequester(focusRequester),
            placeholder = stringResource(id = R.string.vocabulary_testing_screen_answer_placeholder),
            maxLines = 1,
            actionEnabled = state.actionEnabled,
            actionIcon = Icons.Default.Done,
            onActionClick = { state.onDoneClick(onRememberClick) },
            isError = state.isWrongAnswerError,
            errorMessage = errorMessage
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

private fun playSound(context: Context, file: File) {
    MediaPlayer.create(context, Uri.fromFile(file)).start()
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingWordWithAudioPreview() {
    AppTheme {
        TestingWordWithAudio(
            state = TestingWordWithAudioState(
                word = "word",
                soundFile = File("")
            ),
            onRememberClick = {}
        )
    }
}
