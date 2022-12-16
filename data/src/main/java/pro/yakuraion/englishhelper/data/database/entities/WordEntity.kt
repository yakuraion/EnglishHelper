package pro.yakuraion.englishhelper.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "word"
)
internal class WordEntity(
    @PrimaryKey val name: String,
    // todo: rename to soundUri
    val soundFile: String?,
    val examplesJson: String
)
