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

    var word by mutableStateOf("")
        private set

    var isWordAlreadyExistError by mutableStateOf<IsWordAlreadyExistError?>(null)
        private set

    data class IsWordAlreadyExistError(val word: String)

    var isLoading by mutableStateOf(false)
        private set

    fun onWordChanged(word: String) {
        this.word = word
    }

    fun onAddWordClick() {
        addWord(word.trim())
    }

    private fun addWord(word: String) {
        viewModelScope.launch {
            if (!validateIsWordAlreadyExists(word)) return@launch
            isLoading = true
            addWordUseCase.addWord(word)
            isLoading = false
            this@AddWordsViewModel.word = ""
        }
    }

    private suspend fun validateIsWordAlreadyExists(word: String): Boolean {
        val isExist = isWordAlreadyExistUseCase.isWordAlreadyExist(word)
        isWordAlreadyExistError = if (isExist) {
            IsWordAlreadyExistError(word)
        } else {
            null
        }
        return !isExist
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<AddWordsViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): AddWordsViewModel
    }
}
