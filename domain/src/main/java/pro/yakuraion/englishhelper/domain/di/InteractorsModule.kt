package pro.yakuraion.englishhelper.domain.di

import dagger.Binds
import dagger.Module
import pro.yakuraion.englishhelper.domain.interactors.GetWordsToLearnInteractor
import pro.yakuraion.englishhelper.domain.interactors.GetWordsToLearnInteractorImpl
import pro.yakuraion.englishhelper.domain.interactors.WordsInteractor
import pro.yakuraion.englishhelper.domain.interactors.WordsInteractorImpl
import javax.inject.Singleton

@Module
interface InteractorsModule {

    @Singleton
    @Binds
    fun bindsWordsInteractor(impl: WordsInteractorImpl): WordsInteractor

    @Singleton
    @Binds
    fun bindsGetWordsToLearnInteractor(impl: GetWordsToLearnInteractorImpl): GetWordsToLearnInteractor
}
