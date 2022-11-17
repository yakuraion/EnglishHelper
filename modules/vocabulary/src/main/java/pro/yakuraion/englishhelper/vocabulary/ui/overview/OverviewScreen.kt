package pro.yakuraion.englishhelper.vocabulary.ui.overview

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.common.ui.compose.FloatingButtonWithIconAndText
import pro.yakuraion.englishhelper.common.ui.compose.InContentFullWidthButton
import pro.yakuraion.englishhelper.common.ui.theme.AppTheme
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun OverviewScreen(
    onAddWordsClick: () -> Unit,
    onStartTestingClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = { StartTestingButton(onStartTestingClick = onStartTestingClick) },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LearningWordsView(wordsCount = 10, onAddMoreClick = onAddWordsClick)
        }
    }
}

@Composable
fun StartTestingButton(onStartTestingClick: () -> Unit) {
    FloatingButtonWithIconAndText(
        icon = Icons.Filled.PlayArrow,
        text = stringResource(R.string.vocabulary_overview_screen_start_testing),
        onClick = onStartTestingClick
    )
}

@Composable
fun LearningWordsView(wordsCount: Int, onAddMoreClick: () -> Unit) {
    Column {
        Text(text = stringResource(R.string.vocabulary_overview_screen_current_learning_words, wordsCount))
        InContentFullWidthButton(
            text = stringResource(R.string.vocabulary_overview_screen_add_words),
            onClick = onAddMoreClick,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OverviewScreenPreview() {
    AppTheme {
        OverviewScreen(onAddWordsClick = {}, onStartTestingClick = {})
    }
}
