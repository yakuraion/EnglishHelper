package pro.yakuraion.englishhelper.vocabulary.ui.listwords

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppPagerWithTabs
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppTopAppBar
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppArrowBackButton
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.featureDaggerViewModel
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage.ListWordsCompletedPage
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage.ListWordsInProgressPage
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage.rememberListWordsPageState

@Composable
fun ListWordsScreen(
    viewModel: ListWordsViewModel = featureDaggerViewModel(),
    onBackClick: () -> Unit,
) {
    ListWordsScreen(
        inProgressWords = viewModel.inProgressWords,
        completedWords = viewModel.completedWords,
        onResetInProgressWords = { viewModel.onResetLearningWords(it) },
        onDeleteInProgressWords = { viewModel.onDeleteLearningWords(it) },
        onResetCompletedWords = { viewModel.onResetCompletedWords(it) },
        onDeleteCompletedWords = { viewModel.onDeleteCompletedWords(it) },
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ListWordsScreen(
    inProgressWords: ImmutableList<LearningWord>,
    completedWords: ImmutableList<CompletedWord>,
    onResetInProgressWords: (List<LearningWord>) -> Unit,
    onDeleteInProgressWords: (List<LearningWord>) -> Unit,
    onResetCompletedWords: (List<CompletedWord>) -> Unit,
    onDeleteCompletedWords: (List<CompletedWord>) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = { TopBar(onBackClick = onBackClick) }
    ) { paddingValues ->
        val inProgressPageState = rememberListWordsPageState(words = inProgressWords)
        val completedPageState = rememberListWordsPageState(words = completedWords)

        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            AppPagerWithTabs(
                count = Page.values().count(),
                title = { Page.values()[it].title() },
                modifier = Modifier.padding(paddingValues),
                onPageChanged = {
                    inProgressPageState.deselectAll()
                    completedPageState.deselectAll()
                }
            ) { pageNumber ->
                when (Page.values()[pageNumber]) {
                    Page.IN_PROGRESS -> ListWordsInProgressPage(
                        state = inProgressPageState,
                        onResetInProgressWords = { words ->
                            onResetInProgressWords.invoke(words)
                            inProgressPageState.deselectAll()
                        },
                        onDeleteInProgressWords = { words ->
                            onDeleteInProgressWords.invoke(words)
                            inProgressPageState.deselectAll()
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                    Page.COMPLETED -> ListWordsCompletedPage(
                        state = completedPageState,
                        onResetCompletedWords = { words ->
                            onResetCompletedWords.invoke(words)
                            completedPageState.deselectAll()
                        },
                        onDeleteCompletedWords = { words ->
                            onDeleteCompletedWords.invoke(words)
                            completedPageState.deselectAll()
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(onBackClick: () -> Unit) {
    AppTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.vocabulary_list_words_screen_title))
        },
        navigationIcon = {
            AppArrowBackButton(onBackClick = onBackClick)
        }
    )
}

private enum class Page {
    IN_PROGRESS {

        @Composable
        override fun title(): String = stringResource(id = R.string.vocabulary_list_words_screen_tab_in_progress_title)
    },

    COMPLETED {

        @Composable
        override fun title(): String = stringResource(id = R.string.vocabulary_list_words_screen_tab_completed_title)
    };

    @Composable
    abstract fun title(): String
}

@Suppress("MagicNumber")
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListWordsScreenPreview() {
    AppTheme {
        ListWordsScreen(
            inProgressWords = (0..30).map { index ->
                LearningWord(
                    name = "word $index",
                    memorizationLevel = MemorizationLevel(index % 4),
                    nextDayToLearn = 0
                )
            }.toImmutableList(),
            completedWords = (0..30).map { index ->
                CompletedWord(name = "word $index")
            }.toImmutableList(),
            onResetInProgressWords = {},
            onDeleteInProgressWords = {},
            onResetCompletedWords = {},
            onDeleteCompletedWords = {},
            onBackClick = {}
        )
    }
}
