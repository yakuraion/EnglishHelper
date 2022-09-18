package pro.yakuraion.englishhelper.domain.interactors

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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.commontests.MainDispatcherRule
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.entities.MemorizationLevel
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import java.util.*

class GetWordsToLearnInteractorImplTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var dispatchers: Dispatchers

    @MockK
    lateinit var wordsRepository: WordsRepository

    @MockK
    lateinit var learningRepository: LearningRepository

    @MockK
    lateinit var todayLearningWordsRepository: TodayLearningWordsRepository

    lateinit var interactor: GetWordsToLearnInteractorImpl

    @Before
    fun setUp() {
        dispatchers = Dispatchers(
            mainDispatcherRule.testDispatcher,
            mainDispatcherRule.testDispatcher,
            mainDispatcherRule.testDispatcher
        )
        setUpMockk()

        interactor = GetWordsToLearnInteractorImpl(
            dispatchers,
            wordsRepository,
            learningRepository,
            todayLearningWordsRepository
        )
    }

    private fun setUpMockk() {
        coEvery { wordsRepository.getWordsByMaxLearningDay(any()) } returns LEARNING_WORDS
    }

    @Test
    fun getWordsToLearnToday() = runTest {
        coEvery { learningRepository.getLastLearningDate() } returns Calendar.getInstance()

        val expectedWords = listOf(LearningWord(Word("name", null), MemorizationLevel(1), 1))
        coEvery { todayLearningWordsRepository.getTodayWords() } returns flowOf(expectedWords)

        val words = interactor.getWordsToLearnToday().first()

        assertEquals(expectedWords, words)

        coVerify(exactly = 0) { learningRepository.increaseLearningDay() }
        coVerify(exactly = 0) { todayLearningWordsRepository.setTodayWords(any()) }
    }

    @Test
    fun getWordsToLearnTodayWithUpdateLearningDate() = runTest {
        val lastLearningDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        coEvery { learningRepository.getLastLearningDate() } returns lastLearningDate
        coEvery { learningRepository.getLearningDay() } returns CURRENT_LEARNING_DAY

        val todayWords = slot<List<LearningWord>>()
        coEvery { todayLearningWordsRepository.setTodayWords(capture(todayWords)) } answers {
            coEvery { todayLearningWordsRepository.getTodayWords() } returns flowOf(todayWords.captured)
            Unit
        }

        val calendarSlot = slot<Calendar>()
        coJustRun { learningRepository.setLastLearningDate(capture(calendarSlot)) }

        val words = interactor.getWordsToLearnToday().first()

        assertThat(
            "Not actual calendar is set to the learningRepository",
            Calendar.getInstance().timeInMillis - calendarSlot.captured.timeInMillis < 100
        )
        coVerify(exactly = 1) { learningRepository.increaseLearningDay() }

        assertEquals(20, words.count { it.word.name.startsWith("AA_") })
        assertEquals(20, words.count { it.word.name.startsWith("AB_") })
        assertEquals(20, words.count { it.word.name.startsWith("AC_") })

        assertEquals(20, words.count { it.word.name.startsWith("BA_") })
        assertEquals(20, words.count { it.word.name.startsWith("BB_") })
        assertEquals(20, words.count { it.word.name.startsWith("BC_") })

        assertEquals(20, words.count { it.word.name.startsWith("CA_") })
        assertEquals(20, words.count { it.word.name.startsWith("CB_") })
        assertEquals(10, words.count { it.word.name.startsWith("CC_") })

        assertEquals(10, words.count { it.word.name.startsWith("DA_") })
        assertEquals(7, words.count { it.word.name.startsWith("DB_") })
        assertEquals(5, words.count { it.word.name.startsWith("DC_") })
    }

    companion object {

        private const val CURRENT_LEARNING_DAY = 2

        private val LEARNING_WORDS = listOf(
            List(20) { index -> LearningWord(Word("AA_$index", null), MemorizationLevel(0), 0) },
            List(20) { index -> LearningWord(Word("AB_$index", null), MemorizationLevel(0), 1) },
            List(20) { index -> LearningWord(Word("AC_$index", null), MemorizationLevel(0), 2) },

            List(20) { index -> LearningWord(Word("BA_$index", null), MemorizationLevel(1), 0) },
            List(20) { index -> LearningWord(Word("BB_$index", null), MemorizationLevel(1), 1) },
            List(20) { index -> LearningWord(Word("BC_$index", null), MemorizationLevel(1), 2) },

            List(20) { index -> LearningWord(Word("CA_$index", null), MemorizationLevel(2), 0) },
            List(20) { index -> LearningWord(Word("CB_$index", null), MemorizationLevel(2), 1) },
            List(20) { index -> LearningWord(Word("CC_$index", null), MemorizationLevel(2), 2) },

            List(20) { index -> LearningWord(Word("DA_$index", null), MemorizationLevel(3), 0) },
            List(21) { index -> LearningWord(Word("DB_$index", null), MemorizationLevel(3), 1) },
            List(20) { index -> LearningWord(Word("DC_$index", null), MemorizationLevel(3), 2) },
        ).flatten()
    }
}
