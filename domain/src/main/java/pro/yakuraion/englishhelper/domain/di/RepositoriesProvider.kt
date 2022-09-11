package pro.yakuraion.englishhelper.domain.di

import pro.yakuraion.englishhelper.domain.repositories.WordsRepository

interface RepositoriesProvider {

    fun provideWordsRepository(): WordsRepository
}
