package pro.yakuraion.englishhelper.vocabulary.ui.testing

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import pro.yakuraion.englishhelper.common.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.interactors.GetWordsToLearnInteractor
import pro.yakuraion.englishhelper.domain.interactors.LearningWordInteractor
import timber.log.Timber

class TestingViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getWordsToLearnInteractor: GetWordsToLearnInteractor,
    private val learningWordInteractor: LearningWordInteractor
) : ViewModel() {

    val isLoading = mutableStateOf(true)

    val word = mutableStateOf<LearningWord?>(null)

    init {
        viewModelScope.launch {
            getWordsToLearnInteractor.getNextWordToLearnToday().collect { word ->
                Timber.d("newWord = ${word?.word?.name}")
                isLoading.value = false
                this@TestingViewModel.word.value = word
            }
        }
    }

    fun onKnowClick(word: LearningWord) {
        viewModelScope.launch {
            learningWordInteractor.moveWordToNextLevel(word)
        }
    }

    fun onDoNotKnowClick(word: LearningWord) {
        viewModelScope.launch {
            learningWordInteractor.moveWordToPreviousLevel(word)
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<TestingViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): TestingViewModel
    }
}
