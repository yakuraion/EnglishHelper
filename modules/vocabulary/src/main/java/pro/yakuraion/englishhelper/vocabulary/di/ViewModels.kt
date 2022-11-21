package pro.yakuraion.englishhelper.vocabulary.di

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.yakuraion.englishhelper.commonui.di.LocalUseCasesProvider

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(
    key: String? = null,
    defaultArgs: Bundle? = null
): VM {
    val useCasesProvider = checkNotNull(LocalUseCasesProvider.current)
    val savedStateRegistryOwner = checkNotNull(LocalSavedStateRegistryOwner.current)
    val component = DaggerVocabularyComponent.builder().useCasesProvider(useCasesProvider).build()
    component.getViewModelFactory()
    return viewModel(
        key = key,
        factory = component.getViewModelFactory().createFactory(savedStateRegistryOwner, defaultArgs)
    )
}
