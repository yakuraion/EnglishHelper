package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.AppFadingEdgesBox
import pro.yakuraion.englishhelper.domain.entities.WordExample
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingUiState

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

    val scrollMaxOffsetDp = with(LocalDensity.current) { scrollState.maxValue.toDp() }
    val scrollOffsetInDp = with(LocalDensity.current) { scrollState.value.toDp() }

    val maxFadeSize = 36.dp

    AppFadingEdgesBox(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
        topFade = min(scrollOffsetInDp, maxFadeSize),
        bottomFade = min(scrollMaxOffsetDp - scrollOffsetInDp, maxFadeSize)
    ) {
        Text(
            text = examples.toText(),
            modifier = Modifier.verticalScroll(scrollState),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun List<WordExample>.toText(): AnnotatedString {
    var result = buildAnnotatedString { }
    val replaceString = "\uFF3F".repeat(3)
    forEachIndexed { index, wordExample ->
        var orderedSentence = "$index. ${wordExample.sentence}."
        if (index != lastIndex) {
            orderedSentence += "\n"
        }
        result += buildAnnotatedString {
            val parts = orderedSentence.split("%s")
            parts.forEachIndexed { index, part ->
                append(part)
                if (index != parts.lastIndex) {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, letterSpacing = 0.sp)) {
                        append(replaceString)
                    }
                }
            }
        }
    }
    return result
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun Preview() {
    AppTheme {
        Scaffold {
            val state = rememberTestingContentRegularState(
                uiState = TestingUiState.Regular(
                    queueId = 0,
                    word = "word",
                    soundUri = "",
                    examples = List(10) { index ->
                        WordExample("Sentence with given %s #$index", "word")
                    },
                    dictionaryUrl = ""
                )
            )
            state.onExamplesShowClick()

            TestingContentRegularExamplesShowed(
                state = state,
                onVisitedDictionary = {}
            )
        }
    }
}
