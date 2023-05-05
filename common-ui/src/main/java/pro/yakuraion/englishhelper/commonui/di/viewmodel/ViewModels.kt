package pro.yakuraion.englishhelper.commonui.di.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.yakuraion.englishhelper.commonui.compose.compositionlocal.LocalUseCasesProvider
import pro.yakuraion.englishhelper.domain.di.UseCasesProvider

@Suppress("UNCHECKED_CAST")
@Composable
inline fun <reified VM : ViewModel> daggerViewModel(
    key: String? = null,
    crossinline componentProvider: (useCasesProvider: UseCasesProvider) -> FeatureComponent,
): VM {
    val useCasesProvider = checkNotNull(LocalUseCasesProvider.current)
    return viewModel(
        modelClass = VM::class.java,
        key = key,
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val factory = componentProvider(useCasesProvider).getViewModelFactories()[modelClass]?.get()
                return factory?.create(extras.createSavedStateHandle()) as? T
                    ?: error("Unknown modelClass $modelClass")
            }
        }
    )
}
