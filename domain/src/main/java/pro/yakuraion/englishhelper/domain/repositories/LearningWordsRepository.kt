package pro.yakuraion.englishhelper.domain.repositories

import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord

interface LearningWordsRepository {

    suspend fun getWordsAvailableToLearnBy(learningDay: Int): List<LearningWord>
}
