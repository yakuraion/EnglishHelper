package pro.yakuraion.englishhelper.vocabulary.ui.addwords

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

    fun onAddWordsClick(wordsText: String) {
        val learningWords = wordsText
            .split(SEPARATOR)
            .map { it.trim() }
            .map { LearningWordEntity(Word(it), MemorizationLevel.new()) }
        viewModelScope.launch {
            withContext(dispatchers.ioDispatcher) {
                learningWords.forEach { learningWordsDao.insert(it) }
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<AddWordsViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): AddWordsViewModel
    }

    companion object {

        private const val SEPARATOR = ";"
    }
}
