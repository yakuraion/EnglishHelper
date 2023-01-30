package pro.yakuraion.englishhelper.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository

@RunWith(Parameterized::class)
internal class MoveLearningWordToPreviousLevelUseCaseTest(
    private val word: LearningWord,
    private val addToTodayLearning: Boolean
) : UseCaseTest<MoveLearningWordToPreviousLevelUseCase>() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var wordsRepository: WordsRepository

    @MockK
    lateinit var learningRepository: LearningRepository

    override fun setUpMocks() {
        coEvery { learningRepository.getLearningDay() } returns LEARNING_DAY
    }

    override fun createUseCase(dispatchers: Dispatchers): MoveLearningWordToPreviousLevelUseCase {
        return MoveLearningWordToPreviousLevelUseCaseImpl(
            dispatchers,
            wordsRepository,
            learningRepository
        )
    }

    @Test
    fun moveLearningWordToPreviousLevel() = runTest {
        val updatedWord = slot<LearningWord>()
        coEvery { wordsRepository.updateTodayLearningDay(capture(updatedWord), any()) } returns Unit

        useCase.moveLearningWordToPreviousLevel(word)

        coVerify { wordsRepository.updateTodayLearningDay(any(), addToTodayLearning) }

        if (!addToTodayLearning) {
            assertNotEquals(word, updatedWord.captured)
        }
    }

    companion object {

        private const val LEARNING_DAY = 0

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(
                LearningWord("name", MemorizationLevel(0), 0),
                true
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(1), 0),
                false
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(2), 0),
                false
            ),
        )
    }
}
