package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.MediaPlayerUtils
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppTextField
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldActionIcon
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldError
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingUiState

@Composable
fun TestingContentRegular(
    uiState: TestingUiState.Regular,
    onShowExamplesClick: () -> Unit,
    onVisitedDictionary: () -> Unit,
    onWordTested: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberTestingContentRegularState(uiState = uiState)

    Column(modifier = modifier.fillMaxSize()) {
        val examplesModifier = Modifier
            .fillMaxWidth()
            .weight(1f)

        if (state.showExamples) {
            TestingContentRegularExamplesShowed(
                state = state,
                onVisitedDictionary = onVisitedDictionary,
                modifier = examplesModifier
            )
        } else {
            TestingContentRegularExamplesHidden(
                state = state,
                onShowExamplesClick = onShowExamplesClick,
                onVisitedDictionary = onVisitedDictionary,
                modifier = examplesModifier
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
private fun PlaySoundEffect(queueId: Long, soundUri: String) {
    val context = LocalContext.current
    LaunchedEffect(queueId, soundUri) {
        MediaPlayerUtils.playSound(context, soundUri)
    }
}
