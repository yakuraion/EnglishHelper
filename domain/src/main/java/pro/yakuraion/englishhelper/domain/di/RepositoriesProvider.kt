package pro.yakuraion.englishhelper.domain.di

import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsExamplesRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository

interface RepositoriesProvider {

    fun provideWordsRepository(): WordsRepository

    fun provideWordsSoundsRepository(): WordsSoundsRepository

    fun provideWordsExamplesRepository(): WordsExamplesRepository

    fun provideLearningRepository(): LearningRepository
}
