package pro.yakuraion.englishhelper.vocabulary.di

import dagger.BindsInstance
import dagger.Component
import pro.yakuraion.englishhelper.vocabulary.data.daos.LearningWordsDao
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.ViewModelFactoriesModule
import pro.yakuraion.englishhelper.vocabulary.ui.vocabulary.VocabularyActivity

@Component(
    modules = [ViewModelFactoriesModule::class]
)
interface VocabularyComponent {

    fun inject(d: VocabularyActivity)

    @Component.Builder
    interface Builder {

        fun build(): VocabularyComponent

        @BindsInstance
        fun wordsDao(dao: LearningWordsDao): Builder
    }

    companion object {

        fun create(dependencies: VocabularyDependencies): VocabularyComponent {
            return DaggerVocabularyComponent.builder()
                .wordsDao(dependencies.wordsDao())
                .build()
        }
    }
}
