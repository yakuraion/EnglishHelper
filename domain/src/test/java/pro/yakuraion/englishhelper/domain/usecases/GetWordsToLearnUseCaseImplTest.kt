package pro.yakuraion.englishhelper.domain.usecases

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository
import java.util.*

internal class GetWordsToLearnUseCaseImplTest : UseCaseTest<GetWordsToLearnUseCase>() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var learningWordsRepository: LearningWordsRepository

    @MockK
    lateinit var todayLearningWordsRepository: TodayLearningWordsRepository

    @MockK
    lateinit var learningRepository: LearningRepository

    override fun setUpMocks() {
        coEvery { learningWordsRepository.getWordsAvailableToLearnBy(any()) } returns LEARNING_WORDS
    }

    override fun createUseCase(dispatchers: Dispatchers): GetWordsToLearnUseCase {
        return GetWordsToLearnUseCaseImpl(
            dispatchers,
            learningWordsRepository,
            todayLearningWordsRepository,
            learningRepository
        )
    }

    @Test
    fun getNextWordToLearnToday() = runTest {
        coEvery { learningRepository.getLastLearningDate() }
    }

    @Test
    fun getWordsToLearnToday() = runTest {
        coEvery { learningRepository.getLastLearningDate() } returns Calendar.getInstance()

        val expectedWords = listOf(LearningWord("name", MemorizationLevel(1), 1))
        coEvery { todayLearningWordsRepository.getTodayLearningWords() } returns flowOf(expectedWords)

        val words = useCase.getWordsToLearnToday().first()

        assertEquals(expectedWords, words)

        coVerify(exactly = 0) { learningRepository.increaseLearningDay() }
        coVerify(exactly = 0) { todayLearningWordsRepository.setTodayLearningWords(any()) }
    }

    @Test
    fun getWordsToLearnTodayWithUpdateLearningDate() = runTest {
        val lastLearningDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        coEvery { learningRepository.getLastLearningDate() } returns lastLearningDate
        coEvery { learningRepository.getLearningDay() } returns CURRENT_LEARNING_DAY

        val todayWords = slot<List<LearningWord>>()
        coEvery { todayLearningWordsRepository.setTodayLearningWords(capture(todayWords)) } answers {
            coEvery { todayLearningWordsRepository.getTodayLearningWords() } returns flowOf(todayWords.captured)
            Unit
        }

        val calendarSlot = slot<Calendar>()
        coJustRun { learningRepository.setLastLearningDate(capture(calendarSlot)) }

        val words = useCase.getWordsToLearnToday().first()

        assertThat(
            "Not actual calendar is set to the learningRepository",
            Calendar.getInstance().timeInMillis - calendarSlot.captured.timeInMillis < 1000
        )
        coVerify(exactly = 1) { learningRepository.increaseLearningDay() }

        assertEquals(20, words.count { it.name.startsWith("AA_") })
        assertEquals(20, words.count { it.name.startsWith("AB_") })
        assertEquals(20, words.count { it.name.startsWith("AC_") })

        assertEquals(20, words.count { it.name.startsWith("BA_") })
        assertEquals(20, words.count { it.name.startsWith("BB_") })
        assertEquals(20, words.count { it.name.startsWith("BC_") })

        assertEquals(20, words.count { it.name.startsWith("CA_") })
        assertEquals(20, words.count { it.name.startsWith("CB_") })
        assertEquals(10, words.count { it.name.startsWith("CC_") })

        assertEquals(10, words.count { it.name.startsWith("DA_") })
        assertEquals(7, words.count { it.name.startsWith("DB_") })
        assertEquals(5, words.count { it.name.startsWith("DC_") })
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
