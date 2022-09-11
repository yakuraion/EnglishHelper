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
    fun getMaxDeviation() {
        assertEquals(MemorizationLevel(0).maxDeviation, 0)
        assertEquals(MemorizationLevel(1).maxDeviation, 0)
        assertEquals(MemorizationLevel(2).maxDeviation, 1)
    }

    @Test
    fun isMaxLevel() {
        assertFalse(MemorizationLevel(0).isMaxLevel())
        assertTrue(MemorizationLevel(MAX_LEVEL).isMaxLevel())
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
