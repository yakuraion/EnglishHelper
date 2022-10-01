package pro.yakuraion.englishhelper.domain.entities.learning

data class LearningWord(
    val name: String,
    val memorizationLevel: MemorizationLevel,
    val nextDayToLearn: Int
) {

    fun isMaxLevel() = memorizationLevel.isMax()

    fun increaseLevel(currentDay: Int): LearningWord {
        val newLevel = memorizationLevel.increase()
        return copyWithNewLevel(currentDay, newLevel)
    }

    fun decreaseLevel(currentDay: Int): LearningWord {
        val newLevel = memorizationLevel.decrease()
        return copyWithNewLevel(currentDay, newLevel)
    }

    private fun copyWithNewLevel(currentDay: Int, newLevel: MemorizationLevel): LearningWord {
        return LearningWord(
            name = name,
            memorizationLevel = newLevel,
            nextDayToLearn = currentDay + newLevel.daysBeforeLearning
        )
    }
}
