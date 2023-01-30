package pro.yakuraion.englishhelper.domain.entities.converters

import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Test
import pro.yakuraion.englishhelper.domain.entities.WooordhuntWord
import pro.yakuraion.englishhelper.domain.entities.WordExtra

class WordExtraConvertersTest {

    @Test
    fun `get word extra`() {
        val actual = getWordExtra(WOOORDHUNT_WORD, SOUND_FILE_URI)
        assertEquals(WORD_EXTRA, actual)
    }

    companion object {

        private const val SOUND_FILE_URI = ""

        private val WOOORDHUNT_WORD = WooordhuntWord(
            "pierce",
            "",
            "",
            listOf("pierce", "pierces", "piercing", "pierced"),
            listOf(
                WooordhuntWord.Example(
                    "Your drill is the drill that will pierce the Heavens!",
                    "Твой бур - это бур, который пронзит небеса!"
                ),
                WooordhuntWord.Example(
                    "One two three piercing four",
                    "12"
                ),
                WooordhuntWord.Example(
                    "One two three Piercing four",
                    "12"
                ),
            )
        )

        private val WORD_EXTRA = WordExtra(
            "pierce",
            SOUND_FILE_URI,
            persistentListOf(
                WordExtra.Example(
                    "Your drill is the drill that will %s the Heavens!",
                    "pierce",
                    "Твой бур - это бур, который пронзит небеса!"
                ),
                WordExtra.Example(
                    "One two three %s four",
                    "piercing",
                    "12"
                ),
                WordExtra.Example(
                    "One two three %s four",
                    "Piercing",
                    "12"
                )
            )
        )
    }
}
