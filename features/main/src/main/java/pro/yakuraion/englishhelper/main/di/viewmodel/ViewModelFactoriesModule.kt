package pro.yakuraion.englishhelper.main.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.commonui.di.viewmodel.ViewModelKey
import pro.yakuraion.englishhelper.main.ui.MainViewModel

@Module
internal interface ViewModelFactoriesModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindsMain(impl: MainViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
}
