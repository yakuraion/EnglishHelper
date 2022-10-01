package pro.yakuraion.englishhelper.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "learning_word",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["name"],
            childColumns = ["name"],
            onDelete = CASCADE
        )
    ]
)
internal class LearningWordEntity(
    @PrimaryKey val name: String,
    @ColumnInfo val memorizationLevel: Int,
    @ColumnInfo val nextDayToLearn: Int
)
