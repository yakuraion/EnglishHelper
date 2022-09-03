package pro.yakuraion.englishhelper.vocabulary.di

import dagger.Component
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.ViewModelFactoriesModule
import pro.yakuraion.englishhelper.vocabulary.ui.vocabulary.VocabularyActivity

@Component(
    dependencies = [VocabularyDependencies::class],
    modules = [ViewModelFactoriesModule::class]
)
interface VocabularyComponent {

    fun inject(d: VocabularyActivity)

    companion object {

        fun create(dependencies: VocabularyDependencies): VocabularyComponent {
            return DaggerVocabularyComponent.builder()
                .vocabularyDependencies(dependencies)
                .build()
        }
    }
}
