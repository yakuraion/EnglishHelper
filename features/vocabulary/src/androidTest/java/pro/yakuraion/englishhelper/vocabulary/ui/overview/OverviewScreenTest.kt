package pro.yakuraion.englishhelper.vocabulary.ui.overview

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import org.junit.Test
import pro.yakuraion.englishhelper.commontestsui.ComposeTest
import pro.yakuraion.englishhelper.vocabulary.R

class OverviewScreenTest : ComposeTest() {

    @Test
    fun testNonZeroWordsToLearnToday() {
        val uiState = OverviewUiState.Content(
            numberOfWordsToLearnToday = 12,
            totalNumberOfInProgressWords = 0,
            totalNumberOfCompletedWords = 0
        )
        setScreen {
            OverviewScreen(
                uiState = uiState,
                onAddWordsClick = {},
                onStartTestingClick = {},
                onListWordsClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(getString(R.string.vocabulary_overview_screen_testing_title))
            .assertTextContains("12")
    }

    @Test
    fun testZeroWordsToLearnToday() {
        val uiState = OverviewUiState.Content(
            numberOfWordsToLearnToday = 0,
            totalNumberOfInProgressWords = 0,
            totalNumberOfCompletedWords = 0
        )
        setScreen {
            OverviewScreen(
                uiState = uiState,
                onAddWordsClick = {},
                onStartTestingClick = {},
                onListWordsClick = {}
            )
        }

        composeTestRule.onNodeWithText(getString(R.string.vocabulary_overview_screen_empty_testing_title))
            .assertIsDisplayed()
    }

    @Test
    fun testInProgressAndCompletedWords() {
        val uiState = OverviewUiState.Content(
            numberOfWordsToLearnToday = 0,
            totalNumberOfInProgressWords = 12,
            totalNumberOfCompletedWords = 46
        )
        setScreen {
            OverviewScreen(
                uiState = uiState,
                onAddWordsClick = {},
                onStartTestingClick = {},
                onListWordsClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(getString(R.string.vocabulary_overview_screen_words_total_in_progress))
            .assertTextContains("12")
        composeTestRule
            .onNodeWithText(getString(R.string.vocabulary_overview_screen_words_total_completed))
            .assertTextContains("46")
    }

    @Test
    fun testAddWordsButton() {
        val uiState = OverviewUiState.Content(
            numberOfWordsToLearnToday = 0,
            totalNumberOfInProgressWords = 0,
            totalNumberOfCompletedWords = 0
        )
        setScreen {
            OverviewScreen(
                uiState = uiState,
                onAddWordsClick = {},
                onStartTestingClick = {},
                onListWordsClick = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription(getString(R.string.vocabulary_overview_semantics_button_add_words))
            .assertIsDisplayed()
    }

    @Test
    fun testLoading() {
        val uiState = OverviewUiState.Loading
        setScreen {
            OverviewScreen(
                uiState = uiState,
                onAddWordsClick = {},
                onStartTestingClick = {},
                onListWordsClick = {}
            )
        }

        composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }
}
