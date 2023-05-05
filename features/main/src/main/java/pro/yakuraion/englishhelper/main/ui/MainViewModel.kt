package pro.yakuraion.englishhelper.main.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory

class MainViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<MainViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): MainViewModel
    }
}
