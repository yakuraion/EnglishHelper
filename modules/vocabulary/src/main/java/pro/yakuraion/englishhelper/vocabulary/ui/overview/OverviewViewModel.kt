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
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.domain.usecases.GetWordsToLearnUseCase

class OverviewViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getWordsToLearnUseCase: GetWordsToLearnUseCase
) : ViewModel() {

    var uiState by mutableStateOf<OverviewUiState>(OverviewUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            getWordsToLearnUseCase.getWordsToLearnToday().collect { words ->
                uiState = OverviewUiState.Content(numberOfWordsToLearnToday = words.count())
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<OverviewViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): OverviewViewModel
    }
}
