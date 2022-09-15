package pro.yakuraion.englishhelper.domain.repositories

import java.util.*

interface LearningRepository {

    /**
     * Get actual number of learning day.
     *
     * It is sets as 0 when the user install the application
     * and it is increases by 1 every unique day when the user starts to learn the words.
     */
    suspend fun getLearningDay(): Int

    suspend fun increaseLearningDay()

    suspend fun getLastLearningDate(): Calendar

    suspend fun setLastLearningDate(calendar: Calendar)
}
