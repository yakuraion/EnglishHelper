package pro.yakuraion.englishhelper.domain.di

import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository

interface RepositoriesProvider {

    fun provideWordsRepository(): WordsRepository

    fun provideWordsSoundsRepository(): WordsSoundsRepository
}
