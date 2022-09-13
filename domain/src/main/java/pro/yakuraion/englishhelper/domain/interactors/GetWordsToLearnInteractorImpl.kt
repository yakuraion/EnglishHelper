package pro.yakuraion.englishhelper.domain.interactors

import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import java.util.*
import javax.inject.Inject

class GetWordsToLearnInteractorImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val learningWordsRepository: LearningWordsRepository
) : GetWordsToLearnInteractor {

    override suspend fun getWordsToLearnToday(): List<LearningWord> {
        return withContext(dispatchers.ioDispatcher) {
            updateLearningDayIfNeeded()
            emptyList()
        }
    }

    private suspend fun updateLearningDayIfNeeded() {
        val now = Calendar.getInstance()
        if (!isLearningTodayAlready(now)) {
            learningWordsRepository.setLastLearningDate(now)
            learningWordsRepository.increaseLearningDay()
        }
    }

    private suspend fun isLearningTodayAlready(now: Calendar): Boolean {
        val nowWithOffset = now.withOffset()
        val lastWithOffset = learningWordsRepository.getLastLearningDate().withOffset()
        return nowWithOffset.get(Calendar.DAY_OF_YEAR) == lastWithOffset.get(Calendar.DAY_OF_YEAR) &&
            nowWithOffset.get(Calendar.YEAR) == lastWithOffset.get(Calendar.YEAR)
    }

    private fun Calendar.withOffset(): Calendar {
        return (this.clone() as Calendar).apply { add(Calendar.HOUR, DATE_OFFSET_HOURS) }
    }

    override suspend fun getCurrentDay(): Int {
        return withContext(dispatchers.ioDispatcher) {
            learningWordsRepository.getLearningDay()
        }
    }

    companion object {

        // We assume that days start at 4 AM
        private const val DATE_OFFSET_HOURS = -4
    }
}
