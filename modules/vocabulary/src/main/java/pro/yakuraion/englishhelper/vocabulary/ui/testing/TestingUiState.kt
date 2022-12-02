package pro.yakuraion.englishhelper.vocabulary.ui.testing

import java.io.File

sealed class TestingUiState {

    object Loading : TestingUiState()

    object NoMoreWords : TestingUiState()

    data class WordSimple(val word: String, val linkUrl: String) : TestingUiState()

    data class WordWithAudio(val word: String, val soundFile: File, val linkUrl: String) : TestingUiState()
}
