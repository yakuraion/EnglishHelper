package pro.yakuraion.englishhelper.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity

@Entity(
    tableName = "learning_word",
    primaryKeys = ["word_name"],
)
internal data class LearningWordEntity(
    @Embedded(prefix = "word_") val word: Word,
    @ColumnInfo(name = "memorization_level") val memorizationLevel: Int,
    @ColumnInfo(name = "next_day_to_learn") val nextDayToLearn: Int
) {

    data class Word(
        val name: String,
        val soundFile: String?
    )
}
