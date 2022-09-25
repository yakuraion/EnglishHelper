package pro.yakuraion.englishhelper.domain.di

import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository

interface RepositoriesProvider {

    fun provideLearningWordsRepository(): LearningWordsRepository

    fun provideWordsSoundsRepository(): WordsSoundsRepository

    fun provideLearningRepository(): LearningRepository
}
