package pro.yakuraion.englishhelper.vocabulary.ui.overview

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppTertiaryIconTextButton
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.daggerViewModel
import pro.yakuraion.englishhelper.vocabulary.ui.overview.cards.OverviewEmptyTestingCard
import pro.yakuraion.englishhelper.vocabulary.ui.overview.cards.OverviewTestingCard
import pro.yakuraion.englishhelper.vocabulary.ui.overview.cards.OverviewWordsTotalCard

@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = daggerViewModel(),
    onAddWordsClick: () -> Unit,
    onStartTestingClick: () -> Unit,
    onListWordsClick: () -> Unit,
) {
    OverviewScreen(
        uiState = viewModel.uiState,
        onAddWordsClick = onAddWordsClick,
        onStartTestingClick = onStartTestingClick,
        onListWordsClick = onListWordsClick,
    )
}

@Composable
private fun OverviewScreen(
    uiState: OverviewUiState,
    onAddWordsClick: () -> Unit,
    onStartTestingClick: () -> Unit,
    onListWordsClick: () -> Unit
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
                    Column {
                        val padding = 16.dp
                        val paddingModifier = Modifier.padding(horizontal = 16.dp)

                        if (uiState.numberOfWordsToLearnToday > 0) {
                            OverviewTestingCard(
                                wordsNumber = uiState.numberOfWordsToLearnToday,
                                onStartTestingClick = onStartTestingClick,
                                modifier = paddingModifier
                            )
                        } else {
                            OverviewEmptyTestingCard(
                                modifier = paddingModifier
                            )
                        }

                        Spacer(modifier = Modifier.height(padding))

                        OverviewWordsTotalCard(
                            numberOfInProgressWords = uiState.totalNumberOfInProgressWords,
                            numberOfCompletedWords = uiState.totalNumberOfCompletedWords,
                            modifier = paddingModifier
                                .clickable { onListWordsClick() }
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OverviewScreenPreview() {
    AppTheme {
        OverviewScreen(
            uiState = OverviewUiState.Content(
                numberOfWordsToLearnToday = 10,
                totalNumberOfInProgressWords = 20,
                totalNumberOfCompletedWords = 30
            ),
            onAddWordsClick = {},
            onStartTestingClick = {},
            onListWordsClick = {},
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OverviewScreenEmptyWordsPreview() {
    AppTheme {
        OverviewScreen(
            uiState = OverviewUiState.Content(
                numberOfWordsToLearnToday = 0,
                totalNumberOfInProgressWords = 20,
                totalNumberOfCompletedWords = 30
            ),
            onAddWordsClick = {},
            onStartTestingClick = {},
            onListWordsClick = {},
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
            onStartTestingClick = {},
            onListWordsClick = {},
        )
    }
}
