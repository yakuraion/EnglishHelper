package pro.yakuraion.englishhelper.startup.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory

class StartUpViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var isUpdated: Boolean by mutableStateOf(false)
        private set

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<StartUpViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): StartUpViewModel
    }
}
