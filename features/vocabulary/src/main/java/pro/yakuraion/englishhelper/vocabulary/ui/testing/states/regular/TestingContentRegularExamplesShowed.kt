package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.domain.entities.WordExample

@Composable
fun TestingContentRegularExamplesShowed(
    state: TestingContentRegularState,
    onVisitedDictionary: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        ExamplesText(
            examples = state.examples,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.Bottom)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.align(Alignment.Bottom)) {
            TestingContentRegularPlaySoundButton(
                soundUri = state.soundUri,
                modifier = Modifier.size(TestingContentRegularActionButtonsDefaults.size)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TestingContentRegularDictionaryButton(
                dictionaryUrl = state.dictionaryUri,
                onVisitedDictionary = onVisitedDictionary,
                modifier = Modifier.size(TestingContentRegularActionButtonsDefaults.size)
            )
        }
    }
}

@Composable
private fun ExamplesText(
    examples: List<WordExample>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Text(
        text = examples.toText(),
        modifier = modifier.verticalScroll(scrollState),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.bodyMedium
    )
}

private fun List<WordExample>.toText(): String {
    return map { it.sentence.format("___") }
        .mapIndexed { index, sentence -> "$index. $sentence." }
        .joinToString(separator = "\n")
        .repeat(3)
}
