package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.MediaPlayerUtils
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppTextField
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldActionIcon
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldError
import pro.yakuraion.englishhelper.commonui.openLink
import pro.yakuraion.englishhelper.domain.entities.WordExample
import pro.yakuraion.englishhelper.vocabulary.R

private val smallActionButtonSize = 60.dp

@Composable
fun TestingWordWithAudio(
    state: TestingWordWithAudioState,
    onDictionaryViewed: () -> Unit,
    onWordTested: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (state.isExamplesShowing) {
            AudioWithShowedExamples(
                soundUri = state.soundUri,
                examples = state.examples,
                dictionaryUrl = state.dictionaryUrl,
                onDictionaryViewed = onDictionaryViewed,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        } else {
            AudioWithHiddenExamples(
                soundUri = state.soundUri,
                dictionaryUrl = state.dictionaryUrl,
                onExamplesShowClick = { state.onExamplesShowClick() },
                onDictionaryViewed = onDictionaryViewed,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnswerTextField(
            answer = state.answer,
            onAnswerChanged = { state.onAnswerChanged(it) },
            onDoneClick = { state.onDoneClick(onWordTested) },
            isActionEnabled = state.isActionEnabled,
            isWrongAnswer = state.isWrongAnswer
        )

        PlaySoundEffect(state.queueId, state.soundUri)
    }
}

@Composable
private fun AudioWithHiddenExamples(
    soundUri: String,
    dictionaryUrl: String,
    onExamplesShowClick: () -> Unit,
    onDictionaryViewed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        PlaySoundButton(
            soundUri = soundUri,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
        )
        ShowExamplesButton(
            onClick = onExamplesShowClick,
            modifier = Modifier.align(Alignment.BottomStart)
        )
        DictionaryButton(
            dictionaryUrl = dictionaryUrl,
            onDictionaryViewed = onDictionaryViewed,
            modifier = Modifier
                .size(smallActionButtonSize)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun AudioWithShowedExamples(
    soundUri: String,
    examples: List<WordExample>,
    dictionaryUrl: String,
    onDictionaryViewed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        ExamplesText(
            examples = examples,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.Bottom)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.align(Alignment.Bottom)) {
            PlaySoundButton(
                soundUri = soundUri,
                modifier = Modifier.size(smallActionButtonSize)
            )
            Spacer(modifier = Modifier.height(16.dp))
            DictionaryButton(
                dictionaryUrl = dictionaryUrl,
                onDictionaryViewed = onDictionaryViewed,
                modifier = Modifier.size(smallActionButtonSize)
            )
        }
    }
}

@Composable
private fun ExamplesText(
    examples: List<WordExample>,
    modifier: Modifier = Modifier
) {
    val text = examples
        .map { it.sentence.format("___") }
        .mapIndexed { index, sentence -> "$index. $sentence." }
        .joinToString(separator = "\n")
        .repeat(3)

    val scrollState = rememberScrollState()

    Text(
        text = text,
        modifier = modifier.verticalScroll(scrollState),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun ShowExamplesButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text = stringResource(id = R.string.vocabulary_testing_screen_show_examples))
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
private fun PlaySoundButton(
    soundUri: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    ActionButton(
        icon = Icons.Filled.VolumeUp,
        onClick = { MediaPlayerUtils.playSound(context, soundUri) },
        modifier = modifier
    )
}

@Composable
private fun DictionaryButton(
    dictionaryUrl: String,
    onDictionaryViewed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    ActionButton(
        icon = Icons.Default.Translate,
        onClick = {
            context.openLink(Uri.parse(dictionaryUrl))
            onDictionaryViewed()
        },
        modifier = modifier
    )
}

@Composable
private fun ActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconSizeInPercent = 0.6f

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(iconSizeInPercent)
        )
    }
}

@Composable
private fun PlaySoundEffect(queueId: Long, soundUri: String) {
    val context = LocalContext.current
    LaunchedEffect(queueId, soundUri) {
        MediaPlayerUtils.playSound(context, soundUri)
    }
}

class TestingWordWithAudioState(
    val queueId: Long,
    val word: String,
    val soundUri: String,
    val examples: List<WordExample>,
    val dictionaryUrl: String
) {

    var answer: String by mutableStateOf("")
        private set

    val isActionEnabled: Boolean
        get() = answer.length > 2

    var isWrongAnswer by mutableStateOf(false)
        private set

    var isExamplesShowing: Boolean by mutableStateOf(false)
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

    fun onExamplesShowClick() {
        isExamplesShowing = true
    }
}

@Composable
fun rememberTestingWordWithAudioState(
    queueId: Long,
    word: String,
    soundUri: String,
    examples: List<WordExample>,
    dictionaryUrl: String,
): TestingWordWithAudioState {
    return remember(queueId, word, soundUri, examples, dictionaryUrl) {
        TestingWordWithAudioState(queueId, word, soundUri, examples, dictionaryUrl)
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingWordWithAudioPreview() {
    AppTheme {
        TestingWordWithAudio(
            state = TestingWordWithAudioState(
                queueId = 0,
                word = "word",
                soundUri = "",
                examples = emptyList(),
                dictionaryUrl = ""
            ),
            onDictionaryViewed = {},
            onWordTested = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingWordWithAudioAndExamplesPreview() {
    AppTheme {
        val state = rememberTestingWordWithAudioState(
            queueId = 0,
            word = "word",
            soundUri = "",
            examples = List(5) { index ->
                WordExample("Some sentence of %s number $index", "word")
            },
            dictionaryUrl = ""
        )
        state.onExamplesShowClick()
        TestingWordWithAudio(
            state = state,
            onDictionaryViewed = {},
            onWordTested = {}
        )
    }
}
