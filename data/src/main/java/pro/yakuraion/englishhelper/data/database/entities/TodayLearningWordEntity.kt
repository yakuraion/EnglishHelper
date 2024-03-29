package pro.yakuraion.englishhelper.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "today_learning_word",
    foreignKeys = [
        ForeignKey(
            entity = LearningWordEntity::class,
            parentColumns = ["name"],
            childColumns = ["name"],
            onDelete = CASCADE
        )
    ]
)
internal class TodayLearningWordEntity(
    @PrimaryKey val name: String,
    @ColumnInfo val createdAtMillis: Long
)
