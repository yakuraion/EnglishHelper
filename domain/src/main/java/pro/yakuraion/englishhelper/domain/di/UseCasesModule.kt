package pro.yakuraion.englishhelper.domain.di

import dagger.Binds
import dagger.Module
import pro.yakuraion.englishhelper.domain.usecases.AddWordUseCase
import pro.yakuraion.englishhelper.domain.usecases.AddWordUseCaseImpl
import pro.yakuraion.englishhelper.domain.usecases.GetCompletedWordsUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetCompletedWordsUseCaseImpl
import pro.yakuraion.englishhelper.domain.usecases.GetLearningWordsUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetLearningWordsUseCaseImpl
import pro.yakuraion.englishhelper.domain.usecases.GetNextWordToLearnTodayUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetNextWordToLearnTodayUseCaseImpl
import pro.yakuraion.englishhelper.domain.usecases.GetWordsToLearnUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetWordsToLearnUseCaseImpl
import pro.yakuraion.englishhelper.domain.usecases.IsWordAlreadyExistUseCase
import pro.yakuraion.englishhelper.domain.usecases.IsWordAlreadyExistUseCaseImpl
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToNextLevelUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToNextLevelUseCaseImpl
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToPreviousLevelUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToPreviousLevelUseCaseImpl
import javax.inject.Singleton

@Module
internal interface UseCasesModule {

    @Singleton
    @Binds
    fun bindsGetLearningWordsUseCase(impl: GetLearningWordsUseCaseImpl): GetLearningWordsUseCase

    @Singleton
    @Binds
    fun bindsGetCompletedWordsUseCase(impl: GetCompletedWordsUseCaseImpl): GetCompletedWordsUseCase

    @Singleton
    @Binds
    fun bindsAddWordUseCase(impl: AddWordUseCaseImpl): AddWordUseCase

    @Singleton
    @Binds
    fun bindsGetNextWordToLearnTodayUseCase(impl: GetNextWordToLearnTodayUseCaseImpl): GetNextWordToLearnTodayUseCase

    @Singleton
    @Binds
    fun bindsGetWordsToLearnUseCase(impl: GetWordsToLearnUseCaseImpl): GetWordsToLearnUseCase

    @Singleton
    @Binds
    fun bindsIsWordAlreadyExistUseCase(impl: IsWordAlreadyExistUseCaseImpl): IsWordAlreadyExistUseCase

    @Singleton
    @Binds
    fun bindsMoveLearningWordToNextLevelUseCase(
        impl: MoveLearningWordToNextLevelUseCaseImpl
    ): MoveLearningWordToNextLevelUseCase

    @Singleton
    @Binds
    fun bindsMoveLearningWordToPreviousLevelUseCase(
        impl: MoveLearningWordToPreviousLevelUseCaseImpl
    ): MoveLearningWordToPreviousLevelUseCase
}
