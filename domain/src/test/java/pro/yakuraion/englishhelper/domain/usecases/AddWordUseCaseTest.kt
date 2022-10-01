package pro.yakuraion.englishhelper.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.TodayLearningWordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository
import java.io.File

class AddWordUseCaseTest : UseCaseTest<AddWordUseCase>() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var isWordAlreadyExistUseCase: IsWordAlreadyExistUseCase

    @MockK
    lateinit var wordsSoundsRepository: WordsSoundsRepository

    @MockK
    lateinit var todayLearningWordsRepository: TodayLearningWordsRepository

    @MockK
    lateinit var learningRepository: LearningRepository

    private val soundsFile = File("")

    override fun setUpMocks() {
        coEvery { isWordAlreadyExistUseCase.isWordAlreadyExist(NAME) } returns false
        coEvery { wordsSoundsRepository.downloadSoundForWorld(NAME) } returns soundsFile
        coEvery { learningRepository.getLearningDay() } returns LEARNING_DAY
    }

    override fun createUseCase(dispatchers: Dispatchers): AddWordUseCase {
        return AddWordUseCaseImpl(
            dispatchers,
            isWordAlreadyExistUseCase,
            wordsSoundsRepository,
            todayLearningWordsRepository,
            learningRepository
        )
    }

    @Test
    fun addWord() = runTest {
        useCase.addWord(NAME)

        coVerify { todayLearningWordsRepository.addWordToLearning(NAME, soundsFile, LEARNING_DAY) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun addWordWithAlreadyExistedError() = runTest {
        coEvery { isWordAlreadyExistUseCase.isWordAlreadyExist(NAME) } returns true

        useCase.addWord(NAME)
    }

    companion object {

        private const val NAME = "name"

        private const val LEARNING_DAY = 0
    }
}
