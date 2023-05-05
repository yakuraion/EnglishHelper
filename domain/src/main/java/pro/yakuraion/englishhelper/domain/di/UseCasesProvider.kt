package pro.yakuraion.englishhelper.domain.di

import pro.yakuraion.englishhelper.domain.usecases.AddWordUseCase
import pro.yakuraion.englishhelper.domain.usecases.DeleteWordUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetCompletedWordsUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetLearningWordsUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetNextWordToLearnTodayUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetWordExtraUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetWordsToLearnUseCase
import pro.yakuraion.englishhelper.domain.usecases.IsWordAlreadyExistUseCase
import pro.yakuraion.englishhelper.domain.usecases.LearnCompletedWordAgainUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToNextLevelUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToPreviousLevelUseCase
import pro.yakuraion.englishhelper.domain.usecases.ResetLearningWordProgressUseCase

interface UseCasesProvider {

    fun provideGetWordExtraUseCase(): GetWordExtraUseCase

    fun provideGetLearningWordsUseCase(): GetLearningWordsUseCase

    fun provideGetCompletedWordsUseCase(): GetCompletedWordsUseCase

    fun provideAddWordUseCase(): AddWordUseCase

    fun provideDeleteWordUseCase(): DeleteWordUseCase

    fun provideGetNextWordToLearnTodayUseCase(): GetNextWordToLearnTodayUseCase

    fun provideGetWordsToLearnUseCase(): GetWordsToLearnUseCase

    fun provideIsWordAlreadyExistUseCase(): IsWordAlreadyExistUseCase

    fun provideMoveLearningWordToNextLevelUseCase(): MoveLearningWordToNextLevelUseCase

    fun provideMoveLearningWordToPreviousLevelUseCase(): MoveLearningWordToPreviousLevelUseCase

    fun provideResetLearningWordProgressUseCase(): ResetLearningWordProgressUseCase

    fun provideLearnCompletedWordAgainUseCase(): LearnCompletedWordAgainUseCase
}
