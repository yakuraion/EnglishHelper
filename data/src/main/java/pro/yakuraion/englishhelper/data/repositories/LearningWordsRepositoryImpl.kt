package pro.yakuraion.englishhelper.data.repositories

import pro.yakuraion.englishhelper.data.preferences.Preferences
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import java.util.*
import javax.inject.Inject

internal class LearningWordsRepositoryImpl @Inject constructor(
    private val preferences: Preferences
) : LearningWordsRepository {

    override suspend fun getLearningDay(): Int {
        return preferences.learningDay
    }

    override suspend fun increaseLearningDay() {
        preferences.learningDay += 1
    }

    override suspend fun getLastLearningDate(): Calendar {
        return preferences.lastLearningDate
    }

    override suspend fun setLastLearningDate(calendar: Calendar) {
        preferences.lastLearningDate = calendar
    }
}
