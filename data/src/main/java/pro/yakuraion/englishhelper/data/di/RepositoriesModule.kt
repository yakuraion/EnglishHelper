package pro.yakuraion.englishhelper.data.di

import dagger.Binds
import dagger.Module
import pro.yakuraion.englishhelper.data.repositories.WordsRepositoryImpl
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Singleton

@Module
internal interface RepositoriesModule {

    @Singleton
    @Binds
    fun bindsWordsRepository(impl: WordsRepositoryImpl): WordsRepository
}
