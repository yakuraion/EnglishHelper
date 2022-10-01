package pro.yakuraion.englishhelper.domain.entities.learning

import pro.yakuraion.englishhelper.common.pow

data class MemorizationLevel(val level: Int) {

    init {
        require(level in 0..MAX_LEVEL) { "Invalid level = $level" }
    }

    val daysBeforeLearning: Int = if (level == 0) 0 else 2 pow (level - 1)

    val maxDeviation: Int = if (level == 0) 0 else (2 pow (level - 1)) - 1

    fun isMax() = level == MAX_LEVEL

    fun increase(): MemorizationLevel {
        check(!isMax()) { "Cannot increase Memorization with maximum level ($MAX_LEVEL)" }
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
