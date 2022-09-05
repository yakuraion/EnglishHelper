package pro.yakuraion.englishhelper.vocabulary.ui.addwords

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.common.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.vocabulary.data.daos.LearningWordsDao
import pro.yakuraion.englishhelper.vocabulary.data.entities.LearningWordEntity
import pro.yakuraion.englishhelper.vocabulary.data.entities.MemorizationLevel
import pro.yakuraion.englishhelper.vocabulary.data.entities.Word

class AddWordsViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val dispatchers: Dispatchers,
    private val learningWordsDao: LearningWordsDao,
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
            writeWordsToDatabase(uniqueWords)
            uiState.value = UIState.WordsAdded
        }
    }

    private fun distinctWords(words: List<String>): List<String> {
        return words.map { it.lowercase() }.distinct()
    }

    private suspend fun validateAlreadyExistsWords(words: List<String>): Boolean {
        val alreadyExistsWords = withContext(dispatchers.ioDispatcher) {
            words.filter { learningWordsDao.getByName(it) != null }
        }
        return if (alreadyExistsWords.isNotEmpty()) {
            uiState.value = UIState.Error("This words already in learning: $alreadyExistsWords")
            false
        } else {
            true
        }
    }

    private suspend fun writeWordsToDatabase(words: List<String>) {
        withContext(dispatchers.ioDispatcher) {
            words
                .map { word ->
                    LearningWordEntity(
                        Word(word),
                        MemorizationLevel.new()
                    )
                }
                .forEach {
                    learningWordsDao.insert(it)
                }
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
