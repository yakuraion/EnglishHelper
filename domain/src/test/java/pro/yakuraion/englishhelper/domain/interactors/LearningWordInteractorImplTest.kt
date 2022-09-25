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
import pro.yakuraion.englishhelper.domain.repositories.LearningWordsRepository

@RunWith(Parameterized::class)
class LearningWordInteractorImplTest(
    private val word: LearningWord,
    private val nextLevelParams: UpdatedWordParams,
    private val previousLevelParams: UpdatedWordParams
) : InteractorTest() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var learningWordsRepository: LearningWordsRepository

    @MockK
    lateinit var learningRepository: LearningRepository

    lateinit var interactor: LearningWordInteractorImpl

    override fun setUp() {
        super.setUp()
        setUpMockk()
        interactor = LearningWordInteractorImpl(
            learningWordsRepository,
            learningRepository
        )
    }

    private fun setUpMockk() {
        coEvery { learningRepository.getLearningDay() } returns LEARNING_DAY
    }

    @Test
    fun moveWordToNextLevel() = runTest {
        interactor.moveWordToNextLevel(word)

        coVerify {
            learningWordsRepository.updateTodayWord(
                nextLevelParams.updatedWord,
                nextLevelParams.removeFromToday
            )
        }
    }

    @Test
    fun moveWordToPreviousLevel() = runTest {
        interactor.moveWordToPreviousLevel(word)

        coVerify {
            learningWordsRepository.updateTodayWord(
                previousLevelParams.updatedWord,
                previousLevelParams.removeFromToday
            )
        }
    }

    class UpdatedWordParams(val updatedWord: LearningWord, val removeFromToday: Boolean)

    companion object {

        private const val LEARNING_DAY = 0

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(
                LearningWord(Word("name", null), MemorizationLevel(0), 0),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(1), 1), true),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(0), 0), false)
            ),
            arrayOf(
                LearningWord(Word("name", null), MemorizationLevel(1), 0),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(2), 2), true),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(1), 1), true)
            ),
            arrayOf(
                LearningWord(Word("name", null), MemorizationLevel(2), 0),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(3), 4), true),
                UpdatedWordParams(LearningWord(Word("name", null), MemorizationLevel(1), 1), true)
            ),
        )
    }
}
