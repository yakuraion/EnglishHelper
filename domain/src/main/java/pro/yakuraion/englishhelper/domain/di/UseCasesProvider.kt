package pro.yakuraion.englishhelper.domain.di

import pro.yakuraion.englishhelper.domain.usecases.AddWordUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetNextWordToLearnTodayUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetWordsToLearnUseCase
import pro.yakuraion.englishhelper.domain.usecases.IsWordAlreadyExistUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToNextLevelUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToPreviousLevelUseCase

interface UseCasesProvider {

    fun provideAddWordUseCase(): AddWordUseCase

    fun provideGetNextWordToLearnTodayUseCase(): GetNextWordToLearnTodayUseCase

    fun provideGetWordsToLearnUseCase(): GetWordsToLearnUseCase

    fun provideIsWordAlreadyExistUseCase(): IsWordAlreadyExistUseCase

    fun provideMoveLearningWordToNextLevelUseCase(): MoveLearningWordToNextLevelUseCase

    fun provideMoveLearningWordToPreviousLevelUseCase(): MoveLearningWordToPreviousLevelUseCase
}
