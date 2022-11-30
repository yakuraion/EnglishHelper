package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
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
import pro.yakuraion.englishhelper.commonui.MediaPlayerUtils
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppTextField
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldActionIcon
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldError
import pro.yakuraion.englishhelper.vocabulary.R
import java.io.File

@Composable
fun TestingWordWithAudio(
    state: TestingWordWithAudioState,
    onWordTested: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        PlaySoundButton(
            soundFile = state.soundFile,
            modifier = Modifier.align(Alignment.Center)
        )
        AnswerTextField(
            answer = state.answer,
            onAnswerChanged = { state.onAnswerChanged(it) },
            onDoneClick = { state.onDoneClick(onWordTested) },
            isActionEnabled = state.isActionEnabled,
            isWrongAnswer = state.isWrongAnswer,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        PlaySoundEffect(state.soundFile)
    }
}

@Composable
private fun PlaySoundButton(
    soundFile: File,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Button(
        onClick = {
            MediaPlayerUtils.playSound(context, soundFile)
        },
        modifier = modifier.size(100.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.VolumeUp,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun AnswerTextField(
    answer: String,
    onAnswerChanged: (String) -> Unit,
    onDoneClick: () -> Unit,
    isActionEnabled: Boolean,
    isWrongAnswer: Boolean,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    AppTextField(
        value = answer,
        onValueChange = onAnswerChanged,
        modifier = modifier.focusRequester(focusRequester),
        maxLines = 1,
        placeholderText = stringResource(id = R.string.vocabulary_testing_screen_answer_placeholder),
        actionIcon = CustomTextFieldActionIcon(
            icon = Icons.Default.Done,
            onClick = onDoneClick,
            isEnabled = isActionEnabled
        ),
        error = CustomTextFieldError(
            isError = isWrongAnswer,
            text = getWrongAnswerErrorText(isWrongAnswer)
        )
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun getWrongAnswerErrorText(isError: Boolean): String {
    return if (isError) {
        stringResource(id = R.string.vocabulary_testing_screen_wrong_answer_error)
    } else {
        ""
    }
}

@Composable
private fun PlaySoundEffect(soundFile: File) {
    val context = LocalContext.current
    LaunchedEffect(soundFile) {
        MediaPlayerUtils.playSound(context, soundFile)
    }
}

class TestingWordWithAudioState(
    val word: String,
    val soundFile: File
) {

    var answer: String by mutableStateOf("")
        private set

    val isActionEnabled: Boolean
        get() = answer.length > 2

    var isWrongAnswer by mutableStateOf(false)
        private set

    fun onAnswerChanged(answer: String) {
        this.answer = answer
        isWrongAnswer = false
    }

    fun onDoneClick(onRightAnswer: () -> Unit) {
        if (word.equals(answer.trim(), false)) {
            onRightAnswer()
        } else {
            isWrongAnswer = true
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
            onWordTested = {}
        )
    }
}
