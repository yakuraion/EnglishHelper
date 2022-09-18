package pro.yakuraion.englishhelper.domain.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.entities.MemorizationLevel
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import java.util.*
import javax.inject.Inject
import kotlin.math.max

class GetWordsToLearnInteractorImpl @Inject constructor(
    private val dispatchers: Dispatchers,
    private val wordsRepository: WordsRepository,
    private val learningRepository: LearningRepository,
    private val todayLearningWordsRepository: TodayLearningWordsRepository
) : GetWordsToLearnInteractor {

    override suspend fun getWordsToLearnToday(): Flow<List<LearningWord>> {
        return withContext(dispatchers.ioDispatcher) {
            checkIsNewLearningDay { now ->
                increaseLearningDay(now)
                resetWordsToLearn()
            }
            todayLearningWordsRepository.getTodayWords()
        }
    }

    private suspend fun checkIsNewLearningDay(onNewLearningDay: suspend (now: Calendar) -> Unit) {
        val now = Calendar.getInstance()
        if (isNewLearningDay(now)) {
            onNewLearningDay.invoke(now)
        }
    }

    private suspend fun isNewLearningDay(now: Calendar): Boolean {
        val nowWithOffset = now.withOffset()
        val lastWithOffset = learningRepository.getLastLearningDate().withOffset()
        return nowWithOffset.get(Calendar.DAY_OF_YEAR) != lastWithOffset.get(Calendar.DAY_OF_YEAR) ||
            nowWithOffset.get(Calendar.YEAR) != lastWithOffset.get(Calendar.YEAR)
    }

    private fun Calendar.withOffset(): Calendar {
        return (this.clone() as Calendar).apply { add(Calendar.HOUR, DATE_OFFSET_HOURS) }
    }

    private suspend fun increaseLearningDay(now: Calendar) {
        learningRepository.setLastLearningDate(now)
        learningRepository.increaseLearningDay()
    }

    private suspend fun resetWordsToLearn() {
        val currentLearningDay = learningRepository.getLearningDay()
        val words = wordsRepository.getWordsByMaxLearningDay(currentLearningDay)
            .groupBy { WordGroupIdentifier(it.memorizationLevel, it.nextDayToLearn) }
            .flatMap { entry ->
                getSelectionOfWordsForToday(
                    words = entry.value,
                    memorizationLevel = entry.key.memorizationLevel,
                    nextDayToLearn = entry.key.nextDayToLearn,
                    currentLearningDay = currentLearningDay
                )
            }
            .shuffled()
        todayLearningWordsRepository.setTodayWords(words)
    }

    private data class WordGroupIdentifier(val memorizationLevel: MemorizationLevel, val nextDayToLearn: Int)

    private fun getSelectionOfWordsForToday(
        words: List<LearningWord>,
        memorizationLevel: MemorizationLevel,
        nextDayToLearn: Int,
        currentLearningDay: Int
    ): List<LearningWord> {
        val numberOfSubsets = max(nextDayToLearn + memorizationLevel.maxDeviation - currentLearningDay + 1, 1)
        val wordsInSubset = words.count() / numberOfSubsets
        return words.shuffled().take(wordsInSubset)
    }

    companion object {

        // We assume that days start at 4 AM
        private const val DATE_OFFSET_HOURS = -4
    }
}
