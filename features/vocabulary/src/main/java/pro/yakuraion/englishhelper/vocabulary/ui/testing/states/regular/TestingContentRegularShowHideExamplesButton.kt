package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun TestingContentRegularShowHideExamplesButton(
    isShowDisplay: Boolean,
    onShowClick: () -> Unit,
    onHideClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = if (isShowDisplay) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown
    val textId = if (isShowDisplay) {
        R.string.vocabulary_testing_screen_show_examples
    } else {
        R.string.vocabulary_testing_screen_hide_examples
    }
    val onClick = if (isShowDisplay) onShowClick else onHideClick

    TextButton(onClick = onClick, modifier = modifier) {
        Icon(imageVector = icon, contentDescription = null)
        Text(text = stringResource(textId))
    }
}
