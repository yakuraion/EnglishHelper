package pro.yakuraion.englishhelper.main.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.domain.usecases.UpdateDatabaseAfterMigrationsUseCase

class MainViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    updateDatabaseAfterMigrationsUseCase: UpdateDatabaseAfterMigrationsUseCase,
) : ViewModel() {

    var isUpdated by mutableStateOf(!updateDatabaseAfterMigrationsUseCase.getIsNeedToUpdateDatabase())
        private set

    fun onUpdated() {
        isUpdated = true
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<MainViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): MainViewModel
    }
}
