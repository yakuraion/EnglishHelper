package pro.yakuraion.englishhelper.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.fakes.WooordhuntWords
import pro.yakuraion.englishhelper.domain.repositories.LearningRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import pro.yakuraion.englishhelper.domain.repositories.WordsSoundsRepository
import java.io.File

class AddWordUseCaseTest : UseCaseTest<AddWordUseCase>() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var isWordAlreadyExistUseCase: IsWordAlreadyExistUseCase

    @MockK
    lateinit var wordsRepository: WordsRepository

    @MockK
    lateinit var wordsSoundsRepository: WordsSoundsRepository

    @MockK
    lateinit var learningRepository: LearningRepository

    override fun setUpMocks() {
        coEvery { isWordAlreadyExistUseCase.isWordAlreadyExist(any()) } returns false
        coEvery { learningRepository.getLearningDay() } returns LEARNING_DAY
        coEvery { wordsSoundsRepository.downloadSoundForWorld(any(), any()) } returns File("")
    }

    override fun createUseCase(dispatchers: Dispatchers): AddWordUseCase {
        return AddWordUseCaseImpl(
            dispatchers,
            isWordAlreadyExistUseCase,
            wordsRepository,
            wordsSoundsRepository,
            learningRepository
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `add word that is already added`() = runTest {
        val word = WooordhuntWords.Existed.WORD
        coEvery { isWordAlreadyExistUseCase.isWordAlreadyExist(word.name) } returns true

        useCase.addWord(word.name, true)
    }

    @Test
    fun `add word with extra that will be founded`() = runTest {
        val word = WooordhuntWords.Existed.WORD
        coEvery { wordsRepository.downloadWoooordhuntWord(word.name) } returns word

        val result = useCase.addWord(word.name, true)

        coVerify { wordsRepository.addNewWord(word.name, word.html, any(), LEARNING_DAY) }
        assertEquals(AddWordUseCase.Result.SUCCESS, result)
    }

    @Test
    fun `add word with extra that will not be founded`() = runTest {
        val word = WooordhuntWords.NotFound.TOOWOO
        coEvery { wordsRepository.downloadWoooordhuntWord(word) } returns null

        val result = useCase.addWord(word, true)

        coVerify(exactly = 0) { wordsRepository.addNewWord(any(), any(), any(), any()) }
        assertEquals(AddWordUseCase.Result.WORD_NOT_FOUND, result)
    }

    @Test
    fun `add word without extra`() = runTest {
        val word = WooordhuntWords.NotFound.TOOWOO
        coEvery { wordsRepository.downloadWoooordhuntWord(word) } returns null

        val result = useCase.addWord(word, false)

        coVerify { wordsRepository.addNewLiteWord(word, LEARNING_DAY) }
        assertEquals(AddWordUseCase.Result.SUCCESS, result)
    }

    companion object {

        private const val LEARNING_DAY = 0
    }
}
