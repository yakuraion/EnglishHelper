package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppTextField
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldActionIcon
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldError
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun TestingContentRegularAnswerTextField(
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
