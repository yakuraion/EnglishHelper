package pro.yakuraion.englishhelper.vocabulary.ui.testing

import pro.yakuraion.englishhelper.domain.entities.WordExample

sealed class TestingUiState {

    object Loading : TestingUiState()

    object NoMoreWords : TestingUiState()

    data class WordSimple(
        val queueId: Long,
        val word: String,
        val linkUrl: String
    ) : TestingUiState()

    data class WordWithAudio(
        val queueId: Long, // to distinguish two words with same name following one by one
        val word: String,
        val soundUri: String,
        val examples: List<WordExample>,
        val linkUrl: String
    ) : TestingUiState()
}
