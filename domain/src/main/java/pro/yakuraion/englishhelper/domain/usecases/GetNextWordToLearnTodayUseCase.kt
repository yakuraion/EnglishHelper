package pro.yakuraion.englishhelper.domain.usecases

import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord

interface GetNextWordToLearnTodayUseCase {

    suspend fun getNextWordToLearnToday(): LearningWord?
}
