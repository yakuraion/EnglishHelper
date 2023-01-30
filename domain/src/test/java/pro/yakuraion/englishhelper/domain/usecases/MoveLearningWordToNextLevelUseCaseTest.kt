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
internal class MoveLearningWordToNextLevelUseCaseTest(
    private val word: LearningWord,
    private val addedToCompleted: Boolean
) : UseCaseTest<MoveLearningWordToNextLevelUseCase>() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var wordsRepository: WordsRepository

    @MockK
    lateinit var learningRepository: LearningRepository

    override fun setUpMocks() {
        coEvery { learningRepository.getLearningDay() } returns LEARNING_DAY
    }

    override fun createUseCase(dispatchers: Dispatchers): MoveLearningWordToNextLevelUseCase {
        return MoveLearningWordToNextLevelUseCaseImpl(
            dispatchers,
            wordsRepository,
            learningRepository
        )
    }

    @Test
    fun `move learning word to the next level`() = runTest {
        val updatedWord = slot<LearningWord>()
        coEvery { wordsRepository.updateTodayLearningDay(capture(updatedWord), any()) } returns Unit

        useCase.moveLearningWordToNextLevel(word)

        if (addedToCompleted) {
            coVerify { wordsRepository.completeWord(word) }
            coVerify(exactly = 0) { wordsRepository.updateTodayLearningDay(any(), any()) }
        } else {
            coVerify { wordsRepository.updateTodayLearningDay(any(), false) }
            coVerify(exactly = 0) { wordsRepository.completeWord(any()) }
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
                false,
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(1), 0),
                false,
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(2), 0),
                false,
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(3), 0),
                false,
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(4), 0),
                true,
            ),
        )
    }
}
