package pro.yakuraion.englishhelper.vocabulary.ui.overview

import org.junit.Test
import pro.yakuraion.englishhelper.commontestsui.ComposeTest
import pro.yakuraion.englishhelper.vocabulary.ui.overview.pages.overview

class OverviewScreenTest : ComposeTest() {

    @Test
    fun testContentState() {
        val uiState = OverviewUiState.Content(
            numberOfWordsToLearnToday = 12,
            totalNumberOfInProgressWords = 3,
            totalNumberOfCompletedWords = 18
        )
        setScreen {
            OverviewScreen(
                uiState = uiState,
                onAddWordsClick = {},
                onStartTestingClick = {},
                onListWordsClick = {}
            )
        }

        overview {
            checkNumberOfWordsToCheckToday(12)
            checkInProgressWords(3)
            checkCompletedWords(18)
            checkAddWordsIsDisplayed()
            checkEmptyWordsToCheckTodayDoesNotExist()
            checkLoadingDoesNotExist()
        }
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

        overview {
            checkEmptyWordsToCheckTodayIsDisplayed()
            checkNumberOfWordsToCheckTodayDoesNotExist()
        }
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

        overview {
            checkLoadingIsDisplayed()
            checkNumberOfWordsToCheckTodayDoesNotExist()
            checkEmptyWordsToCheckTodayDoesNotExist()
            checkInProgressWordsDoesNotExist()
            checkCompletedWordsDoesNotExist()
            checkAddWordsDoesNotExist()
        }
    }
}
