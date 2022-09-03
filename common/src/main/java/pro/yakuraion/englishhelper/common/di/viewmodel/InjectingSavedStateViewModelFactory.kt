package pro.yakuraion.englishhelper.common.di.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * Fabric for creating all ViewModel's in the application.
 */
@Reusable
class InjectingSavedStateViewModelFactory @Inject constructor(
    private val assistedFactoryProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards AssistedFactoryProvider>
) {
    @Suppress("UNCHECKED_CAST")
    fun <VM : ViewModel, Owner> create(
        viewModelClass: KClass<out ViewModel>,
        owner: Owner,
        defaultArgs: Bundle? = null
    ): VM
        where Owner : SavedStateRegistryOwner,
              Owner : ViewModelStoreOwner {
        val factory = object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return assistedFactoryProviders[modelClass]?.get()?.let { factory -> factory.create(handle) as T }
                    ?: throw IllegalArgumentException("Unknown model class $modelClass")
            }
        }
        return ViewModelProvider(owner, factory)[viewModelClass.java] as VM
    }
}

typealias AssistedFactoryProvider = Provider<AssistedSavedStateViewModelFactory<out ViewModel>>
