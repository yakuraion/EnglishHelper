package pro.yakuraion.englishhelper.startup.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.commonui.di.viewmodel.ViewModelKey
import pro.yakuraion.englishhelper.startup.ui.StartUpViewModel

@Module
internal interface ViewModelFactoriesModule {

    @Binds
    @IntoMap
    @ViewModelKey(StartUpViewModel::class)
    fun bindsStartUp(impl: StartUpViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
}
