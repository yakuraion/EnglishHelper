package pro.yakuraion.englishhelper.domain.di

import pro.yakuraion.englishhelper.domain.interactors.WordsInteractor

interface InteractorsProvider {

    fun provideWordsInteractor(): WordsInteractor
}
