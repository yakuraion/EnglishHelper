package pro.yakuraion.englishhelper.vocabulary.di.viewmodel

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.yakuraion.englishhelper.commonui.compose.compositionlocal.LocalUseCasesProvider
import pro.yakuraion.englishhelper.vocabulary.di.DaggerVocabularyComponent

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