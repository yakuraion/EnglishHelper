package pro.yakuraion.englishhelper.vocabulary.ui.testing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import pro.yakuraion.englishhelper.common.di.viewmodel.AssistedSavedStateViewModelFactory

class TestingViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<TestingViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): TestingViewModel
    }
}
