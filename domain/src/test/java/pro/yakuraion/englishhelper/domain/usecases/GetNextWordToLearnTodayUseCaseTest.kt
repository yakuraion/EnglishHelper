package pro.yakuraion.englishhelper.domain.usecases

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertSame
import org.junit.Rule
import org.junit.Test
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository

internal class GetNextWordToLearnTodayUseCaseTest : UseCaseTest<GetNextWordToLearnTodayUseCase>() {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var getWordsToLearnUseCase: GetWordsToLearnUseCase

    @MockK
    lateinit var wordsRepository: WordsRepository

    override fun setUpMocks() {
        coEvery { getWordsToLearnUseCase.getWordsToLearnToday() } returns flowOf(LEARNING_WORDS)
        coEvery { wordsRepository.getWordByName("name_0") } returns FIRST_WORD
        coEvery { wordsRepository.getWordByName("name_1") } returns SECOND_WORD
    }

    override fun createUseCase(dispatchers: Dispatchers): GetNextWordToLearnTodayUseCase {
        return GetNextWordToLearnTodayUseCaseImpl(
            getWordsToLearnUseCase,
            wordsRepository
        )
    }

    @Test
    fun getNextWordToLearnToday() = runTest {
        val word = useCase.getNextWordToLearnToday().first()!!

        assertSame(FIRST_WORD, word.word)
        assertSame(LEARNING_WORDS[0], word.learningWord)
    }

    @Test
    fun getNextWordToLearnTodayWithChangeableFlow() = runTest {
        val stateFlow = MutableStateFlow(LEARNING_WORDS)
        coEvery { getWordsToLearnUseCase.getWordsToLearnToday() } returns stateFlow

        val wordFlow = useCase.getNextWordToLearnToday()

        stateFlow.emit(LEARNING_WORDS.drop(1))

        val word = wordFlow.first()!!

        assertSame(SECOND_WORD, word.word)
        assertSame(LEARNING_WORDS[1], word.learningWord)
    }

    companion object {

        private val LEARNING_WORDS = listOf(
            LearningWord("name_0", MemorizationLevel(0), 0),
            LearningWord("name_1", MemorizationLevel(0), 0),
            LearningWord("name_2", MemorizationLevel(0), 0),
            LearningWord("name_3", MemorizationLevel(0), 0),
            LearningWord("name_4", MemorizationLevel(0), 0)
        )

        private val FIRST_WORD = Word("name_0", null)
        private val SECOND_WORD = Word("name_1", null)
    }
}
