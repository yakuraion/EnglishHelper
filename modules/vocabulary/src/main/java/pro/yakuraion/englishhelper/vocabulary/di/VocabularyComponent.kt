package pro.yakuraion.englishhelper.vocabulary.di

import dagger.Component
import pro.yakuraion.englishhelper.commonui.di.viewmodel.NewInjectingViewStateViewModelFactory
import pro.yakuraion.englishhelper.domain.di.UseCasesProvider
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.ViewModelFactoriesModule

@Component(
    dependencies = [UseCasesProvider::class],
    modules = [ViewModelFactoriesModule::class]
)
interface VocabularyComponent {

    fun getViewModelFactory(): NewInjectingViewStateViewModelFactory
}
