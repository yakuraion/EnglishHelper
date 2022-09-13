package pro.yakuraion.englishhelper.vocabulary.ui.vocabulary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import pro.yakuraion.englishhelper.common.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.interactors.WordsInteractor

class VocabularyViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val wordsInteractor: WordsInteractor
) : ViewModel() {

    // todo get value from interactor
    val learningDay: MutableStateFlow<Int> = MutableStateFlow(0)

    val words: Flow<List<LearningWord>> = emptyFlow()

    fun onLearningDaySet(day: Int) {
        // todo set learning dat in interactor
        learningDay.value = day
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<VocabularyViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): VocabularyViewModel
    }
}
