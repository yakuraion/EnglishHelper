package pro.yakuraion.englishhelper.vocabulary.ui.vocabulary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pro.yakuraion.englishhelper.common.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.vocabulary.data.daos.LearningWordsDao
import pro.yakuraion.englishhelper.vocabulary.data.entities.LearningWordEntity
import pro.yakuraion.englishhelper.vocabulary.data.preferences.VocabularyPreferences

class VocabularyViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val learningWordsDao: LearningWordsDao,
    private val vocabularyPreferences: VocabularyPreferences
) : ViewModel() {

    val learningDay: MutableStateFlow<Int> = MutableStateFlow(vocabularyPreferences.learningDay)

    val words: Flow<List<LearningWordEntity>> = learningWordsDao.getAll()

    fun onLearningDaySet(day: Int) {
        vocabularyPreferences.learningDay = day
        learningDay.value = day
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<VocabularyViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): VocabularyViewModel
    }
}
