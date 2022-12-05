package pro.yakuraion.englishhelper.vocabulary.ui.overview

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
import pro.yakuraion.englishhelper.common.coroutines.operators.flowCombine
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.domain.usecases.GetCompletedWordsUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetLearningWordsUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetWordsToLearnUseCase

class OverviewViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getWordsToLearnUseCase: GetWordsToLearnUseCase,
    private val getLearningWordsUseCase: GetLearningWordsUseCase,
    private val getCompletedWordsUseCase: GetCompletedWordsUseCase
) : ViewModel() {

    var uiState by mutableStateOf<OverviewUiState>(OverviewUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            flowCombine(
                getWordsToLearnUseCase.getWordsToLearnToday(),
                getLearningWordsUseCase.getLearningWords(),
                getCompletedWordsUseCase.getCompletedWords()
            ).collect { (todayWords, learningWords, completedWords) ->
                uiState = OverviewUiState.Content(
                    numberOfWordsToLearnToday = todayWords.count(),
                    totalNumberOfInProgressWords = learningWords.count(),
                    totalNumberOfCompletedWords = completedWords.count()
                )
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<OverviewViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): OverviewViewModel
    }
}
