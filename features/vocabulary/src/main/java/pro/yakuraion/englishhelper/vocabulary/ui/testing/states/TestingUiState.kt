package pro.yakuraion.englishhelper.vocabulary.ui.testing.states

import pro.yakuraion.englishhelper.domain.entities.WordExample

sealed class TestingUiState {

    object Loading : TestingUiState()

    object NoMoreWords : TestingUiState()

    data class Regular(
        val queueId: Long, // to distinguish two words with same name following one by one
        val word: String,
        val soundUri: String,
        val examples: List<WordExample>,
        val revealExamples: Boolean,
        val dictionaryUrl: String,
    ) : TestingUiState()

    data class Lite(
        val queueId: Long, // to distinguish two words with same name following one by one
        val word: String,
        val dictionaryUrl: String
    ) : TestingUiState()
}
