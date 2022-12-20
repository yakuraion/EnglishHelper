package pro.yakuraion.englishhelper.startup.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.domain.usecases.UpdateDatabaseAfterMigrationsUseCase

class StartUpViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val updateDatabaseAfterMigrationsUseCase: UpdateDatabaseAfterMigrationsUseCase
) : ViewModel() {

    var isUpdated: Boolean by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            val minimumDelayJob = launch { delay(MINIMUM_DELAY_MILLIS) }
            val updateDatabaseJob = launch { updateDatabaseAfterMigrationsUseCase.updateDatabase() }

            minimumDelayJob.join()
            updateDatabaseJob.join()

            isUpdated = true
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<StartUpViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): StartUpViewModel
    }

    companion object {

        private const val MINIMUM_DELAY_MILLIS = 2000L
    }
}
