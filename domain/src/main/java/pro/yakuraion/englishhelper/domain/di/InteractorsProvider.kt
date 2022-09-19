package pro.yakuraion.englishhelper.domain.di

import pro.yakuraion.englishhelper.domain.interactors.GetWordsToLearnInteractor
import pro.yakuraion.englishhelper.domain.interactors.LearningWordInteractor
import pro.yakuraion.englishhelper.domain.interactors.WordsInteractor

interface InteractorsProvider {

    fun provideWordsInteractor(): WordsInteractor

    fun provideGetWordsToLearnInteractor(): GetWordsToLearnInteractor

    fun provideLearningWordInteractor(): LearningWordInteractor
}
