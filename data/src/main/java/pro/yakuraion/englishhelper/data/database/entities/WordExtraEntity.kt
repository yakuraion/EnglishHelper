package pro.yakuraion.englishhelper.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "word_extra",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["name"],
            childColumns = ["name"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class WordExtraEntity(
    @PrimaryKey val name: String,
    val html: String,
    val remoteSoundUri: String,
    val localSoundUri: String,
    val examplesJson: String
)
