package pro.yakuraion.englishhelper.vocabulary.ui.vocabulary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.common.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.vocabulary.data.daos.LearningWordsDao
import pro.yakuraion.englishhelper.vocabulary.data.entities.LearningWordEntity
import pro.yakuraion.englishhelper.vocabulary.data.entities.MemorizationLevel
import pro.yakuraion.englishhelper.vocabulary.data.entities.Word

class VocabularyViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val learningWordsDao: LearningWordsDao
) : ViewModel() {

    val words: Flow<List<LearningWordEntity>> = learningWordsDao.getAll()

    fun onAddNameClick(name: String) {
        val learningWord = LearningWordEntity(
            word = Word(name),
            memorizationLevel = MemorizationLevel.new()
        )
        learningWordsDao.insert(learningWord)
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<VocabularyViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): VocabularyViewModel
    }
}
