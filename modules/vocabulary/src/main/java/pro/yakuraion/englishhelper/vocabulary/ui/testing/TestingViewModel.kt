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
import pro.yakuraion.englishhelper.domain.interactors.GetWordsToLearnInteractor

class TestingViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getWordsToLearnInteractor: GetWordsToLearnInteractor
) : ViewModel() {

    val day = mutableStateOf(0)

    fun onButtonClick() {
        viewModelScope.launch {
            getWordsToLearnInteractor.getWordsToLearnToday()
            day.value = getWordsToLearnInteractor.getCurrentDay()
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<TestingViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): TestingViewModel
    }
}
