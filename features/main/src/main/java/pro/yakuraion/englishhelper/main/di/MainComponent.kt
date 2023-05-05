package pro.yakuraion.englishhelper.main.di

import dagger.Component
import pro.yakuraion.englishhelper.commonui.di.viewmodel.FeatureComponent
import pro.yakuraion.englishhelper.domain.di.UseCasesProvider
import pro.yakuraion.englishhelper.main.di.viewmodel.ViewModelFactoriesModule

@Component(
    dependencies = [UseCasesProvider::class],
    modules = [ViewModelFactoriesModule::class]
)
internal interface MainComponent : FeatureComponent
