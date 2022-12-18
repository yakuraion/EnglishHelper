package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import pro.yakuraion.englishhelper.commonui.MediaPlayerUtils
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingUiState

@Composable
fun TestingContentRegular(
    uiState: TestingUiState.Regular,
    onShowExamplesClick: () -> Unit,
    onVisitedDictionary: () -> Unit,
    onWordTested: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberTestingContentRegularState(uiState = uiState)

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (dictionaryButtonRef, playSoundButtonRef, showExamplesButtonRef, examplesTextRef, answerTextFieldRef) =
            createRefs()

        TestingContentRegularDictionaryButton(
            dictionaryUrl = state.dictionaryUri,
            onVisitedDictionary = onVisitedDictionary,
            modifier = Modifier
                .size(TestingContentRegularActionButtonsDefaults.size)
                .constrainAs(dictionaryButtonRef) {
                    end.linkTo(parent.end)
                    bottom.linkTo(answerTextFieldRef.top, margin = 16.dp)
                }
        )

        TestingContentRegularPlaySoundButton(
            soundUri = state.soundUri,
            modifier = Modifier
                .size(if (state.showExamples) TestingContentRegularActionButtonsDefaults.size else 100.dp)
                .constrainAs(playSoundButtonRef) {
                    if (state.showExamples) {
                        end.linkTo(parent.end)
                        bottom.linkTo(dictionaryButtonRef.top, margin = 16.dp)
                    } else {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(answerTextFieldRef.top)
                    }
                }
        )

        if (!state.showExamples) {
            ShowExamplesButton(
                onClick = onShowExamplesClick,
                modifier = Modifier.constrainAs(showExamplesButtonRef) {
                    start.linkTo(parent.start)
                    bottom.linkTo(answerTextFieldRef.top, margin = 16.dp)
                }
            )
        }

        if (state.showExamples) {
            TestingContentRegularExamplesText(
                examples = state.examples,
                revealExamples = state.revealExamples,
                modifier = Modifier
                    .constrainAs(examplesTextRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(dictionaryButtonRef.start, margin = 16.dp)
                        bottom.linkTo(answerTextFieldRef.top, margin = 16.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )
        }

        TestingContentRegularAnswerTextField(
            answer = state.answer,
            onAnswerChanged = { state.onAnswerChanged(it) },
            onDoneClick = { state.onDoneClick(onWordTested) },
            isActionEnabled = state.isActionEnabled,
            isWrongAnswer = state.isWrongAnswer,
            modifier = Modifier.constrainAs(answerTextFieldRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }

    PlaySoundEffect(state.queueId, state.soundUri)
}

@Composable
private fun ShowExamplesButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text = stringResource(id = R.string.vocabulary_testing_screen_show_examples))
    }
}

@Composable
private fun PlaySoundEffect(queueId: Long, soundUri: String) {
    val context = LocalContext.current
    LaunchedEffect(queueId, soundUri) {
        MediaPlayerUtils.playSound(context, soundUri)
    }
}
