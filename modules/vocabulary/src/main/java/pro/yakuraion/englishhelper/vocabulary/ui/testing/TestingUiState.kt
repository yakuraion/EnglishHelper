package pro.yakuraion.englishhelper.vocabulary.ui.testing

import java.io.File

sealed class TestingUiState {

    object Loading : TestingUiState()

    object NoWords : TestingUiState()

    data class WordSimple(val word: String) : TestingUiState()

    data class WordWithAudio(val word: String, val soundFile: File) : TestingUiState()
}
