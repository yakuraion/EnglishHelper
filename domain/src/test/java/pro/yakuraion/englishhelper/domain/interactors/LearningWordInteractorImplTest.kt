package pro.yakuraion.englishhelper.domain.interactors

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.domain.entities.MemorizationLevel
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository

@RunWith(Parameterized::class)
class LearningWordInteractorImplTest(
    private val word: LearningWord,
    private val nextLevelParams: UpdatedWordParams,
    private val previousLevelParams: UpdatedWordParams
) : InteractorTest() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var wordsRepository: WordsRepository

    @MockK
    lateinit var learningRepository: LearningRepository

    @MockK
    lateinit var todayLearningWordsRepository: TodayLearningWordsRepository

    lateinit var interactor: LearningWordInteractorImpl

    override fun setUp() {
        super.setUp()
        setUpMockk()
        interactor = LearningWordInteractorImpl(
            wordsRepository,
            learningRepository,
            todayLearningWordsRepository
        )
    }

    private fun setUpMockk() {
        coEvery { learningRepository.getLearningDay() } returns LEARNING_DAY
    }

    @Test
    fun moveWordToNextLevel() = runTest {
        interactor.moveWordToNextLevel(word)

        coVerify { todayLearningWordsRepository.removeFromTodayWords(word) }
        coVerify { wordsRepository.updateWord(nextLevelParams.updatedWord) }
        if (nextLevelParams.addToTodayLearning) {
            coVerify { todayLearningWordsRepository.addToTodayWords(nextLevelParams.updatedWord) }
        }
    }

    @Test
    fun moveWordToPreviousLevel() = runTest {
        interactor.moveWordToPreviousLevel(word)

        coVerify { todayLearningWordsRepository.removeFromTodayWords(word) }
        coVerify { wordsRepository.updateWord(previousLevelParams.updatedWord) }
        if (previousLevelParams.addToTodayLearning) {
            coVerify { todayLearningWordsRepository.addToTodayWords(previousLevelParams.updatedWord) }
        }
    }

    class UpdatedWordParams(val updatedWord: LearningWord, val addToTodayLearning: Boolean)

    companion object {

        private const val LEARNING_DAY = 0

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(
                LearningWord(Word("name", null), MemorizationLevel(0), 0),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(1), 1), false),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(0), 0), true)
            ),
            arrayOf(
                LearningWord(Word("name", null), MemorizationLevel(1), 0),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(2), 2), false),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(1), 1), false)
            ),
            arrayOf(
                LearningWord(Word("name", null), MemorizationLevel(2), 0),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(3), 4), false),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(1), 1), false)
            ),
        )
    }
}
