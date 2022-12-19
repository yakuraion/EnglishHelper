package pro.yakuraion.englishhelper.domain.di

import pro.yakuraion.englishhelper.domain.repositories.DatabaseUpdateRepository
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository

interface RepositoriesProvider {

    fun provideDatabaseInfoRepository(): DatabaseUpdateRepository

    fun provideWordsRepository(): WordsRepository

    fun provideWordsSoundsRepository(): WordsSoundsRepository

    fun provideLearningRepository(): LearningRepository
}
