package pro.yakuraion.englishhelper.vocabulary.data.entities

import androidx.room.Embedded
import androidx.room.Entity

@Entity(
    tableName = "learning_word",
    primaryKeys = ["word_name"],
)
data class LearningWordEntity(
    @Embedded(prefix = "word_") val word: Word,
    @Embedded(prefix = "memorization_level_") val memorizationLevel: MemorizationLevel
)
