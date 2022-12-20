package pro.yakuraion.englishhelper.startup.di.viewmodel

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.yakuraion.englishhelper.commonui.compose.compositionlocal.LocalUseCasesProvider
import pro.yakuraion.englishhelper.startup.di.DaggerStartUpComponent

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(
    key: String? = null,
    defaultArgs: Bundle? = null
): VM {
    val useCasesProvider = checkNotNull(LocalUseCasesProvider.current)
    val savedStateRegistryOwner = checkNotNull(LocalSavedStateRegistryOwner.current)
    val component = DaggerStartUpComponent.builder().useCasesProvider(useCasesProvider).build()
    return viewModel(
        key = key,
        factory = component.getViewModelFactory().createFactory(savedStateRegistryOwner, defaultArgs)
    )
}
