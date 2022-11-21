package pro.yakuraion.englishhelper.vocabulary.ui.overview

sealed class OverviewUiState {

    object Loading : OverviewUiState()

    data class Content(
        val numberOfWordsToLearnToday: Int
    ) : OverviewUiState()
}
