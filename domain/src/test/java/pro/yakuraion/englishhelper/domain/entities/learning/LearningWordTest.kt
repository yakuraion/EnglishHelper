package pro.yakuraion.englishhelper.domain.entities.learning

import org.junit.Assert.assertEquals
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
                LearningWord("name", MemorizationLevel(0), 0),
                LearningWord("name", MemorizationLevel(1), 1),
                LearningWord("name", MemorizationLevel(0), 0)
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(1), 0),
                LearningWord("name", MemorizationLevel(2), 2),
                LearningWord("name", MemorizationLevel(1), 1)
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(2), 0),
                LearningWord("name", MemorizationLevel(3), 4),
                LearningWord("name", MemorizationLevel(1), 1)
            ),
            arrayOf(
                LearningWord("name", MemorizationLevel(3), 0),
                LearningWord("name", MemorizationLevel(4), 8),
                LearningWord("name", MemorizationLevel(2), 2)
            ),
        )
    }
}
