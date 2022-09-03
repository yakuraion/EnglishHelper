@file:Suppress("MaxLineLength")

package pro.yakuraion.englishhelper.vocabulary.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pro.yakuraion.englishhelper.common.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.common.di.viewmodel.ViewModelKey
import pro.yakuraion.englishhelper.vocabulary.ui.vocabulary.VocabularyViewModel

@Module
internal interface ViewModelFactoriesModule {

    @Binds
    @IntoMap
    @ViewModelKey(VocabularyViewModel::class)
    fun bindsVocabulary(impl: VocabularyViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
}