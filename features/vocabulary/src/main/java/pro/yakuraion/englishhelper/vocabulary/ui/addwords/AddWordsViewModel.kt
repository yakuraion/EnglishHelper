package pro.yakuraion.englishhelper.vocabulary.ui.addwords

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var uiState by mutableStateOf(AddWordsUiState())
        private set

    fun onWordChanged(word: String) {
        uiState = uiState.copy(
            word = word,
            isWordAlreadyExistError = false
        )
    }

    fun onAddWordClick() {
        addWord(uiState.word.trim())
    }

    private fun addWord(word: String) {
        viewModelScope.launch {
            if (!validateIsWordAlreadyExists(word)) return@launch
            uiState = uiState.copy(isAddButtonLoading = true)
            addWordUseCase.addWord(word)
            uiState = uiState.copy(
                word = "",
                isAddButtonLoading = false
            )
        }
    }

    private suspend fun validateIsWordAlreadyExists(word: String): Boolean {
        val isExist = isWordAlreadyExistUseCase.isWordAlreadyExist(word)
        if (isExist) {
            uiState = uiState.copy(isWordAlreadyExistError = true)
        }
        return !isExist
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<AddWordsViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): AddWordsViewModel
    }
}
