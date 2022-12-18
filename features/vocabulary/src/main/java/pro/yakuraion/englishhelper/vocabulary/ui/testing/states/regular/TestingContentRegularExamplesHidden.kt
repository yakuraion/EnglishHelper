package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun TestingContentRegularExamplesHidden(
    state: TestingContentRegularState,
    onShowExamplesClick: () -> Unit,
    onVisitedDictionary: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        TestingContentRegularPlaySoundButton(
            soundUri = state.soundUri,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
        )
        ShowExamplesButton(
            onClick = onShowExamplesClick,
            modifier = Modifier.align(Alignment.BottomStart)
        )
        TestingContentRegularDictionaryButton(
            dictionaryUrl = state.dictionaryUri,
            onVisitedDictionary = onVisitedDictionary,
            modifier = Modifier
                .size(TestingContentRegularActionButtonsDefaults.size)
                .align(Alignment.BottomEnd)
        )
    }
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
