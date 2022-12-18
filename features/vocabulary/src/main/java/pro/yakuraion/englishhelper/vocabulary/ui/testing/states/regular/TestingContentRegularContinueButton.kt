package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppTextFieldDefaults
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun TestingContentRegularContinueButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    // only for keep keyboard open
    TextField(
        value = "",
        onValueChange = {},
        modifier = Modifier
            .focusRequester(focusRequester)
            .alpha(0f)
    )

    Button(
        onClick = onClick,
        modifier = modifier.height(TextFieldDefaults.MinHeight),
        shape = AppTextFieldDefaults.shapes().shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = stringResource(id = R.string.vocabulary_testing_screen_continue_button))
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
