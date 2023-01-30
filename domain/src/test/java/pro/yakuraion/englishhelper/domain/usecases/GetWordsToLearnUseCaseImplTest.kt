package pro.yakuraion.englishhelper.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import pro.yakuraion.englishhelper.domain.utils.DatesUtils
import pro.yakuraion.englishhelper.domain.utils.LearningDatesUtils
import java.util.*

internal class GetWordsToLearnUseCaseImplTest : UseCaseTest<GetWordsToLearnUseCase>() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var wordsRepository: WordsRepository

    @MockK
    lateinit var learningRepository: LearningRepository

    @MockK
    lateinit var datesUtils: DatesUtils

    private lateinit var learningDatesUtils: LearningDatesUtils

    override fun setUpMocks() {
        coEvery { wordsRepository.getLearningWordsAvailableToLearnBy(any()) } returns LEARNING_WORDS
        coEvery { datesUtils.getIsDatesTheSame(any(), any()) } answers { callOriginal() }

        learningDatesUtils = LearningDatesUtils(datesUtils)
    }

    override fun createUseCase(dispatchers: Dispatchers): GetWordsToLearnUseCase {
        return GetWordsToLearnUseCaseImpl(
            dispatchers,
            wordsRepository,
            learningRepository,
            datesUtils,
            learningDatesUtils
        )
    }

    @Test
    fun `get words to learn today when they are already configured`() = runTest {
        val now = Calendar.getInstance()

        coEvery { wordsRepository.getTodayLearningWords() } returns flowOf(LEARNING_WORDS)
        coEvery { learningRepository.getLastLearningDate() } returns now
        coEvery { datesUtils.getCurrentDate() } returns now

        val result = useCase.getWordsToLearnToday().first()

        assertEquals(LEARNING_WORDS, result)

        coVerify(exactly = 0) { wordsRepository.setTodayLearningWords(any()) }
        coVerify(exactly = 0) { learningRepository.increaseLearningDay() }
    }

    @Test
    fun `get words to learn today when they are not configured yet`() = runTest {
        val now = Calendar.getInstance()
        val lastLearningDate = Calendar.getInstance().apply { add(Calendar.DATE, -2) }

        coEvery { datesUtils.getCurrentDate() } returns now
        coEvery { learningRepository.getLastLearningDate() } returns lastLearningDate
        coEvery { learningRepository.getLearningDay() } returns CURRENT_LEARNING_DAY

        val todayWords = slot<List<LearningWord>>()
        coEvery { wordsRepository.setTodayLearningWords(capture(todayWords)) } answers {
            coEvery { wordsRepository.getTodayLearningWords() } returns flowOf(todayWords.captured)
            Unit
        }

        val result = useCase.getWordsToLearnToday().first()

        coVerify { learningRepository.setLastLearningDate(now) }
        coVerify(exactly = 1) { learningRepository.increaseLearningDay() }

        assertEquals(20, result.count { it.name.startsWith("AA_") })
        assertEquals(20, result.count { it.name.startsWith("AB_") })
        assertEquals(20, result.count { it.name.startsWith("AC_") })

        assertEquals(20, result.count { it.name.startsWith("BA_") })
        assertEquals(20, result.count { it.name.startsWith("BB_") })
        assertEquals(20, result.count { it.name.startsWith("BC_") })

        assertEquals(20, result.count { it.name.startsWith("CA_") })
        assertEquals(20, result.count { it.name.startsWith("CB_") })
        assertEquals(10, result.count { it.name.startsWith("CC_") })

        assertEquals(10, result.count { it.name.startsWith("DA_") })
        assertEquals(7, result.count { it.name.startsWith("DB_") })
        assertEquals(5, result.count { it.name.startsWith("DC_") })
    }

    companion object {

        private const val CURRENT_LEARNING_DAY = 2

        private val LEARNING_WORDS = listOf(
            List(20) { index -> LearningWord("AA_$index", MemorizationLevel(0), 0) },
            List(20) { index -> LearningWord("AB_$index", MemorizationLevel(0), 1) },
            List(20) { index -> LearningWord("AC_$index", MemorizationLevel(0), 2) },

            List(20) { index -> LearningWord("BA_$index", MemorizationLevel(1), 0) },
            List(20) { index -> LearningWord("BB_$index", MemorizationLevel(1), 1) },
            List(20) { index -> LearningWord("BC_$index", MemorizationLevel(1), 2) },

            List(20) { index -> LearningWord("CA_$index", MemorizationLevel(2), 0) },
            List(20) { index -> LearningWord("CB_$index", MemorizationLevel(2), 1) },
            List(20) { index -> LearningWord("CC_$index", MemorizationLevel(2), 2) },

            List(20) { index -> LearningWord("DA_$index", MemorizationLevel(3), 0) },
            List(21) { index -> LearningWord("DB_$index", MemorizationLevel(3), 1) },
            List(20) { index -> LearningWord("DC_$index", MemorizationLevel(3), 2) },
        ).flatten()
    }
}
