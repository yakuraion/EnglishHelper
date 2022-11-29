package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.net.Uri
import java.io.File

sealed class TestingUiState {

    object Loading : TestingUiState()

    object NoMoreWords : TestingUiState()

    data class WordSimple(val word: String, val link: Uri) : TestingUiState()

    data class WordWithAudio(val word: String, val soundFile: File, val link: Uri) : TestingUiState()
}
