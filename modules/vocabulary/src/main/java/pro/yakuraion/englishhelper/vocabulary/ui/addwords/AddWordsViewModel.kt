package pro.yakuraion.englishhelper.vocabulary.ui.addwords

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.domain.usecases.AddWordUseCase
import pro.yakuraion.englishhelper.domain.usecases.IsWordAlreadyExistUseCase

class AddWordsViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val addWordUseCase: AddWordUseCase,
    private val isWordAlreadyExistUseCase: IsWordAlreadyExistUseCase
) : ViewModel() {

    val uiState = mutableStateOf<AddWordsUiState>(AddWordsUiState.EnteringWords())

    fun onAddWordsClick(words: String) {
        val learningWords = words
            .split(SEPARATOR)
            .map { it.trim() }
        addWords(learningWords)
    }

    private fun addWords(words: List<String>) {
        viewModelScope.launch {
            val uniqueWords = distinctWords(words)
            if (!validateAlreadyExistsWords(uniqueWords)) return@launch
            uniqueWords.forEach { addWordUseCase.addWord(it) }
            uiState.value = AddWordsUiState.WordsAdded
        }
    }

    private fun distinctWords(words: List<String>): List<String> {
        return words.map { it.lowercase() }.distinct()
    }

    private suspend fun validateAlreadyExistsWords(words: List<String>): Boolean {
        val alreadyExistsWords = words.filter { isWordAlreadyExistUseCase.isWordAlreadyExist(it) }
        val isValid = alreadyExistsWords.isEmpty()
        uiState.value = AddWordsUiState.EnteringWords(
            wordsAlreadyExistsError = if (isValid) {
                null
            } else {
                AddWordsUiState.EnteringWords.WordsAlreadyExistsError(alreadyExistsWords)
            }
        )
        return isValid
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<AddWordsViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): AddWordsViewModel
    }

    companion object {

        private const val SEPARATOR = ";"
    }
}
