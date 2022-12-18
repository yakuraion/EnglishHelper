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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.AppFadingEdgesBox
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
            revealExamples = state.revealExamples,
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
    revealExamples: Boolean,
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
            text = examples.toText(replaceWithGaps = !revealExamples),
            modifier = Modifier.verticalScroll(scrollState),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Suppress("MagicNumber")
@Composable
private fun List<WordExample>.toText(replaceWithGaps: Boolean): AnnotatedString {
    var result = buildAnnotatedString { }

    forEachIndexed { index, wordExample ->
        val (stringToReplace, styleToReplace) = if (replaceWithGaps) {
            "\uFF3F".repeat(3) to SpanStyle(fontWeight = FontWeight.Bold, letterSpacing = 0.sp)
        } else {
            wordExample.missedWord to SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }

        var orderedSentence = "$index. ${wordExample.sentence}."
        if (index != lastIndex) {
            orderedSentence += "\n"
        }

        result += buildAnnotatedString {
            val parts = orderedSentence.split("%s")
            parts.forEachIndexed { index, part ->
                append(part)
                if (index != parts.lastIndex) {
                    withStyle(styleToReplace) {
                        append(stringToReplace)
                    }
                }
            }
        }
    }
    return result
}
