package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppAlertDialog
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppAlertDialogDefaults
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.AppFadingEdgesBox
import pro.yakuraion.englishhelper.domain.entities.WordExtra

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TestingContentRegularExamplesText(
    examples: ImmutableList<WordExtra.Example>,
    revealExamples: Boolean,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val enableShowTranslate = revealExamples

    var ruTranslateToShow: String? by remember { mutableStateOf(null) }

    val scrollMaxOffsetDp = with(LocalDensity.current) { scrollState.maxValue.toDp() }
    val scrollOffsetInDp = with(LocalDensity.current) { scrollState.value.toDp() }

    val maxFadeSize = 36.dp

    AppFadingEdgesBox(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
        topFade = min(scrollOffsetInDp, maxFadeSize),
        bottomFade = min(scrollMaxOffsetDp - scrollOffsetInDp, maxFadeSize)
    ) {
        val text = examples.toText(replaceWithGaps = !revealExamples)
        AnimatedContent(
            targetState = text,
            transitionSpec = {
                fadeIn() with fadeOut()
            }
        ) { targetText ->
            ClickableText(
                text = targetText,
                modifier = Modifier.verticalScroll(scrollState),
                style = MaterialTheme.typography.bodyMedium +
                    TextStyle(color = MaterialTheme.colorScheme.onBackground, textAlign = TextAlign.Justify),
                onClick = { offset ->
                    if (enableShowTranslate) {
                        val annotations = targetText
                            .getStringAnnotations(tag = "ruTranslate", start = offset, end = offset)
                        val ruTranslate = annotations.firstOrNull()?.item
                        ruTranslate?.let { ruTranslateToShow = it }
                    }
                }
            )
        }
    }

    if (ruTranslateToShow != null) {
        AppAlertDialog(
            onDismissRequest = { ruTranslateToShow = null },
            confirmButton = { AppAlertDialogDefaults.ConfirmButton(onConfirm = { ruTranslateToShow = null }) },
            body = { AppAlertDialogDefaults.TextBody(text = ruTranslateToShow.orEmpty()) }
        )
    }
}

@Suppress("MagicNumber")
@Composable
private fun ImmutableList<WordExtra.Example>.toText(replaceWithGaps: Boolean): AnnotatedString {
    var result = buildAnnotatedString { }

    forEachIndexed { index, wordExample ->
        val (stringToReplace, styleToReplace) = if (replaceWithGaps) {
            "\uFF3F".repeat(3) to SpanStyle(fontWeight = FontWeight.Bold, letterSpacing = 0.sp)
        } else {
            wordExample.missedWord to SpanStyle(
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold
            )
        }

        result += buildAnnotatedString {
            pushStringAnnotation(tag = "ruTranslate", annotation = wordExample.ruTranslate)
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append("${index + 1}. ")
            }

            val parts = wordExample.sentenceWithGap.split("%s")
            parts.forEachIndexed { index, part ->
                append(part)
                if (index != parts.lastIndex) {
                    withStyle(styleToReplace) {
                        append(stringToReplace)
                    }
                }
            }
            if (index != lastIndex) {
                append("\n\n")
            }
        }
    }
    return result
}
