package pro.yakuraion.englishhelper.vocabulary.ui.testing

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
        val linkUrl: String
    ) : TestingUiState()
}
