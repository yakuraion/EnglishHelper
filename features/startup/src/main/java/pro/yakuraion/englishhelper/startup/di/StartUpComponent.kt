package pro.yakuraion.englishhelper.startup.di

import dagger.Component
import pro.yakuraion.englishhelper.commonui.di.viewmodel.FeatureComponent
import pro.yakuraion.englishhelper.domain.di.UseCasesProvider
import pro.yakuraion.englishhelper.startup.di.viewmodel.ViewModelFactoriesModule

@Component(
    dependencies = [UseCasesProvider::class],
    modules = [ViewModelFactoriesModule::class]
)
interface StartUpComponent : FeatureComponent
