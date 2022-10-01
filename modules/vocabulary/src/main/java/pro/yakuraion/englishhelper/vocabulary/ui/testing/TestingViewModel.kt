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
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWordFull
import pro.yakuraion.englishhelper.domain.usecases.GetNextWordToLearnTodayUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToNextLevelUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToPreviousLevelUseCase
import timber.log.Timber

class TestingViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getNextWordToLearnTodayUseCase: GetNextWordToLearnTodayUseCase,
    private val moveLearningWordToNextLevelUseCase: MoveLearningWordToNextLevelUseCase,
    private val moveLearningWordToPreviousLevelUseCase: MoveLearningWordToPreviousLevelUseCase
) : ViewModel() {

    val isLoading = mutableStateOf(true)

    val word = mutableStateOf<LearningWordFull?>(null)

    init {
        viewModelScope.launch {
            getNextWordToLearnTodayUseCase.getNextWordToLearnToday().collect { word ->
                Timber.d("newWord = ${word?.word?.name}")
                isLoading.value = false
                this@TestingViewModel.word.value = word
            }
        }
    }

    fun onKnowClick(word: LearningWordFull) {
        viewModelScope.launch {
            moveLearningWordToNextLevelUseCase.moveLearningWordToNextLevel(word.learningWord)
        }
    }

    fun onDoNotKnowClick(word: LearningWordFull) {
        viewModelScope.launch {
            moveLearningWordToPreviousLevelUseCase.moveLearningWordToPreviousLevel(word.learningWord)
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<TestingViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): TestingViewModel
    }
}
