package pro.yakuraion.englishhelper.data.di

import dagger.Binds
import dagger.Module
import pro.yakuraion.englishhelper.data.repositories.LearningRepositoryImpl
import pro.yakuraion.englishhelper.data.repositories.WordsExamplesRepositoryImpl
import pro.yakuraion.englishhelper.data.repositories.WordsRepositoryImpl
import pro.yakuraion.englishhelper.data.repositories.WordsSoundsRepositoryImpl
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsExamplesRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository
import javax.inject.Singleton

@Module
internal interface RepositoriesModule {

    @Singleton
    @Binds
    fun bindsWordsRepository(impl: WordsRepositoryImpl): WordsRepository

    @Singleton
    @Binds
    fun bindsWordsSoundsRepository(impl: WordsSoundsRepositoryImpl): WordsSoundsRepository

    @Singleton
    @Binds
    fun bindsWordsExamplesRepository(impl: WordsExamplesRepositoryImpl): WordsExamplesRepository

    @Singleton
    @Binds
    fun bindsLearningRepository(impl: LearningRepositoryImpl): LearningRepository
}
