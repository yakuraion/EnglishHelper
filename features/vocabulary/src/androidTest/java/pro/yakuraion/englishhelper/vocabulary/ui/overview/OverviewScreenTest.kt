package pro.yakuraion.englishhelper.vocabulary.ui.overview

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import org.junit.Test
import pro.yakuraion.englishhelper.commontestsui.ComposeTest
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.vocabulary.R

class OverviewScreenTest : ComposeTest() {

    @Test
    fun testContentState() {
        val uiState = OverviewUiState.Content(
            numberOfWordsToLearnToday = 12,
            totalNumberOfInProgressWords = 3,
            totalNumberOfCompletedWords = 1239
        )
        composeTestRule.setContent {
            AppTheme {
                OverviewScreen(
                    uiState = uiState,
                    onAddWordsClick = {},
                    onStartTestingClick = {},
                    onListWordsClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(getString(R.string.vocabulary_overview_screen_testing_title))
            .assertTextContains("12")
        composeTestRule
            .onNodeWithText(getString(R.string.vocabulary_overview_screen_words_total_in_progress))
            .assertTextContains("3")
        composeTestRule
            .onNodeWithText(getString(R.string.vocabulary_overview_screen_words_total_completed))
            .assertTextContains("1239")

        composeTestRule
            .onNodeWithContentDescription(getString(R.string.vocabulary_overview_semantics_button_add_words))
            .assertIsDisplayed()
    }
}
