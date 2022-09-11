package pro.yakuraion.englishhelper.vocabulary.di

import dagger.Component
import pro.yakuraion.englishhelper.domain.di.InteractorsProvider
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.ViewModelFactoriesModule
import pro.yakuraion.englishhelper.vocabulary.ui.addwords.AddWordsActivity
import pro.yakuraion.englishhelper.vocabulary.ui.testing.TestingActivity
import pro.yakuraion.englishhelper.vocabulary.ui.vocabulary.VocabularyActivity

@Component(
    dependencies = [InteractorsProvider::class],
    modules = [ViewModelFactoriesModule::class]
)
interface VocabularyComponent {

    fun inject(d: VocabularyActivity)

    fun inject(d: AddWordsActivity)

    fun inject(d: TestingActivity)
}
