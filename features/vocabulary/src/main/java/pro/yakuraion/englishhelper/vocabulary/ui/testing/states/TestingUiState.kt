package pro.yakuraion.englishhelper.vocabulary.ui.testing.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import pro.yakuraion.englishhelper.domain.entities.WordExtra

sealed class TestingUiState {

    object Loading : TestingUiState()

    object NoMoreWords : TestingUiState()

    data class Regular(
        val queueId: Long, // to distinguish two words with same name following one by one
        val wordExtra: WordExtra,
        val dictionaryUrl: String
    ) : TestingUiState() {

        var isAnswered by mutableStateOf(false)
    }

    data class Lite(
        val queueId: Long, // to distinguish two words with same name following one by one
        val word: String,
        val dictionaryUrl: String
    ) : TestingUiState()
}
