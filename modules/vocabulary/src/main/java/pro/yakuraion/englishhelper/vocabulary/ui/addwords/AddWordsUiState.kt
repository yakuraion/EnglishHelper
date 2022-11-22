package pro.yakuraion.englishhelper.vocabulary.ui.addwords

sealed class AddWordsUiState {

    data class EnteringWords(
        val wordsAlreadyExistsError: WordsAlreadyExistsError? = null,
        val isLoading: Boolean = false
    ) : AddWordsUiState() {

        data class WordsAlreadyExistsError(val words: List<String>)
    }

    object WordsAdded : AddWordsUiState()
}
