package pro.yakuraion.englishhelper.domain.di

import pro.yakuraion.englishhelper.domain.repositories.CompletedWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository

interface RepositoriesProvider {

    fun provideWordsRepository(): WordsRepository

    fun provideWordsSoundsRepository(): WordsSoundsRepository

    fun provideLearningWordsRepository(): LearningWordsRepository

    fun provideCompletedWordsRepository(): CompletedWordsRepository

    fun provideTodayLearningWordsRepository(): TodayLearningWordsRepository

    fun provideLearningRepository(): LearningRepository
}
