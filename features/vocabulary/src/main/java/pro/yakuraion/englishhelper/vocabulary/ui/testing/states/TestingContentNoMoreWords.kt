package pro.yakuraion.englishhelper.vocabulary.ui.testing.states

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppButtonWithText
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun TestingContentNoMoreWords(
    onReturnClick: () -> Unit,
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
            onClick = onReturnClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
