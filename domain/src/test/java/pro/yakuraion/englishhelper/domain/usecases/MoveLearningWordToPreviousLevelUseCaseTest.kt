package pro.yakuraion.englishhelper.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository

@RunWith(Parameterized::class)
internal class MoveLearningWordToPreviousLevelUseCaseTest(
    private val word: LearningWord,
    private val updatedWord: LearningWord,
    private val removeFromToday: Boolean
) : UseCaseTest<MoveLearningWordToPreviousLevelUseCase>() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var todayLearningWordsRepository: TodayLearningWordsRepository

    @MockK
    lateinit var learningRepository: LearningRepository

    override fun setUpMocks() {
        coEvery { learningRepository.getLearningDay() } returns LEARNING_DAY
    }

    override fun createUseCase(dispatchers: Dispatchers): MoveLearningWordToPreviousLevelUseCase {
        return MoveLearningWordToPreviousLevelUseCaseImpl(
            dispatchers,
            todayLearningWordsRepository,
            learningRepository
        )
    }

    @Test
    fun moveLearningWordToPreviousLevel() = runTest {
        useCase.moveLearningWordToPreviousLevel(word)

        if (removeFromToday) {
            coVerify { todayLearningWordsRepository.updateWordAndRemoveFromToday(updatedWord) }
        } else {
            coVerify { todayLearningWordsRepository.updateWordAndMoveToTheEndOfToday(updatedWord) }
        }
    }

    companion object {

        private const val LEARNING_DAY = 0

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(
                LearningWord("name", MemorizationLevel(0), 0),
                LearningWord("name", MemorizationLevel(0), 0),
                false
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(1), 0),
                LearningWord("name", MemorizationLevel(1), 1),
                true
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(2), 0),
                LearningWord("name", MemorizationLevel(1), 1),
                true
            ),
        )
    }
}
