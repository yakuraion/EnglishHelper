package pro.yakuraion.englishhelper.vocabulary.ui.addwords

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import pro.yakuraion.englishhelper.common.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.domain.interactors.WordsInteractor

class AddWordsViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val wordsInteractor: WordsInteractor
) : ViewModel() {

    val uiState = mutableStateOf<UIState>(UIState.EnteringWords)

    fun onAddWordsClick(wordsText: String) {
        val learningWords = wordsText
            .split(SEPARATOR)
            .map { it.trim() }
        addWords(learningWords)
    }

    private fun addWords(words: List<String>) {
        viewModelScope.launch {
            val uniqueWords = distinctWords(words)
            if (!validateAlreadyExistsWords(uniqueWords)) return@launch
            uniqueWords.forEach { wordsInteractor.addWord(it) }
            uiState.value = UIState.WordsAdded
        }
    }

    private fun distinctWords(words: List<String>): List<String> {
        return words.map { it.lowercase() }.distinct()
    }

    private suspend fun validateAlreadyExistsWords(words: List<String>): Boolean {
        val alreadyExistsWords = words.filter { wordsInteractor.isWordAlreadyExist(it) }
        return if (alreadyExistsWords.isNotEmpty()) {
            uiState.value = UIState.Error("This words already in learning: $alreadyExistsWords")
            false
        } else {
            true
        }
    }

    sealed class UIState {
        object EnteringWords : UIState()
        class Error(val message: String) : UIState()
        object WordsAdded : UIState()
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<AddWordsViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): AddWordsViewModel
    }

    companion object {

        private const val SEPARATOR = ";"
    }
}
