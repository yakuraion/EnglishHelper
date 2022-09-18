package pro.yakuraion.englishhelper.domain.entities

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class MemorizationLevelTest {

    @Test(expected = IllegalArgumentException::class)
    fun createWithInvalidLowLevel() {
        MemorizationLevel(-1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun createWithInvalidHighLevel() {
        MemorizationLevel(MAX_LEVEL + 1)
    }

    @Test
    fun daysBeforeLearning() {
        assertEquals(MemorizationLevel(0).daysBeforeLearning, 0)
        assertEquals(MemorizationLevel(1).daysBeforeLearning, 1)
        assertEquals(MemorizationLevel(2).daysBeforeLearning, 2)
        assertEquals(MemorizationLevel(3).daysBeforeLearning, 4)
        assertEquals(MemorizationLevel(4).daysBeforeLearning, 8)
    }

    @Test
    fun maxDeviation() {
        assertEquals(MemorizationLevel(0).maxDeviation, 0)
        assertEquals(MemorizationLevel(1).maxDeviation, 0)
        assertEquals(MemorizationLevel(2).maxDeviation, 1)
        assertEquals(MemorizationLevel(3).maxDeviation, 3)
        assertEquals(MemorizationLevel(4).maxDeviation, 7)
    }

    @Test
    fun isMaxLevel() {
        assertFalse(MemorizationLevel(0).isMax())
        assertTrue(MemorizationLevel(MAX_LEVEL).isMax())
    }

    @Test
    fun increase() {
        val level = MemorizationLevel(0).increase()
        assertEquals(level.level, 1)
    }

    @Test(expected = IllegalStateException::class)
    fun increaseFailed() {
        MemorizationLevel(MAX_LEVEL).increase()
    }

    @Test
    fun decrease() {
        assertEquals(MemorizationLevel(0).decrease().level, 0)
        assertEquals(MemorizationLevel(1).decrease().level, 1)
        assertEquals(MemorizationLevel(2).decrease().level, 1)
    }

    companion object {

        private const val MAX_LEVEL = 4
    }
}
