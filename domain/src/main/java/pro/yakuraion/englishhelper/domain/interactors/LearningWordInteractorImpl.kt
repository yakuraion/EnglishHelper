package pro.yakuraion.englishhelper.domain.interactors

import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import javax.inject.Inject

// todo add tests
class LearningWordInteractorImpl @Inject constructor(
    private val learningWordsRepository: LearningWordsRepository,
    private val learningRepository: LearningRepository
) : LearningWordInteractor {

    override suspend fun moveWordToNextLevel(word: LearningWord) {
        if (word.isMaxLevel()) {
            // todo add word to completed
        } else {
            updateWord(word) { increaseLevel(it) }
        }
    }

    override suspend fun moveWordToPreviousLevel(word: LearningWord) {
        updateWord(word) { decreaseLevel(it) }
    }

    private suspend fun updateWord(word: LearningWord, modify: LearningWord.(learningDay: Int) -> LearningWord) {
        val learningDay = learningRepository.getLearningDay()
        val newWord = modify.invoke(word, learningDay)
        learningWordsRepository.updateTodayWord(newWord, removeFromToday = newWord.nextDayToLearn != learningDay)
    }
}
