package pro.yakuraion.englishhelper.domain.entities

class MemorizationLevel(val level: Int) {

    val maxDeviation: Int by lazy { Integer.max(level - 1, 0) }

    init {
        require(level in 0..MAX_LEVEL) { "Invalid level = $level" }
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
