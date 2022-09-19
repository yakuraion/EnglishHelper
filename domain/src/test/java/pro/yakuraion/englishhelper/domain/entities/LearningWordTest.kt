package pro.yakuraion.englishhelper.domain.entities

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class LearningWordTest(
    private val word: LearningWord,
    private val expectedIncreasedWord: LearningWord,
    private val expectedDecreasedWord: LearningWord
) {

    @Test
    fun increaseLevel() {
        assertEquals(expectedIncreasedWord, word.increaseLevel(CURRENT_DAY))
    }

    @Test
    fun decreaseLevel() {
        assertEquals(expectedDecreasedWord, word.decreaseLevel(CURRENT_DAY))
    }

    companion object {

        private const val CURRENT_DAY = 0

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(
                LearningWord(Word("name", null), MemorizationLevel(0), 0),
                LearningWord(Word("name", null), MemorizationLevel(1), 1),
                LearningWord(Word("name", null), MemorizationLevel(0), 0)
            ),
            arrayOf(
                LearningWord(Word("name", null), MemorizationLevel(1), 0),
                LearningWord(Word("name", null), MemorizationLevel(2), 2),
                LearningWord(Word("name", null), MemorizationLevel(1), 1)
            ),
            arrayOf(
                LearningWord(Word("name", null), MemorizationLevel(2), 0),
                LearningWord(Word("name", null), MemorizationLevel(3), 4),
                LearningWord(Word("name", null), MemorizationLevel(1), 1)
            ),
            arrayOf(
                LearningWord(Word("name", null), MemorizationLevel(3), 0),
                LearningWord(Word("name", null), MemorizationLevel(4), 8),
                LearningWord(Word("name", null), MemorizationLevel(2), 2)
            ),
        )
    }
}
