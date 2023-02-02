package pro.yakuraion.englishhelper.vocabulary.ui.testing.states

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun TestingContentLite(
    state: TestingUiState.Lite,
    onWordCheckSuccess: () -> Unit,
    onWordCheckFailed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 140.dp)
        ) {
            val spaceHeight = 16.dp
            TitleText()
            Spacer(modifier = Modifier.height(spaceHeight))
            WordText(
                word = state.word,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerButton(
                text = stringResource(id = R.string.vocabulary_testing_screen_simple_no),
                onClick = onWordCheckFailed,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = modifier.width(40.dp))
            AnswerButton(
                text = stringResource(id = R.string.vocabulary_testing_screen_simple_yes),
                onClick = onWordCheckSuccess
            )
        }
    }
}

@Composable
private fun TitleText(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.vocabulary_testing_screen_simple_question),
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun WordText(
    word: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = word,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large
            )
            .padding(horizontal = 24.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.Medium,
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
private fun AnswerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleLarge
) {
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minWidth = 120.dp)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = style
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingWordSimplePreview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            TestingContentLite(
                state = TestingUiState.Lite(0, "word", ""),
                onWordCheckSuccess = {},
                onWordCheckFailed = {}
            )
        }
    }
}
