package pro.yakuraion.englishhelper.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "completed_word",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["name"],
            childColumns = ["name"],
            onDelete = CASCADE
        )
    ]
)
internal class CompletedWordEntity(
    @PrimaryKey val name: String,
    val completedAtMillis: Long
)
