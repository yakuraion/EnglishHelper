package pro.yakuraion.englishhelper.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "word"
)
internal class WordEntity(
    @PrimaryKey val name: String,
    val soundUri: String?,
    val examplesJson: String
)
