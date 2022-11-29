package pro.yakuraion.englishhelper.vocabulary.ui.overview

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppTertiaryIconTextButton
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.daggerViewModel

@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = daggerViewModel(),
    onAddWordsClick: () -> Unit,
    onStartTestingClick: () -> Unit
) {
    OverviewScreen(
        uiState = viewModel.uiState,
        onAddWordsClick = onAddWordsClick,
        onStartTestingClick = onStartTestingClick
    )
}

@Composable
private fun OverviewScreen(
    uiState: OverviewUiState,
    onAddWordsClick: () -> Unit,
    onStartTestingClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.vocabulary_overview_screen_title))
                },
            )
        },
        floatingActionButton = {
            if (uiState is OverviewUiState.Content) {
                AddWordsButton(
                    onAddWordsClick = onAddWordsClick
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                OverviewUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is OverviewUiState.Content -> {
                    if (uiState.numberOfWordsToLearnToday > 0) {
                        TestingBlock(
                            wordsNumber = uiState.numberOfWordsToLearnToday,
                            onStartTestingClick = onStartTestingClick,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        EmptyTestingBlock(
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddWordsButton(onAddWordsClick: () -> Unit) {
    AppTertiaryIconTextButton(
        icon = Icons.Filled.Add,
        text = stringResource(R.string.vocabulary_overview_screen_add_words),
        onClick = onAddWordsClick
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
private fun TestingBlock(
    wordsNumber: Int,
    onStartTestingClick: () -> Unit,
    modifier: Modifier
) {
    Block(modifier = modifier) {
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

@Composable
private fun EmptyTestingBlock(
    modifier: Modifier = Modifier
) {
    Block(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.vocabulary_overview_screen_empty_testing_title),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OverviewScreenPreview() {
    AppTheme {
        OverviewScreen(
            uiState = OverviewUiState.Content(numberOfWordsToLearnToday = 10),
            onAddWordsClick = {},
            onStartTestingClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OverviewScreenEmptyWordsPreview() {
    AppTheme {
        OverviewScreen(
            uiState = OverviewUiState.Content(numberOfWordsToLearnToday = 0),
            onAddWordsClick = {},
            onStartTestingClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OverviewScreenProgressPreview() {
    AppTheme {
        OverviewScreen(
            uiState = OverviewUiState.Loading,
            onAddWordsClick = {},
            onStartTestingClick = {}
        )
    }
}
