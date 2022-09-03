package pro.yakuraion.englishhelper.vocabulary.data.entities

import androidx.room.Ignore
import java.lang.Integer.max

class MemorizationLevel(val level: Int) {

    @delegate:Ignore
    val maxDeviation: Int by lazy { max(level - 1, 0) }

    init {
        check(level in 0..MAX_LEVEL) { "Invalid level = $level" }
    }

    fun isMaxLevel() = level == MAX_LEVEL

    fun increase(): MemorizationLevel {
        check(!isMaxLevel()) { "Cannot increase Memorization with maximum level ($MAX_LEVEL)" }
        return MemorizationLevel(level + 1)
    }

    fun decrease(): MemorizationLevel {
        val newLevel = when (level) {
            0, 1 -> level
            else -> level - 1
        }
        return MemorizationLevel(newLevel)
    }

    companion object {

        private const val MAX_LEVEL = 4

        fun new() = MemorizationLevel(0)
    }
}
