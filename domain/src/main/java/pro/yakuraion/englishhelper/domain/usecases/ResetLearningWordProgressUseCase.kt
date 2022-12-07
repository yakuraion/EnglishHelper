package pro.yakuraion.englishhelper.domain.usecases

import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord

interface ResetLearningWordProgressUseCase {

    suspend fun resetLearningWordProgress(word: LearningWord)
}
