package pro.yakuraion.englishhelper.vocabulary.ui.overview

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import pro.yakuraion.englishhelper.commontestsui.ComposeBaseRobot
import pro.yakuraion.englishhelper.commontestsui.ComposeTest
import pro.yakuraion.englishhelper.vocabulary.R

fun ComposeTest.overview(action: OverviewRobot.() -> Unit) = OverviewRobot(composeTestRule).apply(action)

class OverviewRobot(testRule: AndroidComposeTestRule<*, *>) : ComposeBaseRobot(testRule) {

    private val numberOfWordsToCheckTodayNode
        get() = composeTestRule.onNodeWithText(getString(R.string.vocabulary_overview_screen_testing_title))

    private val emptyWordsToCheckTodayNode
        get() = composeTestRule.onNodeWithText(getString(R.string.vocabulary_overview_screen_empty_testing_title))

    private val inProgressWordsNode
        get() = composeTestRule.onNodeWithText(getString(R.string.vocabulary_overview_screen_words_total_in_progress))

    private val completedWordsNode
        get() = composeTestRule.onNodeWithText(getString(R.string.vocabulary_overview_screen_words_total_completed))

    private val addWordsNode
        get() = composeTestRule
            .onNodeWithContentDescription(getString(R.string.vocabulary_overview_semantics_button_add_words))

    private val loadingProgressNode
        get() = composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))

    fun checkNumberOfWordsToCheckToday(number: Int) =
        numberOfWordsToCheckTodayNode.assertTextContains(number.toString())

    fun checkNumberOfWordsToCheckTodayDoesNotExist() = numberOfWordsToCheckTodayNode.assertDoesNotExist()

    fun checkEmptyWordsToCheckTodayIsDisplayed() = emptyWordsToCheckTodayNode.assertIsDisplayed()
    fun checkEmptyWordsToCheckTodayDoesNotExist() = emptyWordsToCheckTodayNode.assertDoesNotExist()

    fun checkInProgressWords(number: Int) = inProgressWordsNode.assertTextContains(number.toString())
    fun checkInProgressWordsDoesNotExist() = inProgressWordsNode.assertDoesNotExist()

    fun checkCompletedWords(number: Int) = completedWordsNode.assertTextContains(number.toString())
    fun checkCompletedWordsDoesNotExist() = completedWordsNode.assertDoesNotExist()

    fun checkAddWordsIsDisplayed() = addWordsNode.assertIsDisplayed()
    fun checkAddWordsDoesNotExist() = addWordsNode.assertDoesNotExist()

    fun checkLoadingIsDisplayed() = loadingProgressNode.assertIsDisplayed()
    fun checkLoadingDoesNotExist() = loadingProgressNode.assertDoesNotExist()
}
