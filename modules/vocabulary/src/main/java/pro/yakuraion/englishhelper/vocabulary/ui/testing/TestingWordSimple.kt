package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppButtonWithText
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun TestingWordSimple(
    word: String,
    onWordTested: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            val spaceHeight = 16.dp
            TitleText()
            Spacer(modifier = Modifier.height(spaceHeight))
            WordText(
                word = word,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(spaceHeight))
            AdditionalText()
        }
        AppButtonWithText(
            text = stringResource(id = R.string.vocabulary_testing_screen_simple_yes),
            onClick = onWordTested,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
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
private fun AdditionalText(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.vocabulary_testing_screen_simple_tip),
        modifier = modifier
            .fillMaxWidth()
            .alpha(0.6f),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.labelMedium
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TestingWordSimplePreview() {
    AppTheme {
        TestingWordSimple(
            word = "some word",
            onWordTested = {}
        )
    }
}
