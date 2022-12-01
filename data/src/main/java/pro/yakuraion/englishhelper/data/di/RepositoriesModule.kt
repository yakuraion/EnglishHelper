package pro.yakuraion.englishhelper.data.di

import dagger.Binds
import dagger.Module
import pro.yakuraion.englishhelper.data.repositories.CompletedWordsRepositoryImpl
import pro.yakuraion.englishhelper.data.repositories.LearningRepositoryImpl
import pro.yakuraion.englishhelper.data.repositories.LearningWordsRepositoryImpl
import pro.yakuraion.englishhelper.data.repositories.TodayLearningWordsRepositoryImpl
import pro.yakuraion.englishhelper.data.repositories.WordsRepositoryImpl
import pro.yakuraion.englishhelper.data.repositories.WordsSoundsRepositoryImpl
import pro.yakuraion.englishhelper.domain.repositories.CompletedWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository
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
    fun bindsLearningWordsRepository(impl: LearningWordsRepositoryImpl): LearningWordsRepository

    @Singleton
    @Binds
    fun bindsCompletedWordsRepository(impl: CompletedWordsRepositoryImpl): CompletedWordsRepository

    @Singleton
    @Binds
    fun bindsTodayLearningWordsRepository(impl: TodayLearningWordsRepositoryImpl): TodayLearningWordsRepository

    @Singleton
    @Binds
    fun bindsLearningRepository(impl: LearningRepositoryImpl): LearningRepository
}
