package pro.yakuraion.englishhelper.domain.usecases

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertSame
import org.junit.Rule
import org.junit.Test
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository

internal class IsWordAlreadyExistUseCaseTest : UseCaseTest<IsWordAlreadyExistUseCase>() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var wordsRepository: WordsRepository

    override fun setUpMocks() {
        coEvery { wordsRepository.getWordByName(any()) } returns null
        coEvery { wordsRepository.getWordByName(NAME) } returns WORD
    }

    override fun createUseCase(dispatchers: Dispatchers): IsWordAlreadyExistUseCase {
        return IsWordAlreadyExistUseCaseImpl(
            dispatchers,
            wordsRepository
        )
    }

    @Test
    fun isWordAlreadyExist() = runTest {
        assertSame(true, useCase.isWordAlreadyExist(NAME))
        assertSame(false, useCase.isWordAlreadyExist("name_1"))
    }

    companion object {

        private const val NAME = "name"

        private val WORD = Word("name", null)
    }
}
