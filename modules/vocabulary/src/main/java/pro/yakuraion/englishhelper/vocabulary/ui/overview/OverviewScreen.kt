package pro.yakuraion.englishhelper.vocabulary.ui.overview

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.common.ui.compose.TertiaryIconTextButton
import pro.yakuraion.englishhelper.common.ui.theme.AppTheme
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun OverviewScreen(
    onAddWordsClick: () -> Unit,
    onStartTestingClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = { AddWordsButton(onAddWordsClick = onAddWordsClick) },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            TestingBlock(wordsNumber = 10, onStartTestingClick = onStartTestingClick)
        }
    }
}

@Composable
private fun AddWordsButton(onAddWordsClick: () -> Unit) {
    TertiaryIconTextButton(
        icon = Icons.Filled.Add,
        text = stringResource(R.string.vocabulary_overview_screen_add_words),
        onClick = onAddWordsClick,
        modifier = Modifier.height(48.dp)
    )
}

@Composable
private fun Block(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large
            )
            .padding(16.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
            content()
        }
    }
}

@Composable
private fun TestingBlock(wordsNumber: Int, onStartTestingClick: () -> Unit) {
    Block {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.vocabulary_overview_screen_testing_title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = wordsNumber.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onStartTestingClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.vocabulary_overview_screen_testing_button),
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OverviewScreenPreview() {
    AppTheme {
        OverviewScreen(onAddWordsClick = {}, onStartTestingClick = {})
    }
}
