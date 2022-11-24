package pro.yakuraion.englishhelper.commonui.di.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Provider

@Reusable
class InjectingSavedStateViewModelFactory @Inject constructor(
    private val assistedFactoryProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards AssistedFactoryProvider>
) {

    fun createFactory(
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ): ViewModelProvider.Factory {
        return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
            override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactoryProviders[modelClass]?.get()?.let { it.create(handle) as T }
                    ?: throw IllegalArgumentException("Unknown model class $modelClass")
            }
        }
    }
}

typealias AssistedFactoryProvider = Provider<AssistedSavedStateViewModelFactory<out ViewModel>>
