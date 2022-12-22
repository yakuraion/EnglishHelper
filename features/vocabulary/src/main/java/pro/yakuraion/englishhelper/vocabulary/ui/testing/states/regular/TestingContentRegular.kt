package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import pro.yakuraion.englishhelper.commonui.MediaPlayerUtils
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingUiState

@Composable
fun TestingContentRegular(
    uiState: TestingUiState.Regular,
    onVisitedDictionary: () -> Unit,
    onWordTested: () -> Unit,
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberTestingContentRegularState(uiState = uiState)

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (dictionaryButtonRef, playSoundButtonRef, showHideExamplesButtonRef, examplesTextRef, answerTextFieldRef) =
            createRefs()

        val textInputTopGuideline = createGuidelineFromBottom(TextFieldDefaults.MinHeight + 16.dp)

        TestingContentRegularDictionaryButton(
            dictionaryUrl = state.uiState.dictionaryUrl,
            onVisitedDictionary = onVisitedDictionary,
            modifier = Modifier
                .size(TestingContentRegularActionButtonsDefaults.size)
                .constrainAs(dictionaryButtonRef) {
                    end.linkTo(parent.end)
                    bottom.linkTo(answerTextFieldRef.top, margin = 16.dp)
                }
        )

        val playButtonTransition = updateTransition(targetState = state.showExamples, label = "PlayButton animation")
        val playButtonAlignment = playButtonTransition.animatePlayButtonAlignment()
        val playButtonSize = playButtonTransition.animatePlayButtonSize()
        Box(
            modifier = Modifier
                .constrainAs(playSoundButtonRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, margin = 16.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(dictionaryButtonRef.top, margin = 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            TestingContentRegularPlaySoundButton(
                soundUri = state.uiState.wordExtra.soundUri,
                modifier = Modifier
                    .size(playButtonSize)
                    .align(playButtonAlignment)
            )
        }

        TestingContentRegularShowHideExamplesButton(
            isShowDisplay = !state.showExamples,
            onShowClick = { state.onShowExamplesClick() },
            onHideClick = { state.onHideExamplesClick() },
            modifier = Modifier.constrainAs(showHideExamplesButtonRef) {
                start.linkTo(parent.start)
                bottom.linkTo(answerTextFieldRef.top, margin = 8.dp)
            }
        )

        Box(
            modifier = Modifier
                .constrainAs(examplesTextRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(dictionaryButtonRef.start, margin = 16.dp)
                    bottom.linkTo(showHideExamplesButtonRef.top, margin = 8.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            AnimatedVisibility(
                visible = state.showExamples,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                TestingContentRegularExamplesText(
                    examples = state.uiState.wordExtra.examples,
                    revealExamples = state.uiState.isAnswered,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Crossfade(
            targetState = state.uiState.isAnswered,
            modifier = Modifier.constrainAs(answerTextFieldRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(textInputTopGuideline)
            }
        ) { isAnswered ->
            if (isAnswered) {
                TestingContentRegularContinueButton(
                    onClick = onContinueClick,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                TestingContentRegularAnswerTextField(
                    answer = state.answer,
                    onAnswerChanged = { state.onAnswerChanged(it) },
                    onDoneClick = { state.onDoneClick(onWordTested) },
                    isActionEnabled = state.isActionEnabled,
                    isWrongAnswer = state.isWrongAnswer
                )
            }
        }
    }

    PlaySoundEffect(state.uiState.queueId, state.uiState.wordExtra.soundUri)
}

@Composable
private fun PlaySoundEffect(queueId: Long, soundUri: String) {
    val context = LocalContext.current
    LaunchedEffect(queueId) {
        MediaPlayerUtils.playSound(context, soundUri)
    }
}

@Composable
private fun Transition<Boolean>.animatePlayButtonAlignment(): BiasAlignment {
    val value by animateFloat(label = "PlayButton alignment value") { showExamples ->
        if (showExamples) 1f else 0f
    }
    val xEasier = CubicBezierEasing(0f, 0.55f, 0.45f, 1f)
    val yEasier = CubicBezierEasing(0.55f, 0f, 1f, 0.45f)
    val x = xEasier.transform(value)
    val y = yEasier.transform(value)
    return BiasAlignment(x, y)
}

@Composable
private fun Transition<Boolean>.animatePlayButtonSize(): Dp {
    return animateDp(label = "PlayButton size") { showExamples ->
        if (showExamples) TestingContentRegularActionButtonsDefaults.size else 100.dp
    }.value
}
