@file:Suppress("MaxLineLength")

package pro.yakuraion.englishhelper.vocabulary.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.commonui.di.viewmodel.ViewModelKey
import pro.yakuraion.englishhelper.vocabulary.ui.addwords.AddWordsViewModel
import pro.yakuraion.englishhelper.vocabulary.ui.overview.OverviewViewModel
import pro.yakuraion.englishhelper.vocabulary.ui.testing.TestingViewModel
import pro.yakuraion.englishhelper.vocabulary.ui.vocabulary.VocabularyViewModel

@Module
internal interface ViewModelFactoriesModule {

    @Binds
    @IntoMap
    @ViewModelKey(VocabularyViewModel::class)
    fun bindsVocabulary(impl: VocabularyViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(OverviewViewModel::class)
    fun bindsOverview(impl: OverviewViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(AddWordsViewModel::class)
    fun bindsAddWords(impl: AddWordsViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(TestingViewModel::class)
    fun bindsTesting(impl: TestingViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
}
