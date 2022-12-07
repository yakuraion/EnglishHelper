package pro.yakuraion.englishhelper.domain.usecases

import pro.yakuraion.englishhelper.domain.entities.learning.LearningWordFull

interface GetNextWordToLearnTodayUseCase {

    suspend fun getNextWordToLearnToday(): LearningWordFull?
}
