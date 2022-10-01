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
internal class MoveLearningWordToNextLevelUseCaseImplTest(
    private val word: LearningWord,
    private val updatedWord: LearningWord?,
    private val addedToCompleted: Boolean
) : UseCaseTest<MoveLearningWordToNextLevelUseCase>() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var todayLearningWordsRepository: TodayLearningWordsRepository

    @MockK
    lateinit var learningRepository: LearningRepository

    override fun setUpMocks() {
        coEvery { learningRepository.getLearningDay() } returns LEARNING_DAY
    }

    override fun createUseCase(dispatchers: Dispatchers): MoveLearningWordToNextLevelUseCase {
        return MoveLearningWordToNextLevelUseCaseImpl(
            dispatchers,
            todayLearningWordsRepository,
            learningRepository
        )
    }

    @Test
    fun moveLearningWordToNextLevel() = runTest {
        useCase.moveLearningWordToNextLevel(word)

        if (addedToCompleted) {
            coVerify { todayLearningWordsRepository.completeWord(word) }
        } else {
            coVerify { todayLearningWordsRepository.updateWordAndRemoveFromToday(updatedWord!!) }
        }
    }

    companion object {

        private const val LEARNING_DAY = 0

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(
                LearningWord("name", MemorizationLevel(0), 0),
                LearningWord("name", MemorizationLevel(1), 1),
                false,
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(1), 0),
                LearningWord("name", MemorizationLevel(2), 2),
                false,
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(2), 0),
                LearningWord("name", MemorizationLevel(3), 4),
                false,
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(3), 0),
                LearningWord("name", MemorizationLevel(4), 8),
                false,
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(4), 0),
                null,
                true,
            ),
        )
    }
}
